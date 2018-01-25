package com.inventory.core.model.converter;

import com.inventory.core.model.dto.ServiceDTO;
import com.inventory.core.model.entity.ServiceInfo;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 1/25/18.
 */
@Service
public class ServiceConverter implements IListConvertable<ServiceInfo , ServiceDTO> , IConvertable<ServiceInfo , ServiceDTO>{

    @Override
    public ServiceInfo convertToEntity(ServiceDTO dto) {
        return copyConvertToEntity(dto , new ServiceInfo());
    }

    @Override
    public ServiceDTO convertToDto(ServiceInfo entity) {

        if (entity == null) {
            return null;
        }

        ServiceDTO dto = new ServiceDTO();

        dto.setExpireDays(entity.getExpireDays());
        dto.setRate(entity.getRate());
        dto.setServiceId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setTotalStore(entity.getTotalStore());
        dto.setTitle(entity.getTitle());

        return dto;
    }

    @Override
    public ServiceInfo copyConvertToEntity(ServiceDTO dto, ServiceInfo entity) {

        if (dto == null | entity == null) {
            return null;
        }

        entity.setExpireDays(dto.getExpireDays());
        entity.setRate(dto.getRate());
        entity.setStatus(dto.getStatus());
        entity.setTotalStore(dto.getTotalStore());
        entity.setTitle(dto.getTitle());

        return entity;
    }

    @Override
    public List<ServiceDTO> convertToDtoList(List<ServiceInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ServiceInfo> convertToEntityList(List<ServiceDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
