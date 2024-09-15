package com.note_awesome.models.entities.note;

import com.note_awesome.models.entities.AuditorEntity;
import jakarta.persistence.*;

import java.util.Set;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Set<UserProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<UserProfile> profiles) {
        this.profiles = profiles;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserProfile> profiles;
    
}
