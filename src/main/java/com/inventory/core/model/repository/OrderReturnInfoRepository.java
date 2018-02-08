package com.inventory.core.model.repository;

import com.inventory.core.model.entity.OrderReturnInfo;
import com.inventory.core.model.liteentity.OrderReturnInfoDomain;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 1/17/18.
 */
@Repository
public interface OrderReturnInfoRepository extends JpaRepository<OrderReturnInfo, Long>{

    OrderReturnInfo findByStatusAndId(Status status , long orderReturnInfoId);

    OrderReturnInfo findByStatusAndIdAndStoreInfo_Id(Status status , long orderReturnInfoId , long storeInfoId);

    List<OrderReturnInfo> findAllByStatusAndStoreInfo_Id(Status status , long storeInfoId);

    long countAllByStatusAndStoreInfo_Id(Status status , long storeInfoId);

    @Query("select new com.inventory.core.model.liteentity.OrderReturnInfoDomain( r.id , r.status , r.returnDate , r.totalAmount , r.orderInfo.id , r.orderInfo.orderNo , r.orderInfo.clientInfo.name , r.orderInfo.clientInfo.companyName) from OrderReturnInfo r where r.status = ?1 and r.storeInfo.id =?2")
    List<OrderReturnInfoDomain> findAllForLiteByStatusAndStoreInfo_Id(Status status , long storeInfoId , Pageable pageable);

    OrderReturnInfo findById(long orderReturnInfoId);

    @Query("select new com.inventory.core.model.liteentity.OrderReturnInfoDomain( r.id , r.status , r.returnDate , r.totalAmount , r.orderInfo.id , r.orderInfo.orderNo , r.orderInfo.clientInfo.name , r.orderInfo.clientInfo.companyName) from OrderReturnInfo r where r.id = ?1 and r.status = ?2 and r.storeInfo.id =?3")
    OrderReturnInfoDomain findForLiteByStatusAndStoreInfo_Id(long orderReternId , Status status , long storeInfoId , Pageable pageable);

}
