package com.inventory.core.model.converter;

import com.inventory.core.model.dto.CategoryInfoDTO;
import com.inventory.core.model.entity.CategoryInfo;
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
public class CategoryInfoConverter implements IConvertable<CategoryInfo, CategoryInfoDTO>, IListConvertable<CategoryInfo, CategoryInfoDTO> {

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CategoryInfo convertToEntity(CategoryInfoDTO dto) {
        return copyConvertToEntity(dto , new CategoryInfo());
    }

    @Override
    public CategoryInfoDTO convertToDto(CategoryInfo entity) {
        if (entity == null){
            return null;
        }

        CategoryInfoDTO dto = new CategoryInfoDTO();

        dto.setCategiryId(entity.getId());
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
    public CategoryInfo copyConvertToEntity(CategoryInfoDTO dto, CategoryInfo entity) {
        if (dto == null | entity == null){
            return null;
        }

        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setCreatedBy(userRepository.findOne(dto.getCreatedById()));
        entity.setStoreInfo(storeInfoRepository.findOne(dto.getStoreInfoId()));

        return entity;
    }

    @Override
    public List<CategoryInfoDTO> convertToDtoList(List<CategoryInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<CategoryInfo> convertToEntityList(List<CategoryInfoDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
