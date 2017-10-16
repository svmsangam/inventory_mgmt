package com.inventory.core.model.repository;

import com.inventory.core.model.entity.OrderInfo;
import com.inventory.core.model.enumconstant.OrderType;
import com.inventory.core.model.enumconstant.SalesOrderStatus;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 8/27/17.
 */
@Transactional(readOnly = true)
@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo , Long>  , JpaSpecificationExecutor<OrderInfo>{

    @Query("select o from OrderInfo o where o.id = ?1 and o.status = ?2 and o.storeInfo.id = ?3")
    OrderInfo findByIdAndStatusAndStoreInfo(long orderId , Status status , long storeId);

    @Query("select o from OrderInfo o where o.orderNo = ?1 and o.storeInfo.id = ?2")
    OrderInfo findByOrderNoAndStoreInfo(String orderNo , long storeInfoId);

    @Query("select o from OrderInfo o where o.status = ?1 and o.storeInfo.id = ?2 and o.orderType = ?3")
    List<OrderInfo> findAllByStatusAndStoreInfoAndOrderType(Status status , long storeId , OrderType orderType, Pageable pageable);

    @Query("select o from OrderInfo o where o.status = ?1 and o.storeInfo.id = ?2 and o.orderType = ?3 and o.saleTrack in (?4)")
    List<OrderInfo> findAllByStatusAndStoreInfoAndOrderTypeAndSaleTrackIn(Status status , long storeId , OrderType orderType, List<SalesOrderStatus> track , Pageable pageable);

    @Query("select count (o) from OrderInfo o where o.status = ?1 and o.storeInfo.id = ?2 and o.orderType = ?3")
    Long countAllByStatusAndStoreInfoAndOrderType(Status status , long storeId , OrderType orderType);

    @Query("select count (o) from OrderInfo o where o.status = ?1 and o.storeInfo.id = ?2 and o.orderType = ?3 and o.saleTrack = ?4")
    long countAllSaleByStatusAndStoreInfoAndOrderTypeAndSaleTrack(Status status , long storeId , OrderType orderType , SalesOrderStatus track);
}
