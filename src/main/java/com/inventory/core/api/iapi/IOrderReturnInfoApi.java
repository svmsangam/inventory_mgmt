package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.OrderReturnInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 1/17/18.
 */
public interface IOrderReturnInfoApi {

    OrderReturnInfoDTO save(OrderReturnInfoDTO orderReturnInfoDTO);

    void cancelInvoice(long invoiceId , long createdById );

    List<OrderReturnInfoDTO> list(Status status , long storeId);
}
