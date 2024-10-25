package com.note_awesome.services.user_services;

import com.note_awesome.core.entities.note.User;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;

public interface ICreateUserService {

    Result<User, Error> create(User user);

    Result<User, Error> create(String username, String password);

    Result<User, Error> create(String username, String password, String location);

}
