package ru.otus.java.pro.result.project.hotelsaggregator.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.KeySpec;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class PasswordProtector {
    public static final String KEY_STORE_TYPE = "pkcs12";
    public static final String KEY_STORE_PASSWORD = "secret";
    public static final String KEY_STORE_FILE_NAME = "keystore.jks";
    public static final String ENCRYPTION_ALGORITHM = "AES";
    public static final String CHARSET = "UTF-8";

    public static KeyStore getKeyStore() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        KeyStore ks = KeyStore.getInstance(KEY_STORE_TYPE);
        File keyStoreFile = new File(KEY_STORE_FILE_NAME);
        if (!keyStoreFile.exists()) {
            char[] pwdArray = KEY_STORE_PASSWORD.toCharArray();
            ks.load(null, pwdArray);
            try (FileOutputStream fos = new FileOutputStream(keyStoreFile)) {
                ks.store(fos, pwdArray);
            }
        }
        try (FileInputStream fis = new FileInputStream(keyStoreFile)) {
            ks.load(fis, KEY_STORE_PASSWORD.toCharArray());
        }
        return ks;
    }

    @SneakyThrows
    public String encryptPassword(String password, String salt) {
        GCMParameterSpec gcmParameterSpec = generateIv();
        SecretKey secretKey = getKeyFromPassword(password, salt);
        String cipherText = encrypt(password, secretKey, gcmParameterSpec);
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
        KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(KEY_STORE_PASSWORD.toCharArray());
        KeyStore ks = getKeyStore();
        ks.setEntry(salt, secretKeyEntry, protectionParameter);
        try (FileOutputStream fos = new FileOutputStream(KEY_STORE_FILE_NAME)) {
            ks.store(fos, password.toCharArray());
        }
        return cipherText;
    }

    @SneakyThrows
    public String decryptPassword(String encodedPassword, String salt) {
        GCMParameterSpec gcmParameterSpec = generateIv();
        KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(KEY_STORE_PASSWORD.toCharArray());
        KeyStore.SecretKeyEntry skEntry = (KeyStore.SecretKeyEntry) getKeyStore().getEntry(salt, protectionParameter);
        SecretKey secretKey = skEntry.getSecretKey();
        return decrypt(encodedPassword, secretKey, gcmParameterSpec);
    }

    private GCMParameterSpec generateIv() {
        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);
        return new GCMParameterSpec(96, iv);
    }

    @SneakyThrows
    private SecretKey getKeyFromPassword(String password, String salt) {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(CHARSET), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), ENCRYPTION_ALGORITHM);
    }

    @SneakyThrows
    private String encrypt(String input, SecretKey key, GCMParameterSpec iv) {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes(CHARSET));
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    @SneakyThrows
    private String decrypt(String cipherText, SecretKey key, GCMParameterSpec iv) {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    @SneakyThrows
    private String convertSecretKeyToString(SecretKey secretKey) {
        byte[] rawData = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(rawData);
    }

    private static SecretKey convertStringToSecretKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, ENCRYPTION_ALGORITHM);
    }

    @SneakyThrows
    public String simpleEncryptPassword(String password) {
        return new String(Base64.getEncoder().encode(password.getBytes(CHARSET)));
    }

    public String simpleDecryptPassword(String cipherText) {
        return new String(Base64.getDecoder().decode(cipherText));
    }
}
