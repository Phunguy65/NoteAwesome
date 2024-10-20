package com.note_awesome.services;

import com.note_awesome.core.entities.note.User;
import com.note_awesome.core.repositories.note.IUserRepository;
import com.note_awesome.extensions.Error;
import com.note_awesome.extensions.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.note_awesome.extensions.Result.failure;

@Component
public class AuthenticationService implements IAuthenticationService {

    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public static class AuthenticationError {
        public static final Error USER_NOT_FOUND_BY_NAME = new Error("User not found", "User name not found");
        public static final Error INVALID_PASSWORD = new Error("Invalid password", "Password is invalid");
    }

    @Autowired
    public AuthenticationService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Result<User, Error> login(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return failure(AuthenticationError.USER_NOT_FOUND_BY_NAME);
        }

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            return failure(AuthenticationError.INVALID_PASSWORD);
        }

        CurrentUser.getInstance(user);

        return Result.success(user);
    }

    @Override
    public void logout() {

    }
}

