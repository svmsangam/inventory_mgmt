package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.AccountEntryType;
import com.inventory.core.model.enumconstant.LedgerEntryType;
import com.inventory.core.model.enumconstant.Status;

import java.util.Date;

/**
 * Created by dhiraj on 8/6/17.
 */
public class LedgerInfoDTO {

    private Long ledgerId;

    private Date date;

    private String accountNo;

    private AccountInfoDTO accountInfo;

    private Long accountId;

    private double amount;

    private AccountEntryType accountEntryType;

    private LedgerEntryType ledgerEntryType;

    private String remarks;

    private long storeInfoId;

    private Status status;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Long getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(Long ledgerId) {
        this.ledgerId = ledgerId;
    }

    public AccountInfoDTO getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfoDTO accountInfo) {
        this.accountInfo = accountInfo;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public AccountEntryType getAccountEntryType() {
        return accountEntryType;
    }

    public void setAccountEntryType(AccountEntryType accountEntryType) {
        this.accountEntryType = accountEntryType;
    }

    public LedgerEntryType getLedgerEntryType() {
        return ledgerEntryType;
    }

    public void setLedgerEntryType(LedgerEntryType ledgerEntryType) {
        this.ledgerEntryType = ledgerEntryType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public long getStoreInfoId() {
        return storeInfoId;
    }

    public void setStoreInfoId(long storeInfoId) {
        this.storeInfoId = storeInfoId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
