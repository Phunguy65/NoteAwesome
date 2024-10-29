package com.note_awesome.services.user_profile_services;

import com.note_awesome.core.entities.note.UserProfile;
import com.note_awesome.core.repositories.note.IUserProfileRepository;
import com.note_awesome.extensions.Result;
import com.note_awesome.extensions.Error;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsrProfQueryService implements IUsrProfQueryService {
    private final IUserProfileRepository userRepository;

    public UsrProfQueryService(IUserProfileRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Result<List<Long>, Error> getUserProfiles(long id) {
        List<UserProfile> userProfiles = userRepository.getByUser_IdOrderByLastUsedDesc(id);
        return Result.success(userProfiles.stream().map(UserProfile::getId).toList());
    }
}
