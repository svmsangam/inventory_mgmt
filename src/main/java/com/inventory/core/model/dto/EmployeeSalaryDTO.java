package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;

/**
 * Created by dhiraj on 2/3/18.
 */
public class EmployeeSalaryDTO {

    private long empSlryId;

    private long storeEmployeeId;

    private double increment;//percent

    private double amount;//actual amount

    private double taxAmount;//amount

    private boolean selected;

    private Status status;

    public long getEmpSlryId() {
        return empSlryId;
    }

    public void setEmpSlryId(long empSlryId) {
        this.empSlryId = empSlryId;
    }

    public long getStoreEmployeeId() {
        return storeEmployeeId;
    }

    public void setStoreEmployeeId(long storeEmployeeId) {
        this.storeEmployeeId = storeEmployeeId;
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
