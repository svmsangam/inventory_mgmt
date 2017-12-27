package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.LedgerFilterDTO;
import com.inventory.core.model.dto.LedgerInfoDTO;
import com.inventory.core.model.enumconstant.AccountEntryType;
import com.inventory.core.model.enumconstant.Status;

import java.util.Date;
import java.util.List;

/**
 * Created by dhiraj on 9/16/17.
 */
public interface ILedgerInfoApi {

    void save(long invoiceId);

    void saveOnPayment(long paymentInfoId);

    List<LedgerInfoDTO> list(Status status , long storeId , int page , int size);

    long countAllByStatusAndStore(Status status , long storeId);

    List<LedgerInfoDTO> filter(LedgerFilterDTO filterDTO);

    long countFilter(LedgerFilterDTO filterDTO);

    List<LedgerInfoDTO> filterReport(Status status , long storeId , long accountId , Date from , Date to);

    Long filterCount(Status status , long storeId , long accountId , Date from , Date to);

    double filterTotalAmount(Status status , long storeId , long accountId , Date from , Date to , AccountEntryType accountEntryType);

    double getTotalAmountByStatusAndStoreInfoIdAndAccountInfoAndAccountEntryType(Status status , long storeId , long accountId, AccountEntryType accountEntryType);

}
