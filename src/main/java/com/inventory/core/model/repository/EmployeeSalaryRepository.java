package com.inventory.core.model.repository;

import com.inventory.core.model.entity.EmployeeSalary;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 2/2/18.
 */
@Repository
public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary , Long>{

    EmployeeSalary findByIdAndStoreEmployee_IdAndStatus(long employeeSalaryId , long storeEmployeeId , Status status);

    List<EmployeeSalary> findByStoreEmployee_IdAndStatusAndSelected(long storeEmployeeId , Status status , boolean selected);

    EmployeeSalary findByIdAndStatus(long employeeSalaryId , Status status);

    @Query("select es from EmployeeSalary es join fetch es.storeEmployee where es.id = ?1 and es.storeEmployee.id = ?2 and es.status = ?3")
    EmployeeSalary findByIdAndStoreEmployee_IdAndStatusWithStoreEmployee(long employeeSalaryId , long storeEmployeeId , Status status);

    @Query("select es from EmployeeSalary es join fetch es.storeEmployee where es.id = ?1 and es.status = ?2")
    EmployeeSalary findByIdAndStatusWithStoreEmployee(long employeeSalaryId , Status status);

    @Query("select es from EmployeeSalary es join fetch es.storeEmployee where es.storeEmployee.id = ?2 and es.status = ?3")
    List<EmployeeSalary> findAllByStoreEmployee_IdAndStatusWithStoreEmployee(long storeEmployeeId , Status status);

    @Query("select es from EmployeeSalary es join fetch es.storeEmployee where es.storeEmployee.id = ?1 and es.status = ?3")
    List<EmployeeSalary> findAllByStoreInfo_IdAndStatusWithStoreEmployee(long storeInfoId , Status status);







}
