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

    @Column(name = "user_name", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password_hash", nullable = false, unique = false, length = 100)
    private String password;

    @Column(name = "user_location", nullable = false, unique = true, length = 255)
    private String userLocation;

    public User() {
    }

    public User(String username, String password, String userLocation) {
        this.username = username;
        this.password = password;
        this.userLocation = userLocation;
    }


    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordHash) {
        this.password = passwordHash;
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserProfile> profiles;

}
