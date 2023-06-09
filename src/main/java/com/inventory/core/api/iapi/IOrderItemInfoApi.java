package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.dto.OrderItemInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 8/30/17.
 */
public interface IOrderItemInfoApi {

    double save(OrderInfoDTO orderInfoDTO);

    List<OrderItemInfoDTO> getAllByStatusAndOrderInfo(Status status , long orderId);

    List<OrderItemInfoDTO> getAllOnValidationFaild( List<OrderItemInfoDTO> itemInfoDTOList , long storeId);

    double getTotalAmountByStatusAndOrderInfo(Status status , long orderId);

    double getTotalSaleAmountOfItem(long itemId );

    double getTotalSaleAmountOfProduct(long productId );
}
