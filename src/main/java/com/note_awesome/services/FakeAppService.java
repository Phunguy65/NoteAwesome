package com.note_awesome.services;

import com.note_awesome.NoteAwesomeEnv;
import com.note_awesome.StageReadyEvent;
import com.note_awesome.core.entities.note.User;
import com.note_awesome.core.entities.note.UserProfile;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

// for demo purposes
@Component
public class FakeAppService implements IAppBaseService {
    protected final ApplicationContext applicationContext;
    protected final PasswordEncoder passwordEncoder;
    protected final IUserService userService;
    protected final IUserProfileService userProfileService;
    protected final IAuthenticationService authenticationService;

    private final User TEST_USER = new User();
    private final UserProfile TEST_USER_PROFILE = new UserProfile();

    private boolean isInitialized = false;

    public FakeAppService(PasswordEncoder passwordEncoder, IUserService userService, IUserProfileService userProfileService, IAuthenticationService authenticationService, ApplicationContext applicationContext) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.userProfileService = userProfileService;
        this.applicationContext = applicationContext;
        TEST_USER.setUsername("test");
        TEST_USER.setPasswordHash(passwordEncoder.encode("test"));
        TEST_USER.setUserLocation(NoteAwesomeEnv.USER_DATA_FOLDER_PATH);

        TEST_USER_PROFILE.setUserData(TEST_USER);
        TEST_USER_PROFILE.setActive(true);
        TEST_USER_PROFILE.setProfileName("test_profile");
        TEST_USER_PROFILE.setProfileSetting(null);

        String pathToProfile = Path.of(NoteAwesomeEnv.USER_DATA_FOLDER_PATH, TEST_USER_PROFILE.getProfileName()).toString();
        TEST_USER_PROFILE.setProfileLocationUrl(pathToProfile);
        TEST_USER.setProfiles(List.of(TEST_USER_PROFILE));

    }

    @Override
    public void initialize() throws IOException {
        // Do nothing

        if (isInitialized) {
            return;
        }

        generateTestData(TEST_USER, TEST_USER_PROFILE);
        this.authenticationService.login(TEST_USER.getUsername(), "test");

        isInitialized = true;

        this.applicationContext.publishEvent(new StageReadyEvent(new Stage()));
    }

    private void generateTestData(User user, UserProfile userProfile) {
        userService.createUser(user);
        userProfileService.createUserProfile(userProfile);

    }

    @Override
    public void refresh() {
        // TODO: Implement refresh
    }

    public User getTestUser() {
        return TEST_USER;
    }

    public UserProfile getTestUserProfile() {
        return TEST_USER_PROFILE;
    }


}

