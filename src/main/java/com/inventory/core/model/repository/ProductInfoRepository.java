package com.inventory.core.model.repository;

import com.inventory.core.model.entity.ProductInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.domain.Pageable;
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
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long>, JpaSpecificationExecutor<ProductInfo> {

    ProductInfo findById(long productInfoId);

    @Query("select p from ProductInfo p where p.id = ?1 and p.status = ?2 and p.storeInfo.id = ?3")
    ProductInfo findByIdAndStatusAndStoreInfo(long productInfoId, Status status, long storeId);

    @Query("select p from ProductInfo p where p.status = ?1 and p.storeInfo.id = ?2 order by p.name asc ")
    List<ProductInfo> findAllByStatusAndStoreInfo(Status status, long storeId);

    @Query("select p from ProductInfo p where p.status = ?1 and p.storeInfo.id = ?2")
    List<ProductInfo> findAllByStatusAndStoreInfo(Status status, long storeId, Pageable pageable);
}
