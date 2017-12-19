package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.*;

/**
 * Created by dhiraj on 8/1/17.
 */
@Entity
@Table(name = "invemployeeprofile")
public class EmployeeProfile extends AbstractEntity<Long> {

    private String firstName;

    private String middleName;

    private String lastName;

    private String permanentAddress;

    private String temporaryAddress;

    private CityInfo permanentCity;

    private CityInfo temporaryCity;

    private String citizenShipNo;

    private CityInfo citizenShipCity;

    private String mobileNumber;

    private String emergencyCantact;

    private String email;

    private User createdBy;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    private Status status;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getTemporaryAddress() {
        return temporaryAddress;
    }

    public void setTemporaryAddress(String temporaryAddress) {
        this.temporaryAddress = temporaryAddress;
    }

    public CityInfo getPermanentCity() {
        return permanentCity;
    }

    public void setPermanentCity(CityInfo permanentCity) {
        this.permanentCity = permanentCity;
    }

    public CityInfo getTemporaryCity() {
        return temporaryCity;
    }

    public void setTemporaryCity(CityInfo temporaryCity) {
        this.temporaryCity = temporaryCity;
    }

    public String getCitizenShipNo() {
        return citizenShipNo;
    }

    public void setCitizenShipNo(String citizenShipNo) {
        this.citizenShipNo = citizenShipNo;
    }

    public CityInfo getCitizenShipCity() {
        return citizenShipCity;
    }

    public void setCitizenShipCity(CityInfo citizenShipCity) {
        this.citizenShipCity = citizenShipCity;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmergencyCantact() {
        return emergencyCantact;
    }

    public void setEmergencyCantact(String emergencyCantact) {
        this.emergencyCantact = emergencyCantact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
