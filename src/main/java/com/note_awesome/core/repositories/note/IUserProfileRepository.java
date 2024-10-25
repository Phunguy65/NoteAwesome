package com.note_awesome.core.repositories.note;

import com.note_awesome.core.entities.note.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByProfileName(String profileName);

    boolean existsByProfileName(String profileName);
}
