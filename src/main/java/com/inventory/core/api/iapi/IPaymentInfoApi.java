package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.PaymentInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 10/10/17.
 */
public interface IPaymentInfoApi {
    
    PaymentInfoDTO save(PaymentInfoDTO paymentInfoDTO);

    PaymentInfoDTO getById(long paymentInfoId);

    PaymentInfoDTO getByIdAndStatus(long paymentInfoId , Status status);

    PaymentInfoDTO show(long paymentInfoId , Status status , long storeId);

    PaymentInfoDTO getByIdAndStatusAndStoreAndInvoiceInfo(long paymentInfoId , Status status , long storeId , long invoiceInfoId);

    List<PaymentInfoDTO> getByStatusAndStoreAndInvoiceInfo(Status status , long storeId , long invoiceInfoId);

}
