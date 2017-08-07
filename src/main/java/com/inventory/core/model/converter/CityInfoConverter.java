package com.inventory.core.model.converter;

import com.inventory.core.model.entity.CityInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import com.inventory.core.model.dto.CityInfoDTO;
import com.inventory.core.model.repository.StateInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 4/25/17.
 */

@Service
public class CityInfoConverter implements IConvertable<CityInfo, CityInfoDTO>, IListConvertable<CityInfo , CityInfoDTO> {

    @Autowired
    private StateInfoRepository stateRepository;

    @Override
    public CityInfo convertToEntity(CityInfoDTO dto) {

        if (dto == null){
            return null;
        }

        return copyConvertToEntity(dto , new CityInfo());
    }

    @Override
    public CityInfoDTO convertToDto(CityInfo entity) {

        if (entity == null){
            return null;
        }

        CityInfoDTO dto = new CityInfoDTO();

        dto.setCityId(entity.getId());
        dto.setCityName(entity.getName());
        dto.setStateName(entity.getStateInfo().getName());
        dto.setCityStatus(entity.getStatus());
        dto.setCountryName(entity.getStateInfo().getCountryInfo().getName());
        dto.setCountryId(entity.getStateInfo().getCountryInfo().getId());

        return dto;
    }

    @Override
    public CityInfo copyConvertToEntity(CityInfoDTO dto, CityInfo entity) {

        if (entity == null || dto == null){
            return null;
        }

        entity.setName(dto.getCityName());
        entity.setStateInfo(stateRepository.findByIdAndStatus(dto.getStateId() , Status.ACTIVE));
        entity.setStatus(Status.ACTIVE);

        return entity;
    }

    @Override
    public List<CityInfoDTO> convertToDtoList(List<CityInfo> entities) {

        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());

    }

    @Override
    public List<CityInfo> convertToEntityList(List<CityInfoDTO> dtoList) {

        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());

    }
}
