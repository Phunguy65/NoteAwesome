package com.note_awesome.services.user_services.validators;

import com.note_awesome.core.entities.note.User;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;
import com.note_awesome.extensions.IValidator;

public interface IUserBasicValidator extends IValidator<User, Error> {
    @Override
    public Result<User, Error> validate(User user);
}
