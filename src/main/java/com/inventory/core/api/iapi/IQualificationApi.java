package com.inventory.core.api.iapi;

import com.inventory.core.model.dto.QualificationLevelDTO;
import com.inventory.core.model.enumconstant.Status;

import java.util.List;

/**
 * Created by dhiraj on 2/4/18.
 */
public interface IQualificationApi {

    QualificationLevelDTO save(QualificationLevelDTO qualificationDTO);

    QualificationLevelDTO show(long qlfyLvlId, long ownerId);

    List<QualificationLevelDTO> list(Status status , long ownerId);


}
