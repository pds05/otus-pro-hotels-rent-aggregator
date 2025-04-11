package ru.otus.java.pro.result.project.hotelsaggregator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.ProviderUserProfile;

import java.util.Optional;

@Repository
public interface ProviderUserProfileRepository extends JpaRepository<ProviderUserProfile, Long> {

    Optional<ProviderUserProfile> findByUserProfile_Id(String userProfileId);
}
