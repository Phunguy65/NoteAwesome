package com.note_awesome.services;

import com.note_awesome.NoteAwesomeEnv;
import com.note_awesome.StageReadyEvent;
import com.note_awesome.core.entities.note.User;
import com.note_awesome.core.entities.note.UserProfile;
import com.note_awesome.models.SessionViewModel;
import com.note_awesome.services.authentication_services.IAuthenticationService;
import com.note_awesome.services.user_profile_services.IUserProfileBaseService;
import com.note_awesome.services.user_services.IUserService;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;

// for demo purposes
@Component
public class FakeAppService implements IAppBaseService {
    protected final ApplicationContext applicationContext;
    protected final IUserService userService;
    protected final IUserProfileBaseService userProfileService;
    protected final IAuthenticationService authenticationService;

    private final User TEST_USER = new User();
    private final UserProfile TEST_USER_PROFILE = new UserProfile();
    private final Logger logger = LoggerFactory.getLogger(FakeAppService.class);
    private boolean isInitialized = false;

    private final SessionViewModel currentSession;

    public FakeAppService(PasswordEncoder passwordEncoder, IUserService userService, IUserProfileBaseService userProfileService, IAuthenticationService authenticationService, SessionViewModel currentSession, ApplicationContext applicationContext) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.userProfileService = userProfileService;
        this.applicationContext = applicationContext;
        this.currentSession = currentSession;
    }

    @Override
    public void initialize() throws IOException {
        // Do nothing

        if (isInitialized) {
            return;
        }

        generateTestData(TEST_USER, TEST_USER_PROFILE);
        var currentUser = this.authenticationService.login(TEST_USER.getUsername(), "test");
        this.currentSession.setCurrentUserId(currentUser.getValue().getId());
        var longIds = this.userProfileService.getUsrProfQueryServices().getUserProfiles(currentUser.getValue().getId()).getValue().stream().map(SimpleLongProperty::new).toList();
        this.currentSession.getUsrProfIds().addAll(FXCollections.observableArrayList(longIds));
        this.currentSession.setCurrentUsrProfId(longIds.get(0).get());
        isInitialized = true;

        this.applicationContext.publishEvent(new StageReadyEvent(new Stage()));
    }

    private void generateTestData(User user, UserProfile userProfile) {
        TEST_USER.setUsername("test");
        TEST_USER.setPassword("test");
        TEST_USER.setUserLocation(NoteAwesomeEnv.USER_DATA_FOLDER_PATH);

        TEST_USER_PROFILE.setProfileName("test_profile");
        TEST_USER_PROFILE.setProfileSetting(null);
        TEST_USER_PROFILE.setProfileLocation(TEST_USER.getUserLocation());

        var can_create_new_user = this.userService.getCreateUserService().create(TEST_USER);
        if (!can_create_new_user.isSuccess()) {
            logger.error("Error creating test user");
            return;
        }

        var can_create_new_profile = this.userProfileService.getCreateUsrProfService().create(TEST_USER_PROFILE.getProfileName(), TEST_USER_PROFILE.getProfileLocation(), can_create_new_user.getValue().getId());
        if (!can_create_new_profile.isSuccess()) {
            logger.error("Error creating test user profile");
            return;
        }


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

