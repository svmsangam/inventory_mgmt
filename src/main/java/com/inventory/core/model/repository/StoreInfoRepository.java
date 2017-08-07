package com.inventory.core.model.repository;

import com.inventory.core.model.entity.StoreInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 8/1/17.
 */
@Repository
public interface StoreInfoRepository extends JpaRepository<StoreInfo, Long> , JpaSpecificationExecutor<StoreInfo> {

    StoreInfo findByIdAndStatus(long storeId , Status status);

    List<StoreInfo> findAllByStatus(Status status);

    StoreInfo findByName(String storeName);

}
