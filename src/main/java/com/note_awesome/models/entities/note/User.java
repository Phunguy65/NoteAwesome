package com.note_awesome.models.entities.note;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;
    
    @Column(name = "user_name", nullable = false, unique = false, length = 50)
    private String username;
    
    @Column(name = "password_hash", nullable = false, unique = false, length = 100)
    private String passwordHash;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<UserProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<UserProfile> profiles) {
        this.profiles = profiles;
    }

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserProfile> profiles;
    
}
