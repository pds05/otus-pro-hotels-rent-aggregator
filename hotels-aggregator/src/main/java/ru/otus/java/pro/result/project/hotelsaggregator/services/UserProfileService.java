package ru.otus.java.pro.result.project.hotelsaggregator.services;

import ru.otus.java.pro.result.project.hotelsaggregator.dtos.UserDtoRq;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.UserDtoUpdateRq;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.UserProfile;

public interface UserProfileService {

    UserProfile createUserProfile(UserDtoRq userDtoRq);

    UserProfile getUserProfile(String id);

    UserProfile updateUserProfile(UserDtoUpdateRq userDtoUpdateRq);

    UserProfile activateUserProfile(String id);

    void deleteUserProfile(String id);
}
