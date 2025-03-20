package ru.otus.java.pro.result.project.hotels.services;

import ru.otus.java.pro.result.project.hotels.dtos.UserDtoRq;
import ru.otus.java.pro.result.project.hotels.entities.UserProfile;

import java.util.Optional;

public interface UserProfileService {

    UserProfile createUserProfile(UserDtoRq userDtoRq);

    Optional<UserProfile> findUserProfile(String id);
}
