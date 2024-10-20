package com.note_awesome.services;

import com.note_awesome.core.entities.note.User;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;

public interface IUserService {

    Result<User, Error> createUser(User user);

    Result<User, Error> getUser(User user);
}
