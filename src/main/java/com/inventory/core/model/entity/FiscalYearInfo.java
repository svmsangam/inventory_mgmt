package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dhiraj on 12/24/17.
 */
@Entity
@Table(name = "fiscal_year_table")
public class FiscalYearInfo extends AbstractEntity<Long>{

    @ManyToOne(fetch = FetchType.LAZY)
    private StoreInfo storeInfo;

    private String title;

    @Temporal(TemporalType.DATE)
    private Date opennigDate;

    @Temporal(TemporalType.DATE)
    private Date closingDate;

    private boolean selected;

    private Status status;

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
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
