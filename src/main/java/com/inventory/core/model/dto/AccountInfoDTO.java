package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.AccountAssociateType;

import java.math.BigDecimal;

/**
 * Created by dhiraj on 8/6/17.
 */
public class AccountInfoDTO {

    private Long accountId;

    private String acountNumber;

    private long associateId;

    private AccountAssociateType associateType;

    private BigDecimal debitAmount;

    private BigDecimal creditAmount;

    private BigDecimal formattedDebitAmount;

    private BigDecimal formattedCreditAmount;

    private Integer version;

    public BigDecimal getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(BigDecimal debitAmount) {
        this.debitAmount = debitAmount;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public BigDecimal getFormattedDebitAmount() {
        return formattedDebitAmount;
    }

    public void setFormattedDebitAmount(BigDecimal formattedDebitAmount) {
        this.formattedDebitAmount = formattedDebitAmount;
    }

    public BigDecimal getFormattedCreditAmount() {
        return formattedCreditAmount;
    }

    public void setFormattedCreditAmount(BigDecimal formattedCreditAmount) {
        this.formattedCreditAmount = formattedCreditAmount;
    }

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
