package com.kob.backend.service.impl.util;

import com.kob.backend.pojo.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;


public class UserDetailImpl implements UserDetails {
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // getter for user
    public User getUser() {
        return user;
    }

    // setter for user
    public void setUser(User user) {
        this.user = user;
    }

    // All args constructor
    public UserDetailImpl(User user) {
        this.user = user;
    }

    // No args constructor
    public UserDetailImpl() {}

    // toString, equals, hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDetailImpl)) return false;
        UserDetailImpl that = (UserDetailImpl) o;
        return Objects.equals(getUser(), that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser());
    }

    @Override
    public String toString() {
        return "UserDetailImpl{" +
                "user=" + user +
                '}';
    }
}
