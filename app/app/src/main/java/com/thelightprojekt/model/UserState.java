package com.thelightprojekt.model;

import com.thelightprojekt.model.data.customer.UserInfo;

public class UserState {
    private static UserState instance;
    private UserInfo user;

    private UserState() {}

    public static UserState getInstance() {
        if (instance == null) {
            instance = new UserState();
        }
        return instance;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}

