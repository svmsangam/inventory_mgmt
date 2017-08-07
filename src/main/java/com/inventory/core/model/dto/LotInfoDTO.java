package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;

/**
 * Created by dhiraj on 8/6/17.
 */
public class LotInfoDTO {

    private Long lotId;

    private String lot;

    private Status status;

    private long createdById;

    private String createdByName;

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
}
