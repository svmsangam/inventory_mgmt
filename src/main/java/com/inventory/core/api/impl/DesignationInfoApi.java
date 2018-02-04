package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IDesignationInfoApi;
import com.inventory.core.model.converter.DesignationConverter;
import com.inventory.core.model.dto.DesignationInfoDTO;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.DesignationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dhiraj on 12/19/17.
 */
@Service
public class DesignationInfoApi implements IDesignationInfoApi {

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private DesignationConverter designationConverter;

    @Override
    public DesignationInfoDTO save(DesignationInfoDTO designationInfoDTO) {
        return designationConverter.convertToDto(designationRepository.save(designationConverter.convertToEntity(designationInfoDTO)));
    }

    @Override
    public DesignationInfoDTO show(long designationId , Status status , long ownerId) {
        return designationConverter.convertToDto(designationRepository.findByIdAndStatusAndOwner_Id(designationId , status , ownerId));
    }

    @Override
    public DesignationInfoDTO getByTitle(String title) {
        return designationConverter.convertToDto(designationRepository.findByTitle(title));
    }

    @Override
    public List<DesignationInfoDTO> list(Status status , long ownerId) {
        return designationConverter.convertToDtoList(designationRepository.findAllByStatusAndOwner_Id(status , ownerId));
    }
}
