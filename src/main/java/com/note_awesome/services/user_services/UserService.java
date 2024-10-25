package com.note_awesome.services.user_services;

import org.springframework.stereotype.Component;

@Component
public class UserService implements IUserService {

    private final ICreateUserService createUserService;

    public UserService(ICreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    @Override
    public ICreateUserService getCreateUserService() {
        return createUserService;
    }
}
