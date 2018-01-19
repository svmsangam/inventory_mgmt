package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.UserType;
import com.inventory.core.model.enumconstant.ProfileAssociateType;
import com.inventory.core.model.enumconstant.Status;

import javax.persistence.*;

@Entity
@Table(name = "invuser")
public class User extends AbstractEntity<Long> {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private UserType userType;

    private String authority;

    @Column(nullable = false)
    private Status status;

    @Column
    private String secret_key;

    private boolean enabled;

    @ManyToOne(fetch = FetchType.EAGER)
    private StoreInfo storeInfo;

    private long associateProfileId;

    private String fcmKey;

    public String getFcmKey() {
        return fcmKey;
    }

    public void setFcmKey(String fcmKey) {
        this.fcmKey = fcmKey;
    }

    private ProfileAssociateType profileAssociateType;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    public long getAssociateProfileId() {
        return associateProfileId;
    }

    public void setAssociateProfileId(long associateProfileId) {
        this.associateProfileId = associateProfileId;
    }

    public ProfileAssociateType getProfileAssociateType() {
        return profileAssociateType;
    }

    public void setProfileAssociateType(ProfileAssociateType profileAssociateType) {
        this.profileAssociateType = profileAssociateType;
    }
}
