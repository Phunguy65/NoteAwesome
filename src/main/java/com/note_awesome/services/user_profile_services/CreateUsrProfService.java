package com.note_awesome.services.user_profile_services;

import com.note_awesome.core.entities.note.UserProfile;
import com.note_awesome.core.repositories.note.IUserProfileRepository;
import com.note_awesome.core.repositories.note.IUserRepository;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;

@Component
public class CreateUsrProfService implements ICreateUsrProfService {

    private final IUserProfileRepository userProfileRepository;
    private final IUserRepository userRepository;
    private final UsrProfileAbstractValidator usrProfileAbstractValidator;

    public CreateUsrProfService(IUserProfileRepository userProfileRepository, IUserRepository userRepository, UsrProfileAbstractValidator usrProfileAbstractValidator) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
        this.usrProfileAbstractValidator = usrProfileAbstractValidator;
    }

    private String generateUniqueName(Optional<Integer> length) {
        var maxLength = length.orElse(10);
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder uniqueProfileName = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < maxLength; i++) {
            uniqueProfileName.append(chars.charAt(random.nextInt(chars.length())));
        }

        return uniqueProfileName.toString();

    }

    private Result<Path, Error> createUsrProfLocation(UserProfile userProfile) {
        var root = userProfile.getProfileLocation();
        var uniqueName = generateUniqueName(Optional.of(10));
        var desPath = Path.of(root, uniqueName);

        while (Files.exists(desPath)) {
            uniqueName = generateUniqueName(Optional.of(10));
            desPath = Path.of(root, uniqueName);
        }

        try {
            Files.createDirectory(desPath);
            return Result.success(desPath);
        } catch (Exception e) {
            try {
                Files.deleteIfExists(desPath);
            } catch (IOException ex) {
                return Result.failure(new Error("Error creating user profile location", "Error creating user profile location"));
            }
            return Result.failure(new Error("Error creating user profile location", "Error creating user profile location"));
        }
    }

    private Result<UserProfile, Error> save(UserProfile userProfile) {
        try {
            var savedUserProfile = userProfileRepository.save(userProfile);
            return Result.success(savedUserProfile);
        } catch (Exception e) {
            return Result.failure(new Error("Error saving user profile", "Error saving user profile"));
        }
    }

    private Result<UserProfile, Error> procedure(UserProfile userProfile) {
        var result = usrProfileAbstractValidator.validate(userProfile);
        if (!result.isSuccess()) {
            return result;
        }

        var location = createUsrProfLocation(userProfile);
        if (!location.isSuccess()) {
            return Result.failure(location.getError());
        }

        UserProfile newUserProfile = new UserProfile();
        newUserProfile.setProfileLocation(location.getValue().toString());
        newUserProfile.setProfileName(userProfile.getProfileName());
        newUserProfile.setLastUsed(userProfile.getLastUsed());
        newUserProfile.setUser(userProfile.getUser());

        return Result.success(newUserProfile);

    }

    @Override
    @Transactional
    public Result<UserProfile, Error> create(UserProfile userProfile) {
        return procedure(userProfile).Match(this::save, Result::failure);
    }

    @Override
    @Transactional
    public Result<UserProfile, Error> create(String profileName, String profileLocationUrl, long userId) {
        var user = userRepository.getReferenceById(userId);
        var newUserProfile = new UserProfile();
        newUserProfile.setProfileName(profileName);
        newUserProfile.setProfileLocation(profileLocationUrl);
        newUserProfile.setUser(user);
        return create(newUserProfile);
    }

    @Override
    @Transactional
    public Result<UserProfile, Error> create(String profileName, String profileLocationUrl, long userId, long profileSettingId) {
        var user = userRepository.getReferenceById(userId);
        var newUserProfile = new UserProfile();
        newUserProfile.setProfileName(profileName);
        newUserProfile.setProfileLocation(profileLocationUrl);
        newUserProfile.setUser(user);
        return create(newUserProfile);
    }


}
