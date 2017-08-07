package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.EmployeeStatus;
import com.inventory.core.model.enumconstant.Status;

import java.util.Date;

/**
 * Created by dhiraj on 8/6/17.
 */
public class EmployeeProfileDTO {

    private Long employeeProfileId;

    private String firstName;

    private String middleName;

    private String lastName;

    private String permanentAddress;

    private String temporaryAddress;

    private CityInfoDTO permanentCity;

    private Long permanentCityId;

    private CityInfoDTO temporaryCity;

    private Long temporaryCityId;

    private String citizenShipNo;

    private CityInfoDTO citizenShipCity;

    private Long citizenShipCityId;

    private String mobileNumber;

    private String emergencyCantact;

    private String email;

    private Date startingDate;

    private Date endingDate;

    private DesignationInfoDTO designationInfoDTO;

    private Long designationId;

    private EmployeeStatus employeeStatus;

    private long createdById;

    private String createdByName;

    private Status status;

    public Long getEmployeeProfileId() {
        return employeeProfileId;
    }

    public void setEmployeeProfileId(Long employeeProfileId) {
        this.employeeProfileId = employeeProfileId;
    }

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

    public CityInfoDTO getPermanentCity() {
        return permanentCity;
    }

    public void setPermanentCity(CityInfoDTO permanentCity) {
        this.permanentCity = permanentCity;
    }

    public Long getPermanentCityId() {
        return permanentCityId;
    }

    public void setPermanentCityId(Long permanentCityId) {
        this.permanentCityId = permanentCityId;
    }

    public CityInfoDTO getTemporaryCity() {
        return temporaryCity;
    }

    public void setTemporaryCity(CityInfoDTO temporaryCity) {
        this.temporaryCity = temporaryCity;
    }

    public Long getTemporaryCityId() {
        return temporaryCityId;
    }

    public void setTemporaryCityId(Long temporaryCityId) {
        this.temporaryCityId = temporaryCityId;
    }

    public String getCitizenShipNo() {
        return citizenShipNo;
    }

    public void setCitizenShipNo(String citizenShipNo) {
        this.citizenShipNo = citizenShipNo;
    }

    public CityInfoDTO getCitizenShipCity() {
        return citizenShipCity;
    }

    public void setCitizenShipCity(CityInfoDTO citizenShipCity) {
        this.citizenShipCity = citizenShipCity;
    }

    public Long getCitizenShipCityId() {
        return citizenShipCityId;
    }

    public void setCitizenShipCityId(Long citizenShipCityId) {
        this.citizenShipCityId = citizenShipCityId;
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

    public DesignationInfoDTO getDesignationInfoDTO() {
        return designationInfoDTO;
    }

    public void setDesignationInfoDTO(DesignationInfoDTO designationInfoDTO) {
        this.designationInfoDTO = designationInfoDTO;
    }

    public Long getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Long designationId) {
        this.designationId = designationId;
    }

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
