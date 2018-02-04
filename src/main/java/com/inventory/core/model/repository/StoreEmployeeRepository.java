package com.inventory.core.model.repository;

import com.inventory.core.model.entity.EmployeeProfile;
import com.inventory.core.model.entity.StoreEmployee;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 2/4/18.
 */
@Repository
public interface StoreEmployeeRepository extends JpaRepository<StoreEmployee , Long> {

    @Query("select se.employeeProfile from StoreEmployee se where se.employeeProfile.id = ?1 and se.status = ?2 and se.owner.id = ?3")
    EmployeeProfile findEmployeeProfileByIdAndStatusAndOwner(long employeeId , Status status , long owner);

    @Query("select se.employeeProfile from StoreEmployee se where se.employeeProfile.id = ?1 and se.status = ?2 and se.owner.id in (?3)")
    EmployeeProfile findEmployeeProfileByIdAndStatusAndOwnerIn(long employeeId , Status status , List<Long> ownerIdList);

    @Query("select se.employeeProfile from StoreEmployee se where se.status = ?1 and se.owner.id = ?2")
    List<EmployeeProfile> findAllEmployeeProfileByStatusAndOwner(Status status , long ownerId);

    @Query("select se.employeeProfile from StoreEmployee se where se.status = ?1 and se.owner.id in (?2)")
    List<EmployeeProfile> findAllEmployeeProfileByStatusAndOwnerIn(Status status , List<Long> ownerIdList);
}
