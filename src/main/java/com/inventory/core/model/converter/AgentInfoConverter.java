package com.inventory.core.model.converter;

import com.inventory.core.model.dto.AgentInfoDTO;
import com.inventory.core.model.entity.AgentInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.CityInfoRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AgentInfoConverter implements IListConvertable<AgentInfo , AgentInfoDTO> , IConvertable<AgentInfo , AgentInfoDTO>{

    @Autowired
    private CityInfoConverter cityInfoConverter;

    @Autowired
    private CityInfoRepository cityInfoRepository;

    @Override
    public AgentInfo convertToEntity(AgentInfoDTO dto) {
        return copyConvertToEntity(dto , new AgentInfo());
    }

    @Override
    public AgentInfoDTO convertToDto(AgentInfo entity) {

        if (entity == null) {
            return null;
        }

        AgentInfoDTO dto = new AgentInfoDTO();

        dto.setAgentId(entity.getId());
        dto.setCityId(entity.getCityInfo().getId());
        dto.setCityInfo(cityInfoConverter.convertToDto(entity.getCityInfo()));
        dto.setCommision(entity.getCommision());
        dto.setEmail(entity.getEmail());
        dto.setFullName(entity.getFullName());
        dto.setMobileNo(entity.getMobileNo());
        dto.setStatus(entity.getStatus());
        dto.setUserId(entity.getUser().getId());
        dto.setUsername(entity.getUser().getUsername());

        return dto;
    }

    @Override
    public AgentInfo copyConvertToEntity(AgentInfoDTO dto, AgentInfo entity) {

        if (entity == null | dto == null) {
            return null;
        }

        entity.setFullName(dto.getFullName().trim());
        entity.setCityInfo(cityInfoRepository.findByIdAndStatus(dto.getCityId() , Status.ACTIVE));
        entity.setCommision(dto.getCommision());
        entity.setEmail(entity.getEmail());
        entity.setMobileNo(dto.getMobileNo());

        return entity;
    }

    @Override
    public List<AgentInfoDTO> convertToDtoList(List<AgentInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<AgentInfo> convertToEntityList(List<AgentInfoDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
