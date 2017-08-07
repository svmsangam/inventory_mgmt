package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.NumberStatus;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by dhiraj on 7/30/17.
 */
@Entity
@Table(name = "number_generator_table")
public class CodeGenerator extends AbstractEntity<Long> {

    private long number;

    private String prefix;

    @ManyToOne(fetch = FetchType.EAGER)
    private StoreInfo storeInfo;

    private NumberStatus numberStatus;

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    public NumberStatus getNumberStatus() {
        return numberStatus;
    }

    public void setNumberStatus(NumberStatus numberStatus) {
        this.numberStatus = numberStatus;
    }
}
