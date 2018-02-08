package com.inventory.core.model.repository;

import com.inventory.core.model.entity.ReturnItemInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.liteentity.OrderReturnInfoDomain;
import com.inventory.core.model.liteentity.ReturnItemInfoDomain;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 1/17/18.
 */
@Repository
public interface ReturnItemInfoRepository extends JpaRepository<ReturnItemInfo , Long> {

    List<ReturnItemInfo> findAllByStatusAndOrderReturnInfo_Id(Status status , long returnOrderInfoId);

    @Query("select new com.inventory.core.model.liteentity.ReturnItemInfoDomain(ri.id , ri.quantity , ri.rate , ri.totalAmount , ri.orderItemInfo.id , ri.orderItemInfo.itemInfo.id , ri.orderItemInfo.itemInfo.productInfo.name , ri.status) from ReturnItemInfo ri where ri.status = ?1 and ri.orderReturnInfo.id =?2")
    List<ReturnItemInfoDomain> findForLiteByStatusAndStoreInfo_Id(Status status , long returnOrderInfoId);

}
