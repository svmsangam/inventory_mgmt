package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by dhiraj on 8/1/17.
 */
@Entity
@Table(name = "invdesignation")
public class Designation extends AbstractEntity<Long> {

    private String title;

    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    private QualificationLevel leastEducationQualification ;//leastEducationQualification

    private double minSalary;

    private String remarks;

    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private StoreInfo owner;

    public QualificationLevel getLeastEducationQualification() {
        return leastEducationQualification;
    }

    public void setLeastEducationQualification(QualificationLevel leastEducationQualification) {
        this.leastEducationQualification = leastEducationQualification;
    }

    public double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(double minSalary) {
        this.minSalary = minSalary;
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
