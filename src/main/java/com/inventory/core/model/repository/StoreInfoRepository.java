package com.inventory.core.model.repository;

import com.inventory.core.model.entity.StoreInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 8/1/17.
 */
@Repository
@Transactional(readOnly = true)
public interface StoreInfoRepository extends JpaRepository<StoreInfo, Long>, JpaSpecificationExecutor<StoreInfo> {

    StoreInfo findById(long storeId);

    @Query("select s from StoreInfo s where s.id = ?1 and s.status = ?2")
    StoreInfo findByIdAndStatus(long storeId, Status status);

    @Query("select s from StoreInfo s where s.status=?1")
    List<StoreInfo> findAllByStatus(Status status);

    StoreInfo findByName(String storeName);

    @Query("select s from StoreInfo s where s.name = ?1 and s.status = ?2")
    StoreInfo findByNameAndStatus(String storeName, Status status);
}
