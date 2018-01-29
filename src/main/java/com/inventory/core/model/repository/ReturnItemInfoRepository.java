package com.inventory.core.model.repository;

import com.inventory.core.model.entity.ReturnItemInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dhiraj on 1/17/18.
 */
@Repository
public interface ReturnItemInfoRepository extends JpaRepository<ReturnItemInfo , Long> {

    List<ReturnItemInfo> findAllByStatusAndOrderReturnInfo_Id(Status status , long returnOrderInfoId);
}
