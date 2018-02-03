package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.List;

/**
 * Created by dhiraj on 2/2/18.
 */
@Entity
@Table(name = "qualification_level_table")
public class QualificationLevel extends AbstractEntity<Long>{

    private String title;

    private String code;

    private String remarks;

    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private StoreInfo owner;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public StoreInfo getOwner() {
        return owner;
    }

    public void setOwner(StoreInfo owner) {
        this.owner = owner;
    }
}
