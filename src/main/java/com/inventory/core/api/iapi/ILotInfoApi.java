package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.LotInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 8/15/17.
 */
public interface ILotInfoApi {

    LotInfoDTO save(LotInfoDTO lotInfoDTO);

    LotInfoDTO update(LotInfoDTO lotInfoDTO);

    void delete(long lotInfoId);

    LotInfoDTO getByNameAndStatus(String lotName , Status status);

    LotInfoDTO getByIdAndStatus(long lotInfoId , Status status);

    List<LotInfoDTO> list(Status status);
}
