package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.UnitInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 8/15/17.
 */
public interface IUnitInfoApi {

    UnitInfoDTO save(UnitInfoDTO unitInfoDTO);

    UnitInfoDTO update(UnitInfoDTO unitInfoDTO);

    void delete(long unitInfoId);

    UnitInfoDTO getByNameAndStoreAndStatus(String unitName, long storeId, Status status);

    UnitInfoDTO getByIdAndStoreAndStatus(long unitInfoId, long storeId, Status status);

    List<UnitInfoDTO> list(Status status, long storeId);
}
