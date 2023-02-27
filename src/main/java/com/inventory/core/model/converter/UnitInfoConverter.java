package com.inventory.core.model.converter;

import com.inventory.core.model.dto.UnitInfoDTO;
import com.inventory.core.model.entity.UnitInfo;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 8/9/17.
 */
@Service
public class UnitInfoConverter implements IConvertable<UnitInfo, UnitInfoDTO>, IListConvertable<UnitInfo, UnitInfoDTO> {

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UnitInfo convertToEntity(UnitInfoDTO dto) {
        return copyConvertToEntity(dto, new UnitInfo());
    }

    @Override
    public UnitInfoDTO convertToDto(UnitInfo entity) {
        if (entity == null) {
            return null;
        }

        UnitInfoDTO dto = new UnitInfoDTO();

        dto.setUnitId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setCreatedById(entity.getCreatedBy().getId());
        dto.setCreatedByName(entity.getCreatedBy().getUsername());
        dto.setStoreInfoId(entity.getStoreInfo().getId());
        dto.setVersion(entity.getVersion());

        return dto;
    }

    @Override
    public UnitInfo copyConvertToEntity(UnitInfoDTO dto, UnitInfo entity) {
        if (dto == null | entity == null) {
            return null;
        }

        entity.setCode(dto.getCode().trim());
        entity.setName(dto.getName().trim());
        entity.setCreatedBy(userRepository.findById(dto.getCreatedById()));
        entity.setStoreInfo(storeInfoRepository.findById(dto.getStoreInfoId()).orElse(null));

        return entity;
    }

    @Override
    public List<UnitInfoDTO> convertToDtoList(List<UnitInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<UnitInfo> convertToEntityList(List<UnitInfoDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
