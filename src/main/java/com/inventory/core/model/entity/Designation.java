package com.inventory.core.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by dhiraj on 8/1/17.
 */
@Entity
@Table(name = "invdesignation")
public class Designation extends AbstractEntity<Long> {

    private String title;

    private String code;

    private String remarks;

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
}
