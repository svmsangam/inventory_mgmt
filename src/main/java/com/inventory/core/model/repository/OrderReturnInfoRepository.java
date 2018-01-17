package com.inventory.core.model.repository;

import com.inventory.core.model.entity.OrderReturnInfo;
import com.inventory.core.model.enumconstant.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dhiraj on 1/17/18.
 */
@Repository
public interface OrderReturnInfoRepository extends JpaRepository<OrderReturnInfo, Long>{

    OrderReturnInfo findByStatusAndId(Status status , long orderReturnInfoId);

    OrderReturnInfo findById(long orderReturnInfoId);
}
