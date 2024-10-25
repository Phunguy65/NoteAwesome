package com.note_awesome.services.user_profile_services.validators;

import com.note_awesome.core.entities.note.UserProfile;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class UsrProfileBasicValidator implements IUsrProfileBasicValidator {
    private final int MAX_URL_LOCATION_LENGTH = 255;
    private final int MAX_PROFILE_NAME_LENGTH = 50;

    public static class UsrProfileValidationError {
        public final static Error PROFILE_NAME_EMPTY = new Error("Profile name is empty", "Profile name is empty");
        public final static Error PROFILE_NAME_TOO_LONG = new Error("Profile name is too long", "Profile name is too long");
        public final static Error URL_LOCATION_EMPTY = new Error("URL location is empty", "URL location is empty");
        public final static Error URL_LOCATION_TOO_LONG = new Error("URL location is too long", "URL location is too long");

        public static Error URL_LOCATION_NOT_FOUND(String imageUrl) {
            return new Error("URL location not found", "URL location not found: " + imageUrl);
        }
    }

    @Override
    public Result<UserProfile, Error> validate(UserProfile userProfile) {
        if (userProfileIsNull(userProfile)) {
            return Result.failure(UsrProfileValidationError.PROFILE_NAME_EMPTY);
        }
        if (profileNameIsEmpty(userProfile)) {
            return Result.failure(UsrProfileValidationError.PROFILE_NAME_EMPTY);
        }
        if (profileNameTooLong(userProfile)) {
            return Result.failure(UsrProfileValidationError.PROFILE_NAME_TOO_LONG);
        }
        if (urlLocationIsEmpty(userProfile)) {
            return Result.failure(UsrProfileValidationError.URL_LOCATION_EMPTY);
        }
        if (urlLocationTooLong(userProfile)) {
            return Result.failure(UsrProfileValidationError.URL_LOCATION_TOO_LONG);
        }
        return Result.success(userProfile);
    }

    private boolean userProfileIsNull(UserProfile userProfile) {
        return userProfile == null;
    }

    private boolean profileNameIsEmpty(UserProfile userProfile) {
        return userProfile.getProfileName() == null || userProfile.getProfileName().isEmpty();
    }

    private boolean profileNameTooLong(UserProfile userProfile) {
        return userProfile.getProfileName().length() > MAX_PROFILE_NAME_LENGTH;
    }

    private boolean urlLocationIsEmpty(UserProfile userProfile) {
        return userProfile.getProfileLocationUrl() == null || !Files.exists(Paths.get(userProfile.getProfileLocationUrl()));
    }

    private boolean urlLocationTooLong(UserProfile userProfile) {
        return userProfile.getProfileLocationUrl().length() > MAX_URL_LOCATION_LENGTH;
    }
}
