package com.note_awesome.models.entities.note;

import jakarta.persistence.*;

@Entity
@Table(name = "profile_settings")
public class ProfileSetting {
    @Id
    @Column(name = "profile_setting_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "theme", nullable = false, unique = false, length = 50)
    private String theme;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;
    
}