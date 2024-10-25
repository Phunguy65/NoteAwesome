package com.note_awesome.services.user_profile_services;

import com.note_awesome.core.entities.note.UserProfile;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;

public interface ICreateUsrProfService {
    Result<UserProfile, Error> create(UserProfile userProfile);

    Result<UserProfile, Error> create(String profileName, String profileLocationUrl, long userId);

    Result<UserProfile, Error> create(String profileName, String profileLocationUrl, long userId, long profileSettingId);
    
}
