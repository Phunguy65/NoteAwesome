package com.note_awesome.services.user_profile_services;

import com.note_awesome.core.entities.note.UserProfile;
import com.note_awesome.extensions.Result;
import com.note_awesome.services.user_profile_services.validators.IUsrProfileBasicValidator;
import com.note_awesome.extensions.Error;

public abstract class UsrProfileAbstractValidator implements IUsrProfileBasicValidator {

    private final IUsrProfileBasicValidator usrProfileBasicValidator;

    public UsrProfileAbstractValidator(IUsrProfileBasicValidator usrProfileBasicValidator) {
        this.usrProfileBasicValidator = usrProfileBasicValidator;
    }

    @Override
    public Result<UserProfile, Error> validate(UserProfile userProfile) {
        return usrProfileBasicValidator.validate(userProfile);
    }
}
