package com.inventory.core.model.converter;

import com.inventory.core.model.dto.SubCategoryInfoDTO;
import com.inventory.core.model.entity.SubCategoryInfo;
import com.inventory.core.model.enumconstant.CategoryType;
import com.inventory.core.model.repository.SubCategoryInfoRepository;
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
public class SubCategoryInfoConverter implements IConvertable<SubCategoryInfo, SubCategoryInfoDTO>, IListConvertable<SubCategoryInfo, SubCategoryInfoDTO> {

    @Autowired
    private SubCategoryInfoRepository subCategoryInfoRepository;

    @Override
    public SubCategoryInfo convertToEntity(SubCategoryInfoDTO dto) {
        return copyConvertToEntity(dto, new SubCategoryInfo());
    }

    @Override
    public SubCategoryInfoDTO convertToDto(SubCategoryInfo entity) {

        if (entity == null) {
            return null;
        }

        SubCategoryInfoDTO dto = new SubCategoryInfoDTO();

        dto.setSubCategoryId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setVersion(entity.getVersion());
        dto.setDepth(entity.getDepth());

        return dto;
    }

    @Override
    public SubCategoryInfo copyConvertToEntity(SubCategoryInfoDTO dto, SubCategoryInfo entity) {
        if (dto == null | entity == null) {
            return null;
        }

        entity.setCode(dto.getCode().trim());
        entity.setName(dto.getName().trim());
        entity.setDescription(dto.getDescription().trim());
        if (dto.getCategoryId() != null){

            if (dto.getCategoryId() <=0){
                entity.setType(CategoryType.Parent);
                entity.setDepth(0);
                entity.setParent(null);
            }else {
                entity.setParent(subCategoryInfoRepository.findById(dto.getCategoryId()));
                entity.setType(CategoryType.Child);
                entity.setDepth(entity.getParent().getDepth() + 1);
            }

        }else {
            entity.setDepth(0);
            entity.setType(CategoryType.Parent);
            entity.setParent(null);
        }

        return entity;
    }

    @Override
    public List<SubCategoryInfoDTO> convertToDtoList(List<SubCategoryInfo> entities) {

        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());

    }

    @Override
    public List<SubCategoryInfo> convertToEntityList(List<SubCategoryInfoDTO> dtoList) {

        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());

    }
}
