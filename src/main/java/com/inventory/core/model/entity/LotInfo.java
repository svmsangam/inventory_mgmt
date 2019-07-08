package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by dhiraj on 7/30/17.
 */
@Entity
@Table(name = "lot_table")
public class LotInfo extends AbstractEntity<Long> {

    private String lot;

    private Status status;

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
