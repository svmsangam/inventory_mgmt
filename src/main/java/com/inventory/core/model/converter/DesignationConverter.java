package com.inventory.core.model.converter;

import com.inventory.core.model.dto.DesignationInfoDTO;
import com.inventory.core.model.entity.Designation;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 8/12/17.
 */
@Service
public class DesignationConverter implements IConvertable<Designation , DesignationInfoDTO> , IListConvertable<Designation , DesignationInfoDTO>{

    @Override
    public Designation convertToEntity(DesignationInfoDTO dto) {
        return copyConvertToEntity(dto , new Designation());
    }

    @Override
    public DesignationInfoDTO convertToDto(Designation entity) {

        if (entity == null){
            return null;
        }

        DesignationInfoDTO dto = new DesignationInfoDTO();

        dto.setDesignationId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setCode(entity.getCode());
        dto.setRemarks(entity.getRemarks());
        dto.setVersion(entity.getVersion());

        return dto;
    }

    @Override
    public Designation copyConvertToEntity(DesignationInfoDTO dto, Designation entity) {

        if (entity == null | dto == null){
            return null;
        }

        entity.setTitle(dto.getTitle());
        entity.setCode(dto.getCode());
        entity.setRemarks(dto.getRemarks());

        return entity;
    }

    @Override
    public List<DesignationInfoDTO> convertToDtoList(List<Designation> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<Designation> convertToEntityList(List<DesignationInfoDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
