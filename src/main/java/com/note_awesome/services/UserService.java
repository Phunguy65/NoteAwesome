package com.note_awesome.services;

import com.note_awesome.NoteAwesomeEnv;
import com.note_awesome.core.entities.note.User;
import com.note_awesome.extensions.Result;
import com.note_awesome.core.repositories.note.IUserRepository;
import com.note_awesome.extensions.Error;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public static class UserServiceError {
        public static final Error USER_NOT_FOUND(String name) {
            return new Error("User not found", "User not found by name: " + name);
        }

        public static final Error USER_ALREADY_EXISTS(String name) {
            return new Error("User already exists", "User already exists by name: " + name);
        }

        public static final Error USER_LOCATION_CANNOT_BE_CREATED(String location) {
            return new Error("User location cannot be created", "User location cannot be created: " + location);
        }
    }

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Result<User, Error> createUser(User user) {
        var userExists = this.getUser(user);

        if (userExists.isSuccess()) {
            return Result.failure(UserServiceError.USER_ALREADY_EXISTS(user.getUsername()));
        }

        try {
            Files.createDirectories(Path.of(NoteAwesomeEnv.USER_DATA_FOLDER_PATH, user.getUsername()));
        } catch (IOException e) {
            return Result.failure(UserServiceError.USER_LOCATION_CANNOT_BE_CREATED(user.getUsername()));
        }

        return Result.success(userRepository.save(user));
    }

    @Override
    public Result<User, Error> getUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        var userOptional = userRepository.findByUsername(user.getUsername());

        return userOptional.<Result<User, Error>>map(Result::success).orElseGet(() -> Result.failure(UserServiceError.USER_NOT_FOUND(user.getUsername())));

    }
}
