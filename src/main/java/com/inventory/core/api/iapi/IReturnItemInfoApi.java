package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.OrderItemInfoDTO;
import com.inventory.core.model.dto.OrderReturnInfoDTO;
import com.inventory.core.model.dto.ReturnItemInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.liteentity.ReturnItemInfoDomain;

import java.util.List;

/**
 * Created by dhiraj on 1/29/18.
 */
public interface IReturnItemInfoApi {

    double save(OrderReturnInfoDTO orderReturnInfoDTO);

    double save(long orderId , long orderReturnId);

    List<ReturnItemInfoDomain> list(Status status , long returnOrderInfoId);
}
