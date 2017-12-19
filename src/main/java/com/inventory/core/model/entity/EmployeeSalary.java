package com.inventory.core.model.entity;

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
}
