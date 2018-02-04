package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.DesignationInfoDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 12/19/17.
 */
public interface IDesignationInfoApi {

    DesignationInfoDTO save(DesignationInfoDTO designationInfoDTO);

    DesignationInfoDTO show(long designationId, Status status, long ownerId);

    DesignationInfoDTO getByTitle(String title);

    List<DesignationInfoDTO> list(Status status, long ownerId);
}
