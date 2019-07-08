package com.inventory.core.model.dto;

import com.inventory.core.model.entity.QualificationLevel;
import com.inventory.core.model.entity.StoreInfo;
import com.inventory.core.model.enumconstant.Status;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Created by dhiraj on 8/6/17.
 */
public class DesignationInfoDTO {

    private Long designationId;

    private String title;

    private String code;

    private int version;

    private String remarks;

    private long leqId; // leastEducationQualification Id

    private String leqTitle; // leastEducationQualification Title

    private String leqCode; //leastEducationQualification Code

    private String leqRemarks; // leastEducationQualification Remarks

    private double minSalary;

    private Status status;

    private long ownerId;

    private String ownerName;

    public void setVersion(int version) {
        this.version = version;
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

    public double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(double minSalary) {
        this.minSalary = minSalary;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Long designationId) {
        this.designationId = designationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
