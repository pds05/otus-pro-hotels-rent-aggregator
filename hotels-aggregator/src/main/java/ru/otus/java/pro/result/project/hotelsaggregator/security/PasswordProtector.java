package ru.otus.java.pro.result.project.hotelsaggregator.security;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@NoArgsConstructor
@Component
public class PasswordProtector {
    public static final String KEY_STORE_TYPE = "JCEKS";
    public static final String KEY_STORE_PASSWORD = "secret";
    public static final String KEY_STORE_FILE_NAME = "keystore.jks";
    public static final String ENCRYPTION_ALGORITHM = "AES";
//    public static final String CIPHER_TRANSFORMATION_TYPE = "AES/CFB/NoPadding";
    public static final String CIPHER_TRANSFORMATION_TYPE = "AES/CBC/PKCS5Padding";
    public static final String SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA256";
    public static final String CHARSET = "UTF-8";
    public static final int BLOCK_SIZE = 16;

    @SneakyThrows
    public static KeyStore getKeyStore() {
        KeyStore ks = KeyStore.getInstance(KEY_STORE_TYPE);
        char[] pwdArray = KEY_STORE_PASSWORD.toCharArray();
        File keyStoreFile = new File(KEY_STORE_FILE_NAME);
        if (!keyStoreFile.exists()) {
            ks.load(null, pwdArray);
            try (FileOutputStream fos = new FileOutputStream(keyStoreFile)) {
                ks.store(fos, pwdArray);
            }
        }
        try (FileInputStream fis = new FileInputStream(keyStoreFile)) {
            ks.load(fis, pwdArray);
        }
        return ks;
    }

    @SneakyThrows
    private void saveSecretKey(String alias, SecretKey cipher) {
        KeyStore ks = getKeyStore();
        char[] pwdArray = KEY_STORE_PASSWORD.toCharArray();
        KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(pwdArray);
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(cipher);
        ks.setEntry(alias, secretKeyEntry, protectionParameter);
        try (FileOutputStream fos = new FileOutputStream(KEY_STORE_FILE_NAME)) {
            ks.store(fos, pwdArray);
        }
    }

    private byte[] getDefaultIv() {
        return new byte[] { 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0 };
    }

    private IvParameterSpec generateIv() {
        byte[] iv = new byte[BLOCK_SIZE];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    @SneakyThrows
    private SecretKey getKeyFromPassword(String password, String salt) {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), ENCRYPTION_ALGORITHM);
    }

    @SneakyThrows
    public String encryptPassword(String password, String salt) {
        IvParameterSpec parameterSpec = new IvParameterSpec(getDefaultIv());
        SecretKey secretKey = getKeyFromPassword(password, salt);
        String cipherText = encrypt(password, secretKey, parameterSpec);
        log.info("Encoded cipher text: {}", cipherText);
        saveSecretKey(salt, secretKey);
        return cipherText;
    }

    @SneakyThrows
    private String encrypt(String input, SecretKey key, IvParameterSpec iv) {
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_TYPE);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes(CHARSET));
        log.info("Encrypt text={}", new String(cipherText));
        return Base64.getEncoder().encodeToString(cipherText);
    }

    @SneakyThrows
    public String decryptPassword(String encodedPassword, String salt) {
        KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(KEY_STORE_PASSWORD.toCharArray());
        KeyStore.SecretKeyEntry skEntry = (KeyStore.SecretKeyEntry) getKeyStore().getEntry(salt, protectionParameter);
        SecretKey secretKey = skEntry.getSecretKey();
        log.info("Encoded cipher text={}", encodedPassword);
        byte[] decodedText = Base64.getDecoder().decode(encodedPassword);
        log.info("Decoded cipher text={}", new String(decodedText));
//        byte[] iv = Arrays.copyOfRange(decodedText, 0, BLOCK_SIZE);
        IvParameterSpec parameterSpec = new IvParameterSpec(getDefaultIv());
//        final byte[] textToDecipherWithoutIv = Arrays.copyOfRange(decodedText, BLOCK_SIZE,
//                decodedText.length);
        return decrypt(decodedText, secretKey, parameterSpec);
    }

    @SneakyThrows
    private String decrypt(byte[] encryptedText, SecretKey key, IvParameterSpec iv) {
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_TYPE);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decryptedText = cipher.doFinal(encryptedText);
        log.info("Decrypted text={}", new String(decryptedText, CHARSET));
        return new String(decryptedText, CHARSET);
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

    @SneakyThrows
    public String simpleDecryptPassword(String cipherText) {
        return new String(Base64.getDecoder().decode(cipherText), CHARSET);
    }
}
