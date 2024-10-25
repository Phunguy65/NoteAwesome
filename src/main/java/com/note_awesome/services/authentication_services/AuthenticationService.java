package com.note_awesome.services.authentication_services;

import com.note_awesome.core.entities.note.User;
import com.note_awesome.core.repositories.note.IUserRepository;
import com.note_awesome.extensions.Error;
import com.note_awesome.extensions.Result;
import com.note_awesome.services.authentication_services.validators.AuthAbstractValidator;
import com.note_awesome.services.authentication_services.validators.AuthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService implements IAuthenticationService {

    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthAbstractValidator validator;

    @Autowired
    public AuthenticationService(IUserRepository userRepository, PasswordEncoder passwordEncoder, AuthValidator validator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    @Override
    public Result<User, Error> login(String username, String password) {
        var usr = new User();
        usr.setUsername(username);
        usr.setPassword(password);

        return login(usr);
    }

    @Override
    public Result<User, Error> login(User user) {
        return validator.validate(user);
    }

    @Override
    public void logout() {

    }
}

