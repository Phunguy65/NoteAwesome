package com.note_awesome.models.entities.note;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    @Column(name = "user_profile_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "profile_name", nullable = false, unique = true, length = 50)
    private String profileName;
    
    @Column(name = "profile_picture_url", nullable = true, unique = false, length = 100)
    private String profilePictureUrl;
    
    @Column(name = "profile_location_url", nullable = true, unique = false, length = 100)
    private String profileLocationUrl;
    
    @Column(name = "last_used", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date lastUsed;
    
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private ProfileSetting profileSetting;
    
}
