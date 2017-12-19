package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.DesignationInfoDTO;

import java.util.List;

/**
 * Created by dhiraj on 12/19/17.
 */
public interface IDesignationInfoApi {

    DesignationInfoDTO save(DesignationInfoDTO designationInfoDTO);

    DesignationInfoDTO show(long designationId);

    DesignationInfoDTO getByTitle(String title);

    List<DesignationInfoDTO> list();
}
