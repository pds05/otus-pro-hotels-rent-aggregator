package ru.otus.java.pro.result.project.hotels.services;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotels.dtos.UserDtoRq;
import ru.otus.java.pro.result.project.hotels.entities.UserProfile;
import ru.otus.java.pro.result.project.hotels.exceptions.ResourceNotFoundException;
import ru.otus.java.pro.result.project.hotels.repositories.UserProfilesRepository;

@RequiredArgsConstructor
@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfilesRepository repository;
    @Override
    public UserProfile createUserProfile(UserDtoRq userDtoRq) {
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
        return repository.save(userProfile);
    }

    @Override
    public UserProfile findUserProfile(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist"));
    }
}
