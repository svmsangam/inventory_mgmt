package com.inventory.core.model.repository;

import com.inventory.core.model.entity.StockInfo;
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
@Transactional(readOnly = true)
public interface StockInfoRepository extends JpaRepository<StockInfo, Long>, JpaSpecificationExecutor<StockInfo> {

    StockInfo findById(long stockInfoId);

    StockInfo findByIdAndStatus(long stockInfoId, Status status);

    @Query("select s from StockInfo s where s.id = ?1 and s.status = ?2 and s.productInfo.id = ?3 ")
    StockInfo findByIdAndStatusAndProductInfo(long stockInfoId, Status status, long productInfoId);

    @Query("select s from StockInfo s where s.status = ?1 and s.productInfo.storeInfo.id = ?2 order by s.productInfo.name asc ")
    List<StockInfo> findAllByStatusAndStoreInfo(Status status, long storeId);
}
