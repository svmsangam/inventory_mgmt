package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by dhiraj on 12/19/17.
 */
@Entity
@Table(name = "employee_salary")
public class EmployeeSalary extends AbstractEntity<Long>{

    @OneToOne(fetch = FetchType.LAZY)
    private StoreEmployee storeEmployee;

    private double increment;//percent

    private double amount;//actual amount

    private double taxAmount;//amount

    private boolean selected;

    private Status status;

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
