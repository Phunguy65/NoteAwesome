package com.note_awesome.services.user_profile_services.validators;

import com.note_awesome.core.repositories.note.IUserProfileRepository;
import com.note_awesome.core.repositories.note.IUserRepository;
import com.note_awesome.services.user_profile_services.UsrProfileAbstractValidator;
import org.springframework.stereotype.Component;
import com.note_awesome.core.entities.note.UserProfile;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;

@Component
public class CreateUsrProfileValidator extends UsrProfileAbstractValidator {

    private final IUserProfileRepository userProfileRepository;
    private final IUserRepository userRepository;

    public final static class CreateUsrProfileValidatorError {
        public static final Error PROFILE_NAME_ALREADY_EXISTS = new Error("Profile name already exists", "profileName");
    }

    public CreateUsrProfileValidator(UsrProfileBasicValidator usrProfileBasicValidator, IUserProfileRepository userProfileRepository, IUserRepository userRepository) {
        super(usrProfileBasicValidator);
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Result<UserProfile, Error> validate(UserProfile userProfile) {
        var result = super.validate(userProfile);
        if (!result.isSuccess()) {
            return result;
        }

        if (userProfileExists(userProfile.getProfileName())) {
            return Result.failure(CreateUsrProfileValidatorError.PROFILE_NAME_ALREADY_EXISTS);
        }

        if (usernameExists(userProfile.getProfileName())) {
            return Result.failure(CreateUsrProfileValidatorError.PROFILE_NAME_ALREADY_EXISTS);
        }

        return Result.success(userProfile);
    }

    private boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean userProfileExists(String username) {
        return userProfileRepository.existsByProfileName(username);
    }
}
