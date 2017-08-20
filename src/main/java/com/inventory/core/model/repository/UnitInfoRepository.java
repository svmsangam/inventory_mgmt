package com.inventory.core.model.repository;

import com.inventory.core.model.entity.UnitInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 8/6/17.
 */
@Repository
@Transactional(readOnly = true)
public interface UnitInfoRepository extends JpaRepository<UnitInfo , Long> , JpaSpecificationExecutor<UnitInfo> {

    UnitInfo findById(long unitId);

    @Query("select u from UnitInfo u where u.id = ?1 and u.status = ?2 and  u.storeInfo.id = ?3")
    UnitInfo findByIdAndStatusAndStoreInfo(long unitId, Status status, long storeId);

    UnitInfo findByName(String unitName);

    @Query("select u from UnitInfo u where u.name = ?1 and u.status = ?2 and  u.storeInfo.id = ?3")
    UnitInfo findByNameAndStatusAndStoreInfo(String unitName, Status status, long storeId);

    @Query("select u from UnitInfo u where u.code = ?1 and u.status = ?2 and  u.storeInfo.id = ?3")
    UnitInfo findByCodeAndStatusAndStoreInfo(String unitCode, Status status, long storeId);

    @Query("select u from UnitInfo u where u.status = ?1 and u.storeInfo.id=?2 order by u.id desc")
    List<UnitInfo> findAllByStatusAndStoreInfo(Status status, long storeId);
}
