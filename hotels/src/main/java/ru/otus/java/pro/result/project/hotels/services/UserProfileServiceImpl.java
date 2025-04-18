package ru.otus.java.pro.result.project.hotels.services;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotels.dtos.UserDtoRq;
import ru.otus.java.pro.result.project.hotels.entities.UserProfile;
import ru.otus.java.pro.result.project.hotels.exceptions.BusinessLogicException;
import ru.otus.java.pro.result.project.hotels.exceptions.ResourceNotFoundException;
import ru.otus.java.pro.result.project.hotels.repositories.UserProfilesRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfilesRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
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
                .password(passwordEncoder.encode(userDtoRq.getPassword()))
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
    public UserProfile getUserProfile(String id) {
        UserProfile userProfile = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist"));
        if (!userProfile.getIsActive()) {
            throw new BusinessLogicException("USER_BLOCKED", "User profile is blocked");
        }
        return userProfile;
    }

    @Override
    public UserProfile updateUserProfile(UserDtoRq userDtoRq) {
        //TODO бизнес-метод на подумать
        return null;
    }

    @Transactional
    @Override
    public void deleteUserProfile(String id) {
        log.info("Deleting user profile, {}", id);
        UserProfile userProfile = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist"));
        userProfile.setIsActive(false);
        repository.save(userProfile);
        log.info("User profile is blocked, {}", userProfile);
    }

    @Override
    public UserProfile activateUserProfile(String id) {
        log.info("Activating user profile, {}", id);
        UserProfile userProfile = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist"));
        if (!userProfile.getIsActive()) {
            userProfile.setIsActive(true);
            repository.save(userProfile);
        }
        log.info("User profile is activated, {}", userProfile);
        return userProfile;
    }

}
