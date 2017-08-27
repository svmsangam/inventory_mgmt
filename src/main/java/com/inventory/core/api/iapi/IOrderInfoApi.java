package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 8/27/17.
 */
public interface IOrderInfoApi {

    OrderInfoDTO save(OrderInfoDTO orderInfoDTO);

    OrderInfoDTO show(Status status , long orderId , long storeId);

    List<OrderInfoDTO> list(Status status , long storeId , int page , int size);

    long countList(Status status , long storeId);
}
