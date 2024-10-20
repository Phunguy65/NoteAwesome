package com.note_awesome.services;

import com.note_awesome.core.entities.note.User;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;

public interface IAuthenticationService {
    Result<User, Error> login(String username, String password);

    void logout();
}
