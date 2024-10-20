package com.note_awesome.services;

import com.note_awesome.core.entities.note.User;

public final class CurrentUser {

    private static volatile CurrentUser currentUser;

    private final User user;

    private CurrentUser(User user) {
        this.user = user;
    }

    public static CurrentUser getInstance(User user) {
        CurrentUser result = currentUser;
        if (result != null) {
            return result;
        }
        synchronized (CurrentUser.class) {
            if (currentUser == null) {
                currentUser = new CurrentUser(user);
            }
            return currentUser;
        }
    }

    public static CurrentUser getInstance() {
        return currentUser;
    }

    public long getId() {
        if (user == null) {
            throw new NullPointerException("User is null");
        }
        return user.getId();
    }

    public String getUsername() {
        if (user == null) {
            throw new NullPointerException("User is null");
        }
        return user.getUsername();
    }

    public String getUserLocation() {
        if (user == null) {
            throw new NullPointerException("User is null");
        }
        return user.getUserLocation();
    }
}
