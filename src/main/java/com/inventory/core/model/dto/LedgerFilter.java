package com.inventory.core.model.dto;

import com.inventory.core.model.entity.AccountInfo;
import com.inventory.core.model.entity.FiscalYearInfo;
import com.inventory.core.model.entity.StoreInfo;
import com.inventory.core.model.enumconstant.Status;

import java.util.Date;

/**
 * Created by dhiraj on 10/20/17.
 */
public class LedgerFilter {

    private FiscalYearInfo fiscalYearInfo;

    private AccountInfo accountInfo;

    private StoreInfo storeInfo;

    private Date from;

    private Date to;

    private Status status;

    public FiscalYearInfo getFiscalYearInfo() {
        return fiscalYearInfo;
    }

    public void setFiscalYearInfo(FiscalYearInfo fiscalYearInfo) {
        this.fiscalYearInfo = fiscalYearInfo;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
