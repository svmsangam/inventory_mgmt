package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.AccountAssociateType;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by manohar-td-003 on 6/26/17.
 */
@Entity
@Table(name = "invaccount")
public class AccountInfo extends AbstractEntity<Long> {

    private String acountNumber;

    private long associateId;

    private BigDecimal debitAmount;

    private BigDecimal creditAmount;

    private AccountAssociateType associateType;

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
}
