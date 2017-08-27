package com.inventory.core.model.repository;

import com.inventory.core.model.entity.OrderInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 8/27/17.
 */
@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo , Long>  , JpaSpecificationExecutor<OrderInfo>{

    @Query("select o from OrderInfo o where o.id = ?1 and o.status = ?2 and o.storeInfo.id = ?3")
    OrderInfo findByIdAndStatusAndStoreInfo(long orderId , Status status , long storeId);

    @Query("select o from OrderInfo o where o.status = ?1 and o.storeInfo.id = ?2")
    List<OrderInfo> findAllByStatusAndStoreInfo(Status status , long storeId , Pageable pageable);

    @Query("select count (o) from OrderInfo o where o.status = ?1 and o.storeInfo.id = ?2")
    Long countAllByStatusAndStoreInfo(Status status , long storeId );

    @Query("select o from OrderInfo o where o.orderNo = ?1 and o.storeInfo.id = ?2")
    OrderInfo findByOrderNo(String orderNo , long storeId);
}
