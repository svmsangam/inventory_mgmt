package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.AccountAssociateType;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by manohar-td-003 on 6/26/17.
 */
@Entity
@Table(name = "invaccount")
public class AccountInfo extends AbstractEntity<Long>{

    private String acountNumber;

    private long associateId;

    private AccountAssociateType associateType;

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
