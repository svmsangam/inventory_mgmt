package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.AccountEntryType;
import com.inventory.core.model.enumconstant.LedgerEntryType;
import com.inventory.core.model.enumconstant.Status;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by manohar-td-003 on 6/25/17.
 */
@Entity
@Table(name="ledger_table")
public class LedgerInfo extends AbstractEntity<Long>{

    @ManyToOne(fetch = FetchType.EAGER)
    private AccountInfo accountInfo;

    private double amount;

    private AccountEntryType accountEntryType;

    private LedgerEntryType ledgerEntryType;

    private String remarks;

    @ManyToOne(fetch = FetchType.EAGER)
    private StoreInfo storeInfo;

    private Status status;

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
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

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
