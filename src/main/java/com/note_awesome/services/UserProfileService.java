package com.note_awesome.services;

import com.note_awesome.NoteAwesomeEnv;
import com.note_awesome.core.entities.note.UserProfile;
import com.note_awesome.core.repositories.note.IUserProfileRepository;
import com.note_awesome.extensions.Error;
import com.note_awesome.extensions.Result;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class UserProfileService implements IUserProfileService {
    private final IUserProfileRepository userProfileRepository;

    public static class UserProfileError {
        public static final Error USER_PROFILE_NOT_FOUND(String name) {
            return new Error("User profile not found", "User profile not found by name: " + name);
        }

        public static final Error USER_PROFILE_ALREADY_EXISTS(String name) {
            return new Error("User profile already exists", "User profile already exists by name: " + name);
        }

        public static final Error USER_PROFILE_LOCATION_CANNOT_BE_CREATED(String location) {
            return new Error("User profile location cannot be created", "User profile location cannot be created: " + location);
        }
    }

    public UserProfileService(IUserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public Result<UserProfile, Error> createUserProfile(UserProfile userProfile) {
        var userProfileExists = this.getUserProfile(userProfile);

        if (userProfileExists.isSuccess()) {
            return Result.failure(UserProfileError.USER_PROFILE_ALREADY_EXISTS(userProfile.getProfileName()));
        }

        try {
            Files.createDirectories(Path.of(NoteAwesomeEnv.USER_DATA_FOLDER_PATH, userProfile.getProfileName()));
        } catch (IOException e) {
            return Result.failure(UserProfileError.USER_PROFILE_LOCATION_CANNOT_BE_CREATED(userProfile.getProfileName()));
        }

        return Result.success(userProfileRepository.save(userProfile));
    }

    @Override
    public Result<UserProfile, Error> getUserProfile(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("User profile cannot be null");
        }

        var userProfileOptional = userProfileRepository.findByProfileName(userProfile.getProfileName());

        return userProfileOptional.<Result<UserProfile, Error>>map(Result::success).orElseGet(() -> Result.failure(UserProfileError.USER_PROFILE_NOT_FOUND(userProfile.getProfileName())));

    }

}
