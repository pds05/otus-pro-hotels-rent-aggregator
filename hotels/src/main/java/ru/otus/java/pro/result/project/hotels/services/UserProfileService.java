package ru.otus.java.pro.result.project.hotels.services;

import ru.otus.java.pro.result.project.hotels.dtos.UserDtoRq;
import ru.otus.java.pro.result.project.hotels.entities.UserProfile;

import java.util.Optional;

public interface UserProfileService {

    UserProfile createUserProfile(UserDtoRq userDtoRq);

    UserProfile findUserProfile(String id);

    UserProfile updateUserProfile(UserDtoRq userDtoRq);

    UserProfile activateUserProfile(String id);

    void deleteUserProfile(String id);
}
