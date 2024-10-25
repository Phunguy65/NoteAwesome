package com.note_awesome.core.entities.note;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    @Column(name = "user_profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "profile_name", nullable = false, unique = true, length = 50)
    private String profileName;

    @Column(name = "profile_location_url", nullable = true, unique = false, length = 255)
    private String profileLocationUrl;

    @Column(name = "last_used", nullable = false)
    @CreationTimestamp
    private Date lastUsed;

    public UserProfile() {
    }

    public UserProfile(String profileName, String profileLocationUrl, Date lastUsed, User userData, ProfileSetting profileSetting, List<NoteContent> noteContents) {
        this.profileName = profileName;
        this.profileLocationUrl = profileLocationUrl;
        this.lastUsed = lastUsed;
        this.userData = userData;
        this.profileSetting = profileSetting;
        this.noteContents = noteContents;
    }

    public UserProfile(String profileName, String profileLocationUrl, Date lastUsed, User userData, ProfileSetting profileSetting) {
        this.profileName = profileName;
        this.profileLocationUrl = profileLocationUrl;
        this.lastUsed = lastUsed;
        this.userData = userData;
        this.profileSetting = profileSetting;
    }

    public UserProfile(String profileName, String profileLocationUrl, Date lastUsed, User userData) {
        this.profileName = profileName;
        this.profileLocationUrl = profileLocationUrl;
        this.lastUsed = lastUsed;
        this.userData = userData;
    }
    

    public long getId() {
        return id;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileLocationUrl() {
        return profileLocationUrl;
    }

    public void setProfileLocationUrl(String profileLocationUrl) {
        this.profileLocationUrl = profileLocationUrl;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    public User getUserData() {
        return userData;
    }

    public void setUserData(User user) {
        this.userData = user;
    }

    public ProfileSetting getProfileSetting() {
        return profileSetting;
    }

    public void setProfileSetting(ProfileSetting profileSetting) {
        this.profileSetting = profileSetting;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userData;

    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_setting_id", nullable = true)
    private ProfileSetting profileSetting;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private List<NoteContent> noteContents;

}
