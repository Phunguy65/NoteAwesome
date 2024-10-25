package com.note_awesome.services.authentication_services.validators;

import com.note_awesome.core.entities.note.User;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;

public abstract class AuthAbstractValidator implements IAuthBasicValidator {
    private final IAuthBasicValidator validator;

    public AuthAbstractValidator(IAuthBasicValidator validator) {
        this.validator = validator;
    }

    @Override
    public Result<User, Error> validate(User user) {
        return validator.validate(user);
    }
}
