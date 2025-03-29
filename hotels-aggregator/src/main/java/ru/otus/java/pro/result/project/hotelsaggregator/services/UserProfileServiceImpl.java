package ru.otus.java.pro.result.project.hotelsaggregator.services;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotelsaggregator.exceptions.BusinessLogicException;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.UserDtoRq;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.UserProfile;
import ru.otus.java.pro.result.project.hotelsaggregator.repositories.UserProfileRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository repository;
    @Override
    public UserProfile createUserProfile(UserDtoRq userDtoRq) {
        log.info("Creating new user profile, {}", userDtoRq);
        repository.findByEmailOrPhoneNumber(userDtoRq.getEmail(), userDtoRq.getPhoneNumber()).ifPresent(profile -> {
            throw new BusinessLogicException("USER_ALREADY_EXIST", "user already exists with " + ((userDtoRq.getEmail() != null && userDtoRq.getEmail().equalsIgnoreCase(profile.getEmail())) ? "email " + userDtoRq.getEmail() : "phoneNumber " + userDtoRq.getPhoneNumber()));
        });
        UserProfile userProfile = UserProfile.builder()
                .firstName(userDtoRq.getFirstName())
                .lastName(userDtoRq.getLastName())
                .middleName(userDtoRq.getMiddleName())
                .password(userDtoRq.getPassword())
                .email(userDtoRq.getEmail())
                .phoneNumber(userDtoRq.getPhoneNumber())
                .isActive(true)
                .login(StringUtils.isBlank(userDtoRq.getEmail()) ? userDtoRq.getPhoneNumber() : userDtoRq.getEmail())
                .build();
        userProfile = repository.save(userProfile);
        log.info("User profile is created, {}", userProfile);
        return userProfile;
    }

    @Override
    public UserProfile findUserProfile(String id) {
        return null;
    }

    @Override
    public UserProfile updateUserProfile(UserDtoRq userDtoRq) {
        return null;
    }

    @Override
    public UserProfile activateUserProfile(String id) {
        return null;
    }

    @Override
    public void deleteUserProfile(String id) {

    }
}
