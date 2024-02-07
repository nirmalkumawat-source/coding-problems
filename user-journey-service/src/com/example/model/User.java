package com.example.model;

import java.util.UUID;

public class User {

    String userId;

    String userName;

    public User(String userName) {
        this.userId = UUID.randomUUID().toString();
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
