package com.note_awesome.services.user_services.validators;

import com.note_awesome.core.entities.note.User;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class UserBasicValidator implements IUserBasicValidator {
    private final int MAX_USERNAME_LENGTH = 50;
    private final int MAX_PASSWORD_LENGTH = 72;
    private final int MAX_USER_LOCATION_LENGTH = 255;

    public static class UserBasicValidatorError {
        public static final Error INVALID_USERNAME = new Error("Invalid username", "Username must be between 1 and 50 characters");
        public static final Error INVALID_PASSWORD = new Error("Invalid password", "Password must be between 1 and 72 characters");
        public static final Error USER_LOCATION_TOO_LONG = new Error("User location is too long", "User location is too long");
        public static final Error USER_LOCATION_NULL = new Error("User location is null", "User location is null");
    }

    @Override
    public Result<User, Error> validate(User user) {
        if (!isValidUsername(user.getUsername())) {
            return Result.failure(UserBasicValidatorError.INVALID_USERNAME);
        }
        if (!isValidPassword(user.getPassword())) {
            return Result.failure(UserBasicValidatorError.INVALID_PASSWORD);
        }
        if (isUserLocationNull(user)) {
            return Result.failure(UserBasicValidatorError.USER_LOCATION_NULL);
        }

        if (isUserLocationTooLong(user)) {
            return Result.failure(UserBasicValidatorError.USER_LOCATION_TOO_LONG);
        }
        return Result.success(user);
    }

    protected boolean isValidUsername(String username) {
        return username != null && !username.isEmpty() && username.length() <= MAX_USERNAME_LENGTH;
    }

    protected boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return true;
        }
        return password.length() <= MAX_PASSWORD_LENGTH;
    }

    protected boolean isUserLocationNull(User user) {
        return user.getUserLocation() == null && Files.exists(Path.of(user.getUserLocation()));
    }

    protected boolean isUserLocationTooLong(User user) {
        return user.getUserLocation().length() > MAX_USER_LOCATION_LENGTH;
    }
}
