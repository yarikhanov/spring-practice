package com.example.spring_practice.entity;

public enum Permission {

    CLIENT_USER("client:user"),
    CLIENT_MODERATOR("client:moderator"),
    CLIENT_ADMIN("client:admin");

    private final String permission;

    public String getPermission() {
        return permission;
    }

    Permission(String permission) {
        this.permission = permission;
    }
}
