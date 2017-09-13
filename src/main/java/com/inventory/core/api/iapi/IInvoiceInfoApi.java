package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.InvoiceInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 9/13/17.
 */
public interface IInvoiceInfoApi {

    String generatInvoiceNumber(long storeId);

    InvoiceInfoDTO save(long orderInfoId , long createdById);

    InvoiceInfoDTO show(long invoiceId , long storeId , Status status);

    List<InvoiceInfoDTO> list(Status status , long storeId , int page , int size);
}
