package com.note_awesome.core.repositories.note;

import com.note_awesome.core.entities.note.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByProfileName(String profileName);

    boolean existsByProfileName(String profileName);

    List<UserProfile> getByUser_IdOrderByLastUsedDesc(@NonNull long id);

    @Nullable
    Optional<UserProfile> getByProfileLocationLike(@NonNull String profileLocation);


}
