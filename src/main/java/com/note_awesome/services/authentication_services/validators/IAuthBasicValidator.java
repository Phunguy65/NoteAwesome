package com.note_awesome.services.authentication_services.validators;

import com.note_awesome.core.entities.note.User;
import com.note_awesome.extensions.IValidator;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;

public interface IAuthBasicValidator extends IValidator<User, Error> {
    @Override
    Result<User, Error> validate(User user);
}
