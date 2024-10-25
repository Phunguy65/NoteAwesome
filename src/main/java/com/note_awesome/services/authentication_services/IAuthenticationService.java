package com.note_awesome.services.authentication_services;

import com.note_awesome.core.entities.note.User;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;

public interface IAuthenticationService {
    Result<User, Error> login(String username, String password);

    Result<User, Error> login(User user);

    void logout();
}
