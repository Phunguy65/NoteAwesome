package com.note_awesome.services.user_profile_services;

import org.springframework.stereotype.Component;

@Component
public class UserProfileService implements IUserProfileBaseService {
    private final ICreateUsrProfService createUsrProfService;

    public UserProfileService(ICreateUsrProfService createUsrProfService) {
        this.createUsrProfService = createUsrProfService;
    }

    @Override
    public ICreateUsrProfService getCreateUsrProfService() {
        return createUsrProfService;
    }
}
