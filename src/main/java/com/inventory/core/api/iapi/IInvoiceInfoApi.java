package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.InvoiceInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 9/13/17.
 */
public interface IInvoiceInfoApi {

    double getTotalAmountByStoreInfoAndStatus(long storeInfoId , Status status);

    double getToDayTotalAmountByStoreInfoAndStatus(long storeInfoId , Status status);

    String generatInvoiceNumber(long storeId);

    InvoiceInfoDTO save(long orderInfoId , long createdById);

    void updateOnPayment(long paymentInfoId);

    void updateVersion(long invoiceId);

    InvoiceInfoDTO show(long invoiceId , long storeId , Status status);

    InvoiceInfoDTO getByOrderIdAndStatusAndStoreId(long orderId , Status status  , long storeId);

    List<InvoiceInfoDTO> list(Status status , long storeId , int page , int size);

    List<InvoiceInfoDTO> listTopReceivable(Status status , long storeId , int page , int size);

    long countlist(Status status , long storeId);

    double getTotalReceivableByStoreInfoAndStatus(long storeInfoId , Status status);

    List<Double> getTotalSellOfYearByStore(long storeId, String year);
}
