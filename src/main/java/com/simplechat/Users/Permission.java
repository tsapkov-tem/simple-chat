package com.example.mongodb.spring.Models.Users;

public enum Permission {
    READ("read"),
    All("all");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
