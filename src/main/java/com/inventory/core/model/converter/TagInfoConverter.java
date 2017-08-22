package com.inventory.core.model.converter;

import com.inventory.core.model.dto.TagInfoDTO;
import com.inventory.core.model.entity.TagInfo;
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
public class TagInfoConverter implements IConvertable<TagInfo, TagInfoDTO>, IListConvertable<TagInfo, TagInfoDTO> {

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TagInfo convertToEntity(TagInfoDTO dto) {
        return copyConvertToEntity(dto, new TagInfo());
    }

    @Override
    public TagInfoDTO convertToDto(TagInfo entity) {

        if (entity == null) {
            return null;
        }

        TagInfoDTO dto = new TagInfoDTO();

        dto.setTagId(entity.getId());
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
    public TagInfo copyConvertToEntity(TagInfoDTO dto, TagInfo entity) {

        if (dto == null | entity == null) {
            return null;
        }

        entity.setCode(dto.getCode().trim());
        entity.setName(dto.getName().trim());
        entity.setCreatedBy(userRepository.findOne(dto.getCreatedById()));
        entity.setStoreInfo(storeInfoRepository.findOne(dto.getStoreInfoId()));

        return entity;
    }

    @Override
    public List<TagInfoDTO> convertToDtoList(List<TagInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TagInfo> convertToEntityList(List<TagInfoDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
