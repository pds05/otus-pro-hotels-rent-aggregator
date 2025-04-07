package ru.otus.java.pro.result.project.hotelsaggregator.services;


import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.ProviderUserProfile;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.UserProfile;

import java.util.Optional;

public interface ProviderUserProfileService {

    Optional <ProviderUserProfile> getProviderUserProfile(String userProfileId);

    ProviderUserProfile createProviderUserProfile(UserProfile userProfile, Provider provider);

}
