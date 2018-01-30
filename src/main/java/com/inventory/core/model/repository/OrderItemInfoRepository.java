package com.inventory.core.model.repository;

import com.inventory.core.model.entity.OrderItemInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 8/30/17.
 */
@Transactional(readOnly = true)
@Repository
public interface OrderItemInfoRepository extends JpaRepository<OrderItemInfo , Long> , JpaSpecificationExecutor<OrderItemInfo>{

    OrderItemInfo findById(long orderItemInfoId);

    OrderItemInfo findByIdAndStatus(long orderItemInfoId , Status status);

    OrderItemInfo findByIdAndStatusAndOrderInfo_Id(long orderItemInfoId , Status status , long orderInfoId);

    @Query("select o from OrderItemInfo o where o.id = ?1 and o.status = ?2 and o.orderInfo.id = ?3")
    OrderItemInfo findByIdAndStatusAndOrderInfo(long orderItemInfoId , Status status , long orderInfoId);

    @Query("select o from OrderItemInfo o where o.status = ?1 and o.orderInfo.id = ?2")
    List<OrderItemInfo> findAllByStatusAndOrderInfo(Status status , long orderInfoId);

    @Query("select sum(oi.amount) from OrderItemInfo oi inner join oi.orderInfo o where oi.itemInfo.id = ?1 and o.status = 0 and (o.saleTrack = 3 or o.saleTrack = 4)")/*((o.quantity * o.rate) - (o.quantity * o.rate * o.discount / 100))*/
    Double findTotalSaleAmountOfItem(long itemId );

    @Query("select sum(oi.amount) from OrderItemInfo oi inner join oi.orderInfo o where oi.itemInfo.productInfo.id = ?1 and o.status = 0 and (o.saleTrack = 3 or o.saleTrack = 4)")/*((o.quantity * o.rate) - (o.quantity * o.rate * o.discount / 100))*/
    Double findTotalSaleAmountOfProduct(long productId );
}
