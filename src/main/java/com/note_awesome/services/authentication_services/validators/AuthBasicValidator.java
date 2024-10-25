package com.note_awesome.services.authentication_services.validators;

import com.note_awesome.core.entities.note.User;
import com.note_awesome.core.repositories.note.IUserRepository;
import com.note_awesome.extensions.Error;
import com.note_awesome.extensions.Result;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthBasicValidator implements IAuthBasicValidator {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthBasicValidator(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static class AuthenticationValidatorError {
        public static final com.note_awesome.extensions.Error USER_NOT_FOUND_BY_NAME = new com.note_awesome.extensions.Error("User not found", "User name not found");
        public static final com.note_awesome.extensions.Error INVALID_PASSWORD = new com.note_awesome.extensions.Error("Invalid password", "Password is invalid");
    }

    @Override
    public Result<User, Error> validate(User user) {
        var userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb.isEmpty()) {
            return Result.failure(AuthenticationValidatorError.USER_NOT_FOUND_BY_NAME);
        }

        if (!passwordEncoder.matches(user.getPassword(), userFromDb.get().getPassword())) {
            return Result.failure(AuthenticationValidatorError.INVALID_PASSWORD);
        }

        return Result.success(userFromDb.get());

    }
}
