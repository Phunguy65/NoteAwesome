package com.note_awesome.services.user_profile_services.validators;

import com.note_awesome.core.entities.note.UserProfile;
import com.note_awesome.extensions.Error;
import com.note_awesome.extensions.IValidator;
import com.note_awesome.extensions.Result;

public interface IUsrProfileBasicValidator extends IValidator<UserProfile, Error> {
    @Override
    public Result<UserProfile, Error> validate(UserProfile userProfile);
}
