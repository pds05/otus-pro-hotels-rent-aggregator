package ru.otus.java.pro.result.project.hotelsaggregator.services;

import ru.otus.java.pro.result.project.hotelsaggregator.dtos.UserDtoRq;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.UserProfile;

public interface UserProfileService {

    UserProfile createUserProfile(UserDtoRq userDtoRq);

    UserProfile findUserProfile(String id);

    UserProfile updateUserProfile(UserDtoRq userDtoRq);

    UserProfile activateUserProfile(String id);

    void deleteUserProfile(String id);
}
