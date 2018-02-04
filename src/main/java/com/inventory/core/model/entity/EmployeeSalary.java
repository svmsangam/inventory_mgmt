package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dhiraj on 12/19/17.
 */
@Entity
@Table(name = "employee_salary")
public class EmployeeSalary extends AbstractEntity<Long>{

    @OneToOne(fetch = FetchType.LAZY)
    private StoreEmployee storeEmployee;

    @ManyToOne(fetch = FetchType.LAZY)
    private Designation designation;

    @Temporal(TemporalType.DATE)
    private Date date;

    private double increment;//percent

    private double amount;//actual amount

    private double taxAmount;//amount

    private boolean selected;

    private String remarks;

    private Status status;

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public StoreEmployee getStoreEmployee() {
        return storeEmployee;
    }

    public void setStoreEmployee(StoreEmployee storeEmployee) {
        this.storeEmployee = storeEmployee;
    }

    public double getIncrement() {
        return increment;
    }

    public void setIncrement(double increment) {
        this.increment = increment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
