package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.EmployeeStatus;
import com.inventory.core.model.enumconstant.Status;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dhiraj on 12/19/17.
 */
@Entity
@Table(name = "store_employee")
public class StoreEmployee extends AbstractEntity<Long>{

    @ManyToOne(fetch = FetchType.LAZY)
    private StoreInfo owner;

    @ManyToOne(fetch = FetchType.EAGER)
    private EmployeeProfile employeeProfile;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Designation designation;

    private EmployeeStatus employeeStatus;

    private String remarks;

    private Status status;

    public StoreInfo getOwner() {
        return owner;
    }

    public void setOwner(StoreInfo owner) {
        this.owner = owner;
    }

    public EmployeeProfile getEmployeeProfile() {
        return employeeProfile;
    }

    public void setEmployeeProfile(EmployeeProfile employeeProfile) {
        this.employeeProfile = employeeProfile;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
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
}
