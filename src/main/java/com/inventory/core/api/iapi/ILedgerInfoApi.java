package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.LedgerInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 9/16/17.
 */
public interface ILedgerInfoApi {

    void save(long invoiceId);

    List<LedgerInfoDTO> list(Status status , long storeId , int page , int size);

    long countAllByStatusAndStore(Status status , long storeId);
}
