package com.inventory.web.session;

import java.io.Serializable;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsWrapper implements UserDetails, Serializable, Comparable<UserDetailsWrapper> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final Collection<GrantedAuthority> authorities;
    private final User user;
    private String remoteAddress;

    public UserDetailsWrapper(User user, Collection<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public UserDetailsWrapper(User user, Collection<GrantedAuthority> authorities, String remoteAddress) {
        this.user = user;
        this.authorities = authorities;
        this.remoteAddress = remoteAddress;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.getStatus().equals(Status.DELETED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getStatus().equals(Status.ACTIVE);
        // return !user.isDisabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getStatus().equals(Status.ACTIVE);
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus().equals(Status.ACTIVE);
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserDetailsWrapper) {
            return ((UserDetailsWrapper) obj).getUser().getId().equals(user.getId());
        }
        return false;
    }

    @Override
    public int compareTo(UserDetailsWrapper o) {
        return 0;
    }

    @Override
    public String toString() {
        return "user" + user.getId();
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(user.getId() + "");
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

}

