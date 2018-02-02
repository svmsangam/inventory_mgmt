package com.inventory.core.model.dto;

import com.inventory.core.model.entity.CityInfo;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Created by dhiraj on 1/25/18.
 */
public class SubscriberDTO {

    private long subscriberId;

    private String fullName;

    private String contact;

    private String mobile;

    private String email;

    private long cityInfoId;

    private String cityName;

    private String street;

    private long serviceId;

    private String password;

    private String repassword;

    private long userId;

    private String username;

    private long createdById;

    private String createdByName;

    private UserType createdByType;

    private Status status;

    public long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(long subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCityInfoId() {
        return cityInfoId;
    }

    public void setCityInfoId(long cityInfoId) {
        this.cityInfoId = cityInfoId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public UserType getCreatedByType() {
        return createdByType;
    }

    public void setCreatedByType(UserType createdByType) {
        this.createdByType = createdByType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }
}
