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
    private Long id;

    @Column(name = "profile_name", nullable = false, unique = true, length = 50)
    private String profileName;

    @Column(name = "profile_location", nullable = true, unique = false, length = 255)
    private String profileLocation;

    @Column(name = "last_used", nullable = false)
    @CreationTimestamp
    private Date lastUsed;

    public UserProfile() {
    }

    public UserProfile(String profileName, String profileLocation, Date lastUsed, User user, ProfileSetting profileSetting, List<NoteContent> noteContents) {
        this.profileName = profileName;
        this.profileLocation = profileLocation;
        this.lastUsed = lastUsed;
        this.user = user;
        this.profileSetting = profileSetting;
        this.noteContents = noteContents;
    }

    public UserProfile(String profileName, String profileLocation, Date lastUsed, User user, ProfileSetting profileSetting) {
        this.profileName = profileName;
        this.profileLocation = profileLocation;
        this.lastUsed = lastUsed;
        this.user = user;
        this.profileSetting = profileSetting;
    }

    public UserProfile(String profileName, String profileLocation, Date lastUsed, User user) {
        this.profileName = profileName;
        this.profileLocation = profileLocation;
        this.lastUsed = lastUsed;
        this.user = user;
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

    public String getProfileLocation() {
        return profileLocation;
    }

    public void setProfileLocation(String profileLocationUrl) {
        this.profileLocation = profileLocationUrl;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProfileSetting getProfileSetting() {
        return profileSetting;
    }

    public void setProfileSetting(ProfileSetting profileSetting) {
        this.profileSetting = profileSetting;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_setting_id", nullable = true)
    private ProfileSetting profileSetting;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private List<NoteContent> noteContents;

}
