package com.inventory.core.model.repository;

import com.inventory.core.model.entity.Designation;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 8/11/17.
 */
@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long>, JpaSpecificationExecutor<Designation> {

    Designation findByIdAndStatusAndOwner_Id(long designationId , Status status , long ownerId);

    Designation findByTitle(String title);

    Designation findByTitleAndStatus(String title , Status status);

    Designation findByTitleAndStatusAndOwner_Id(String title , Status status , long ownerId);

    List<Designation> findAllByStatusAndOwner_Id(Status status , long ownerId);
}
