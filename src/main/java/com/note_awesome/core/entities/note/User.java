package com.note_awesome.core.entities.note;

import com.note_awesome.core.entities.AuditorEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User extends AuditorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "user_name", nullable = false, unique = false, length = 50)
    private String username;

    @Column(name = "password_hash", nullable = true, unique = false, length = 100)
    private String passwordHash;

    @Column(name = "user_location", nullable = false, unique = true, length = 255)
    private String userLocation;
    

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public List<UserProfile> getProfiles() {
        return profiles;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public void setProfiles(List<UserProfile> profiles) {
        this.profiles = profiles;
    }

    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL)
    private List<UserProfile> profiles;

}
