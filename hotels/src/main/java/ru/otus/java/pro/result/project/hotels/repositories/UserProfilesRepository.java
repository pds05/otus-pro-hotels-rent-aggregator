package ru.otus.java.pro.result.project.hotels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.result.project.hotels.entities.UserProfile;

import java.util.Optional;

@Repository
public interface UserProfilesRepository extends JpaRepository<UserProfile, String> {

    Optional<UserProfile> findByEmailOrPhoneNumber(String email, String phoneNumber);
}
