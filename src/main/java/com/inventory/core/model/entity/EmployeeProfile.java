package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.EmployeeStatus;
import com.inventory.core.model.enumconstant.Status;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

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

    private Date startingDate;

    private Date endingDate;

    private Designation designation;

    private EmployeeStatus employeeStatus;

    private User createdBy;

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

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
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
}
