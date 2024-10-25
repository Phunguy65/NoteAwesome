package com.note_awesome.services.user_services.validators;

import com.note_awesome.core.entities.note.User;
import com.note_awesome.core.repositories.note.IUserRepository;
import com.note_awesome.extensions.Result;
import com.note_awesome.services.user_services.UserAbstractValidator;
import org.springframework.stereotype.Component;
import com.note_awesome.extensions.Error;

@Component
public class CreateUserValidator extends UserAbstractValidator {
    private final IUserRepository userRepository;

    public static class CreateUserValidatorError {
        public static final Error USERNAME_ALREADY_EXISTS = new Error("Username already exists", "username");
    }

    public CreateUserValidator(IUserRepository userRepository, IUserBasicValidator userBasicValidator) {
        super(userBasicValidator);
        this.userRepository = userRepository;
    }

    @Override
    public Result<User, Error> validate(User user) {
        var result = super.validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (usernameExists(user.getUsername())) {
            return Result.failure(CreateUserValidatorError.USERNAME_ALREADY_EXISTS);
        }

        return Result.success(user);
    }

    private boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
