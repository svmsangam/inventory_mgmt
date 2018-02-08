package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.OrderReturnInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.liteentity.OrderReturnInfoDomain;

import java.util.List;

/**
 * Created by dhiraj on 1/17/18.
 */
public interface IOrderReturnInfoApi {

    OrderReturnInfoDTO save(OrderReturnInfoDTO orderReturnInfoDTO);

    void cancelInvoice(long invoiceId , long createdById );

    long countAllByStatusAndStoreInfo_Id(Status status, long storeId);

    List<OrderReturnInfoDomain> list(Status status, long storeId, int page, int size);
}
