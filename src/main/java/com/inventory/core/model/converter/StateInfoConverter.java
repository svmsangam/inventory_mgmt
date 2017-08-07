package com.inventory.core.model.converter;

import com.inventory.core.model.dto.StateInfoDTO;
import com.inventory.core.model.entity.StateInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.CountryInfoRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Created by dhiraj on 4/25/17.
 */

@Service
public class StateInfoConverter implements IConvertable<StateInfo, StateInfoDTO>, IListConvertable<StateInfo , StateInfoDTO> {

    @Autowired
    private CountryInfoRepository countryRepository;

    @Override
    public StateInfo convertToEntity(StateInfoDTO dto) {

        return copyConvertToEntity(dto , new StateInfo());
    }

    @Override
    public StateInfoDTO convertToDto(StateInfo entity) {

        if (entity == null){
            return null;
        }

        StateInfoDTO dto = new StateInfoDTO();

        dto.setCountryName(entity.getCountryInfo().getName());
        dto.setStateId(entity.getId());
        //dto.setStateStatus(entity.getStatus());
        dto.setStateName(entity.getName());

        return dto;
    }

    @Override
    public StateInfo copyConvertToEntity(StateInfoDTO dto, StateInfo entity) {

        if (entity == null || dto == null){
            return null;
        }

        entity.setCountryInfo(countryRepository.findByIdAndStatus(dto.getCountryId(), Status.ACTIVE));
        entity.setName(dto.getStateName());
        entity.setStatus(Status.ACTIVE);

        return entity;
    }

    @Override
    public List<StateInfoDTO> convertToDtoList(List<StateInfo> entities) {

        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());

    }

    @Override
    public List<StateInfo> convertToEntityList(List<StateInfoDTO> dtoList) {

        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());

    }
}
