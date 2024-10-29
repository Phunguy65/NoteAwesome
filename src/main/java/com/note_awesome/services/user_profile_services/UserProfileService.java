package com.note_awesome.services.user_profile_services;

import org.springframework.stereotype.Component;

@Component
public class UserProfileService implements IUserProfileBaseService {
    private final ICreateUsrProfService createUsrProfService;
    private final IUsrProfQueryService usrProfQueryService;

    public UserProfileService(ICreateUsrProfService createUsrProfService, IUsrProfQueryService usrProfQueryService) {
        this.createUsrProfService = createUsrProfService;
        this.usrProfQueryService = usrProfQueryService;
    }

    @Override
    public ICreateUsrProfService getCreateUsrProfService() {
        return createUsrProfService;
    }

    @Override
    public IUsrProfQueryService getUsrProfQueryServices() {
        return usrProfQueryService;
    }
}
