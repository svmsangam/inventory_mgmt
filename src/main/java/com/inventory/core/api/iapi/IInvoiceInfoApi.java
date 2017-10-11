package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.InvoiceInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 9/13/17.
 */
public interface IInvoiceInfoApi {

    double getTotalAmountByStoreInfoAndStatus(long storeInfoId , Status status);

    String generatInvoiceNumber(long storeId);

    InvoiceInfoDTO save(long orderInfoId , long createdById);

    void updateOnPayment(long paymentInfoId);

    InvoiceInfoDTO show(long invoiceId , long storeId , Status status);

    InvoiceInfoDTO getByOrderIdAndStatusAndStoreId(long orderId , Status status  , long storeId);

    List<InvoiceInfoDTO> list(Status status , long storeId , int page , int size);

    long countlist(Status status , long storeId);
}
