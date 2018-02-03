package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;

/**
 * Created by dhiraj on 2/3/18.
 */
public class QualificationLevelDTO {

    private long qlfyLvlId;

    private String title;

    private String code;

    private String remarks;

    private Status status;

    private long ownerId;

    private String ownerName;

    public long getQlfyLvlId() {
        return qlfyLvlId;
    }

    public void setQlfyLvlId(long qlfyLvlId) {
        this.qlfyLvlId = qlfyLvlId;
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
}
