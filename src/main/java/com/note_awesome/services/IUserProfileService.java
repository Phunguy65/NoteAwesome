package com.note_awesome.services;

import com.note_awesome.core.entities.note.UserProfile;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;

public interface IUserProfileService {

    Result<UserProfile, Error> createUserProfile(UserProfile userProfile);

    Result<UserProfile, Error> getUserProfile(UserProfile userProfile);
}
