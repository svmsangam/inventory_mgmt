package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.EmployeeStatus;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.web.multipart.MultipartFile;

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

    private long createdById;

    private String createdByName;

    private long userId;

    private String username;

    private Status status;

    private Integer version;

    private String photo;

    private MultipartFile file;

    private long leqId; // educationQualification Id

    private String leqTitle; // educationQualification Title

    private String leqCode; //educationQualification Code

    private String leqRemarks; // educationQualification Remarks

    private long ownerId;

    private String ownerName;

    private EmployeeStatus employeeStatus;

    private long designationId;

    private Date joinDate;

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public long getDesignationId() {
        return designationId;
    }

    public void setDesignationId(long designationId) {
        this.designationId = designationId;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public long getLeqId() {
        return leqId;
    }

    public void setLeqId(long leqId) {
        this.leqId = leqId;
    }

    public String getLeqTitle() {
        return leqTitle;
    }

    public void setLeqTitle(String leqTitle) {
        this.leqTitle = leqTitle;
    }

    public String getLeqCode() {
        return leqCode;
    }

    public void setLeqCode(String leqCode) {
        this.leqCode = leqCode;
    }

    public String getLeqRemarks() {
        return leqRemarks;
    }

    public void setLeqRemarks(String leqRemarks) {
        this.leqRemarks = leqRemarks;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

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
}
