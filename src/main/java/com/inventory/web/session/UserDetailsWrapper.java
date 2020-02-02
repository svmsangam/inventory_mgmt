package com.inventory.web.session;


import com.inventory.core.api.iapi.ISubscriberServiceApi;
import com.inventory.core.model.dto.SubscriberServiceDTO;
import com.inventory.core.model.entity.ServiceInfo;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.core.util.DateParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class UserDetailsWrapper implements UserDetails, Serializable, Comparable<UserDetailsWrapper> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final Collection<GrantedAuthority> authorities;
    private final User user;
    private String remoteAddress;
    private SubscriberServiceDTO subscriberServiceDTO;

    private ISubscriberServiceApi subscriberServiceApi;

    private UserRepository userRepository;

    public UserDetailsWrapper(User user, Collection<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public UserDetailsWrapper(User user, Collection<GrantedAuthority> authorities, String remoteAddress, UserRepository userRepository, ISubscriberServiceApi subscriberServiceApi) {
        this.user = user;
        this.authorities = authorities;
        this.remoteAddress = remoteAddress;
        this.userRepository = userRepository;
        this.subscriberServiceApi = subscriberServiceApi;
    }

    public UserDetailsWrapper(User user, Collection<GrantedAuthority> authorities, String remoteAddress, UserRepository userRepository, ISubscriberServiceApi subscriberServiceApi, SubscriberServiceDTO subscriberServiceDTO) {
        this.user = user;
        this.authorities = authorities;
        this.remoteAddress = remoteAddress;
        this.userRepository = userRepository;
        this.subscriberServiceApi = subscriberServiceApi;
        this.subscriberServiceDTO = subscriberServiceDTO;
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

        return checkAccount(user.getUsername());

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

    private boolean checkAccount(String username) {

        try {

            User user = userRepository.findByUsername(username);

            if (!user.getStatus().equals(Status.ACTIVE)) {
                return false;
            }

            if (user.getUserType().equals(UserType.SYSTEM)) {
                return true;
            }

            if (subscriberServiceDTO == null) {
                return false;
            }

            return !DateParseUtil.validateBeforeOrEqualCurrentDate(subscriberServiceDTO.getExpireOn());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        return new Date(cal.getTime().getTime());
    }

    public SubscriberServiceDTO getSubscriberServiceDTO() {
        return subscriberServiceDTO;
    }
}

