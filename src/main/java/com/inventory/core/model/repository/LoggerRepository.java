package com.inventory.core.model.repository;

import com.inventory.core.model.entity.Logger;
import com.inventory.core.model.enumconstant.LogType;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 12/17/17.
 */
@Repository
public interface LoggerRepository extends JpaRepository<Logger , Long>{

    @Query("select l from Logger l where l.status = ?1 and l.associateId = ?2 and l.type = ?3 and l.storeInfo.id = ?4")
    List<Logger> findAllByStatusAndAssociateIdAndTypeAndStore(Status status , long associateId , LogType type , long storeInfoId);
}
