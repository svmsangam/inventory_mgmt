package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;

import java.util.Date;

public class StoreInvoiceDTO {

    private Long storeInvoiceId;

    private Long storeId;

    private String storeName;

    private Date month;

    private int count;

    private Status status;

    public Long getStoreInvoiceId() {
        return storeInvoiceId;
    }

    public void setStoreInvoiceId(Long storeInvoiceId) {
        this.storeInvoiceId = storeInvoiceId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
