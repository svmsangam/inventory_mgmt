package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.AccountAssociateType;

/**
 * Created by dhiraj on 8/6/17.
 */
public class AccountInfoDTO {

    private Long accountId;

    private String acountNumber;

    private long associateId;

    private AccountAssociateType associateType;

    private Integer version;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAcountNumber() {
        return acountNumber;
    }

    public void setAcountNumber(String acountNumber) {
        this.acountNumber = acountNumber;
    }

    public long getAssociateId() {
        return associateId;
    }

    public void setAssociateId(long associateId) {
        this.associateId = associateId;
    }

    public AccountAssociateType getAssociateType() {
        return associateType;
    }

    public void setAssociateType(AccountAssociateType associateType) {
        this.associateType = associateType;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
