package com.note_awesome.services.user_profile_services;

import com.note_awesome.extensions.Error;
import com.note_awesome.extensions.Result;

import java.util.List;

public interface IUsrProfQueryService {
    Result<List<Long>, Error> getUserProfiles(long id);
} 
