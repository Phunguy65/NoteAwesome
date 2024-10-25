package com.note_awesome.services.authentication_services.validators;

import com.note_awesome.core.entities.note.User;
import com.note_awesome.core.repositories.note.IUserRepository;
import com.note_awesome.extensions.Error;
import com.note_awesome.extensions.Result;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthValidator extends AuthAbstractValidator {

    public AuthValidator(AuthBasicValidator validator) {
        super(validator);
    }
}
