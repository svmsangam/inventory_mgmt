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
    private StoreInfo storeInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    private EmployeeProfile employeeProfile;

    @Temporal(TemporalType.DATE)
    private Date startingDate;

    @Temporal(TemporalType.DATE)
    private Date endingDate;

    private Designation designation;

    private EmployeeStatus employeeStatus;

    private double initialSalary;

    private double tax;//percent

    private Status status;
}
