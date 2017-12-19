package com.inventory.core.model.repository;

import com.inventory.core.model.entity.EmployeeProfile;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dhiraj on 12/19/17.
 */
@Repository
public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile , Long>{

    EmployeeProfile findByStatusAndUser(Status status , long userId);
}
