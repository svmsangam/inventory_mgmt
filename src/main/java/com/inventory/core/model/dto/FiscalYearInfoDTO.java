package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;

import java.util.Date;

/**
 * Created by dhiraj on 12/24/17.
 */
public class FiscalYearInfoDTO {

    private long fiscalYearInfoId;

    private long storeInfoId;

    private String title;

    private Date opennigDate;

    private Date closingDate;

    private boolean selected;

    private Status status;

    public long getFiscalYearInfoId() {
        return fiscalYearInfoId;
    }

    public void setFiscalYearInfoId(long fiscalYearInfoId) {
        this.fiscalYearInfoId = fiscalYearInfoId;
    }

    public long getStoreInfoId() {
        return storeInfoId;
    }

    public void setStoreInfoId(long storeInfoId) {
        this.storeInfoId = storeInfoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getOpennigDate() {
        return opennigDate;
    }

    public void setOpennigDate(Date opennigDate) {
        this.opennigDate = opennigDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
