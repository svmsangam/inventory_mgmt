package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IQualificationApi;
import com.inventory.core.model.converter.QualificationConverter;
import com.inventory.core.model.dto.QualificationLevelDTO;
import com.inventory.core.model.entity.QualificationLevel;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.QualificationLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dhiraj on 2/4/18.
 */
@Service
public class QualificationApi implements IQualificationApi {

    @Autowired
    private QualificationConverter qualificationConverter;

    @Autowired
    private QualificationLevelRepository qualificationLevelRepository;

    @Override
    public QualificationLevelDTO save(QualificationLevelDTO qualificationDTO) {

        QualificationLevel qualificationLevel = qualificationConverter.convertToEntity(qualificationDTO);

        qualificationLevel.setStatus(Status.ACTIVE);

        return qualificationConverter.convertToDto(qualificationLevelRepository.save(qualificationLevel));
    }

    @Override
    public QualificationLevelDTO show(long qlfyLvlId , long ownerId) {
        return qualificationConverter.convertToDto(qualificationLevelRepository.findByIdAndStatusAndOwner_Id(qlfyLvlId , Status.ACTIVE , ownerId));
    }

    @Override
    public List<QualificationLevelDTO> list(Status status, long ownerId) {
        return qualificationConverter.convertToDtoList(qualificationLevelRepository.findAllByStatusAndOwner_Id(status , ownerId));
    }
}
