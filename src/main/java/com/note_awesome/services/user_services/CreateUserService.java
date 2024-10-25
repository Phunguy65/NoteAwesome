package com.note_awesome.services.user_services;

import com.note_awesome.NoteAwesomeEnv;
import com.note_awesome.core.entities.note.User;
import com.note_awesome.extensions.Result;
import com.note_awesome.core.repositories.note.IUserRepository;
import com.note_awesome.extensions.Error;
import com.note_awesome.services.user_services.validators.CreateUserValidator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Component
public class CreateUserService implements ICreateUserService {
    private final IUserRepository userRepository;
    private final UserAbstractValidator userAbstractValidator;
    private final PasswordEncoder passwordEncoder;

    public CreateUserService(IUserRepository userRepository, CreateUserValidator userAbstractValidator, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userAbstractValidator = userAbstractValidator;
        this.passwordEncoder = passwordEncoder;
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

    private String getPasswordHash(String password) {
        return passwordEncoder.encode(password);
    }

    private Result<User, Error> save(User user) {
        try {
            var savedUser = userRepository.save(user);
            return Result.success(savedUser);
        } catch (Exception e) {
            return Result.failure(new Error("Error saving user", "Error saving user"));
        }
    }

    private Result<Path, Error> createUserLocation(User user) {
        var root = user.getUserLocation();
        var desLocation = Path.of(root, this.generateUniqueName(Optional.empty()));
        int count = 0;
        while (Files.exists(desLocation) && count < 2) {
            desLocation = Path.of(root, this.generateUniqueName(Optional.empty()));
            count++;
        }
        try {
            Files.createDirectory(desLocation);
        } catch (IOException e) {
            try {
                Files.deleteIfExists(desLocation);
            } catch (IOException ex) {
                return Result.failure(new Error("Error creating user location", "Error creating user location"));
            }
            return Result.failure(new Error("Error creating user location", "Error creating user location"));
        }
        return Result.success(desLocation);
    }

    private Result<User, Error> procedure(User user) {
        var result = userAbstractValidator.validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        var userLocationResult = createUserLocation(user);

        if (!userLocationResult.isSuccess()) {
            return Result.failure(userLocationResult.getError());
        }

        var newUser = new User(user.getUsername(), getPasswordHash(user.getPassword()), userLocationResult.getValue().toString());

        return Result.success(newUser);
    }

    @Override
    @Transactional
    public Result<User, Error> create(User user) {
        return procedure(user).Match(
                this::save,
                Result::failure
        );
    }

    @Override
    @Transactional
    public Result<User, Error> create(String username, String password) {
        return create(new User(username, password, NoteAwesomeEnv.USER_DATA_FOLDER_PATH));
    }

    @Override
    @Transactional
    public Result<User, Error> create(String username, String password, String location) {
        return create(new User(username, password, location));
    }

}
