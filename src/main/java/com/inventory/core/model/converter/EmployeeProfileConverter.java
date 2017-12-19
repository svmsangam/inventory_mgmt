package com.inventory.core.model.converter;

import com.inventory.core.model.dto.EmployeeProfileDTO;
import com.inventory.core.model.entity.EmployeeProfile;
import com.inventory.core.model.repository.CityInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 12/19/17.
 */
@Service
public class EmployeeProfileConverter implements IListConvertable<EmployeeProfile , EmployeeProfileDTO> , IConvertable<EmployeeProfile , EmployeeProfileDTO>{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityInfoConverter cityInfoConverter;

    @Autowired
    private CityInfoRepository cityInfoRepository;

    @Override
    public EmployeeProfile convertToEntity(EmployeeProfileDTO dto) {
        return copyConvertToEntity(dto , new EmployeeProfile());
    }

    @Override
    public EmployeeProfileDTO convertToDto(EmployeeProfile entity) {

        if (entity == null) {
            return null;
        }

        EmployeeProfileDTO dto = new EmployeeProfileDTO();

        dto.setEmployeeProfileId(entity.getId());
        dto.setCitizenShipCity(cityInfoConverter.convertToDto(entity.getCitizenShipCity()));
        dto.setCitizenShipCityId(entity.getCitizenShipCity().getId());
        dto.setCitizenShipNo(entity.getCitizenShipNo());
        dto.setCreatedById(entity.getCreatedBy().getId());
        dto.setCreatedByName(entity.getCreatedBy().getUsername());
        dto.setEmail(entity.getEmail());
        dto.setEmergencyCantact(entity.getEmergencyCantact());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setMiddleName(entity.getMiddleName());
        dto.setMobileNumber(entity.getMobileNumber());
        dto.setPermanentAddress(entity.getPermanentAddress());
        dto.setPermanentCity(cityInfoConverter.convertToDto(entity.getPermanentCity()));
        dto.setPermanentCityId(entity.getPermanentCity().getId());
        dto.setStatus(entity.getStatus());
        dto.setTemporaryAddress(entity.getTemporaryAddress());
        dto.setTemporaryCity(cityInfoConverter.convertToDto(entity.getTemporaryCity()));
        dto.setUserId(entity.getUser().getId());
        dto.setUsername(entity.getUser().getUsername());
        dto.setVersion(entity.getVersion());
        dto.setTemporaryCityId(entity.getTemporaryCity().getId());

        return dto;

    }

    @Override
    public EmployeeProfile copyConvertToEntity(EmployeeProfileDTO dto, EmployeeProfile entity) {

        if (entity == null | dto == null) {
            return null;
        }

        entity.setCitizenShipCity(cityInfoRepository.findOne(dto.getCitizenShipCityId()));
        entity.setCitizenShipNo(dto.getCitizenShipNo());
        entity.setCreatedBy(userRepository.findById(dto.getCreatedById()));
        entity.setEmail(dto.getEmail());
        entity.setEmergencyCantact(dto.getEmergencyCantact());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setMobileNumber(dto.getMobileNumber());
        entity.setPermanentAddress(dto.getPermanentAddress());
        entity.setPermanentCity(cityInfoRepository.findOne(dto.getPermanentCityId()));
        entity.setTemporaryAddress(dto.getTemporaryAddress());
        entity.setTemporaryCity(cityInfoRepository.findOne(dto.getTemporaryCityId()));
        entity.setUser(userRepository.findById(dto.getUserId()));

        return entity;
    }

    @Override
    public List<EmployeeProfileDTO> convertToDtoList(List<EmployeeProfile> entities) {
        return entities.parallelStream().filter(Objects::nonNull).map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeProfile> convertToEntityList(List<EmployeeProfileDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
