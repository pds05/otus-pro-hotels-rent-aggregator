package ru.otus.java.pro.result.project.hotelsaggregator.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.java.pro.result.project.hotelsaggregator.configs.properties.ProviderUserProperty;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.messages.ProviderResponseDto;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.messages.UserDtoMsg;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.messages.UserDtoRqMsg;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.ProviderUserProfile;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.UserProfile;
import ru.otus.java.pro.result.project.hotelsaggregator.enums.BusinessMethodEnum;
import ru.otus.java.pro.result.project.hotelsaggregator.messaging.MessageService;
import ru.otus.java.pro.result.project.hotelsaggregator.repositories.ProviderUserProfileRepository;
import ru.otus.java.pro.result.project.hotelsaggregator.security.PasswordGenerator;
import ru.otus.java.pro.result.project.hotelsaggregator.security.PasswordProtector;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProviderUserProfileServiceImpl implements ProviderUserProfileService {
    public static final int SALT_LENGTH = 10;

    private final ProviderUserProfileRepository providerUserProfileRepository;
    private final ProviderUserProperty providerUserProperty;
    private final PasswordProtector passwordSecurity;
    private final PasswordGenerator passwordGenerator;
    private final MessageService messageService;

    @Override
    public Optional<ProviderUserProfile> getProviderUserProfile(String userProfileId) {
        return providerUserProfileRepository.findByUserProfile_Id(userProfileId);
    }

    @Transactional
    @Override
    public ProviderUserProfile createProviderUserProfile(UserProfile userProfile, Provider provider) {
        log.debug("Creating provider user profile {}", userProfile);
        UserDtoRqMsg request = new UserDtoRqMsg();
        request.setFirstName(userProfile.getFirstName());
        request.setLastName(userProfile.getLastName());
        request.setMiddleName(userProfile.getMiddleName());
        request.setPhoneNumber(userProfile.getPhoneNumber());
        String email = prepareEmail(userProfile.getPhoneNumber());
        request.setEmail(email);

        String password = passwordGenerator.generateInsecurePassword();
        //Пересылаем запрос с паролем в простом шифровании через кафку
        request.setPassword(passwordSecurity.simpleEncryptPassword(password));
        ProviderResponseDto<UserDtoMsg> providerResponse = messageService.sendMessage(provider, request, BusinessMethodEnum.REGISTER_USER);
        UserDtoMsg userDto = providerResponse.getData();

        ProviderUserProfile providerUserProfile = new ProviderUserProfile();
        providerUserProfile.setLogin(userDto.getLogin());
        providerUserProfile.setProvidersUserId(userDto.getId());
        providerUserProfile.setProvider(provider);
        providerUserProfile.setFirstName(userProfile.getFirstName());
        providerUserProfile.setLastName(userProfile.getLastName());
        providerUserProfile.setMiddleName(userProfile.getMiddleName());
        providerUserProfile.setPhoneNumber(userProfile.getPhoneNumber());
        providerUserProfile.setEmail(email);
        providerUserProfile.setUserProfile(userProfile);

        //сохраняем пароль в базе и шифруем симметричным ключом
        String salt = RandomStringUtils.insecure().nextAlphanumeric(SALT_LENGTH);
        String cipher = passwordSecurity.encryptPassword(password, salt);
        providerUserProfile.setPassword(cipher);
        providerUserProfile.setPasswordSalt(salt);

        providerUserProfile = providerUserProfileRepository.save(providerUserProfile);
        log.info("Provider user profile created {}", providerUserProfile);
        return providerUserProfile;
    }

    private String prepareEmail(String phoneNumber) {
        StringBuilder email = new StringBuilder();
        if (providerUserProperty.isEmailByPhoneNumber() && phoneNumber != null && !phoneNumber.isEmpty()) {
            email.append(phoneNumber);
        } else {
            email.append(RandomStringUtils.insecure().nextAlphanumeric(providerUserProperty.getEmailUserLength()));
        }
        return email.append("@").append(providerUserProperty.getEmailDomain()).toString();
    }
}
