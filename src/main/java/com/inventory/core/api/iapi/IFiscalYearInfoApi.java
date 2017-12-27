package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.FiscalYearInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 12/24/17.
 */
public interface IFiscalYearInfoApi {

    FiscalYearInfoDTO save(FiscalYearInfoDTO fiscalYearInfoDTO);

    List<FiscalYearInfoDTO> list(Status status , long storeInfoId , int page , int size);

    FiscalYearInfoDTO show(Status status , long fiscalYearInfoId , long storeInfoId);

    FiscalYearInfoDTO select(long fiscalYearInfoId , long storeInfoId);

    FiscalYearInfoDTO getByIdAndStatusAndStoreInfoAndSelected(long fiscalYearInfoId , Status status , long storeInfoId , boolean selected);

    FiscalYearInfoDTO getCurrentFiscalYearByStoreInfo(long storeInfoId);

    long getCurrentFiscalYearIdByStoreInfo(long storeInfoId);
}
