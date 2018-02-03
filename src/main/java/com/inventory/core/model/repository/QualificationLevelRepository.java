package com.inventory.core.model.repository;

import com.inventory.core.model.entity.QualificationLevel;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 2/2/18.
 */
@Repository
public interface QualificationLevelRepository extends JpaRepository<QualificationLevel , Long>{

    QualificationLevel findByIdAndStatusAndOwner_Id(long qualLevId , Status status , long ownerId);

    QualificationLevel findByTitleAndStatusAndOwner_Id(String title , Status status , long ownerId);

    List<QualificationLevel> findAllByStatusAndOwner_Id(Status status , long ownerId);

}
