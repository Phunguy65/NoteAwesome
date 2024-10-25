package com.note_awesome.services.user_services;

import com.note_awesome.core.entities.note.User;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;
import com.note_awesome.services.user_services.validators.IUserBasicValidator;

public abstract class UserAbstractValidator implements IUserBasicValidator {
    private final IUserBasicValidator userBasicValidator;

    public UserAbstractValidator(IUserBasicValidator userBasicValidator) {
        this.userBasicValidator = userBasicValidator;
    }

    @Override
    public Result<User, Error> validate(User user) {
        return userBasicValidator.validate(user);
    }
}