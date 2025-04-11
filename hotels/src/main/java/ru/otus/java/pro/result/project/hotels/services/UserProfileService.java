package ru.otus.java.pro.result.project.hotels.services;

import ru.otus.java.pro.result.project.hotels.dtos.UserDtoRq;
import ru.otus.java.pro.result.project.hotels.entities.UserProfile;

public interface UserProfileService {

    UserProfile createUserProfile(UserDtoRq userDtoRq);

    UserProfile getUserProfile(String id);

    UserProfile updateUserProfile(UserDtoRq userDtoRq);

    UserProfile activateUserProfile(String id);

    void deleteUserProfile(String id);
}
