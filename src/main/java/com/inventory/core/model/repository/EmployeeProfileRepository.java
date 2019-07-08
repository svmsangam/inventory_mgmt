package com.inventory.core.model.repository;

import com.inventory.core.model.entity.EmployeeProfile;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 12/19/17.
 */
@Repository
public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile , Long>{

    @Query("select e from EmployeeProfile e where e.status = ?1 and e.user.id = ?2")
    EmployeeProfile findByStatusAndUser(Status status , long userId);

    EmployeeProfile findByStatusAndId(Status status , long employeeProfileId);

    List<EmployeeProfile> findAllByStatus(Status status);

    EmployeeProfile findByStatusAndIdAndOwner_Id(Status status , long employeeProfileId , long ownerId);

    @Query("select ep from EmployeeProfile ep join fetch ep.owner as epown where ep.status = ?1 and  ep.id = ?2 and  ep.owner.id = ?3")
    EmployeeProfile findByStatusAndIdAndOwner_IdWithOwner(Status status , long employeeProfileId , long ownerId);

    List<EmployeeProfile> findAllByStatusAndOwner(Status status , long ownerId);


}
