package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "store_invoice_count_table")
public class StoreInvoice extends AbstractEntity<Long> {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    private StoreInfo storeInfo;

    @Temporal(TemporalType.DATE)
    private Date month;

    private int count;

    private Status status;

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
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
