package com.example.spring_practice.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum UserRole {

    ADMIN(Set.of(Permission.CLIENT_USER, Permission.CLIENT_MODERATOR, Permission.CLIENT_ADMIN)),
    MODERATOR(Set.of(Permission.CLIENT_USER, Permission.CLIENT_MODERATOR)),
    USER(Set.of(Permission.CLIENT_USER));

    private final Set<Permission> permissions;

    UserRole(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
