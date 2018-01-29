package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.OrderItemInfoDTO;
import com.inventory.core.model.dto.OrderReturnInfoDTO;
import com.inventory.core.model.dto.ReturnItemInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 1/29/18.
 */
public interface IReturnItemInfoApi {

    double save(OrderReturnInfoDTO orderReturnInfoDTO);

    List<ReturnItemInfoDTO> list(Status status , long returnOrderInfoId);
}
