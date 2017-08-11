package com.inventory.core.model.converter;

import com.inventory.core.model.dto.ProductInfoDTO;
import com.inventory.core.model.entity.ProductInfo;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.model.repository.SubCategoryInfoRepository;
import com.inventory.core.model.repository.UnitInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 8/10/17.
 */
@Service
public class ProductInfoConverter implements IConvertable<ProductInfo , ProductInfoDTO> , IListConvertable<ProductInfo , ProductInfoDTO>{

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubCategoryInfoRepository subCategoryInfoRepository;

    @Autowired
    private SubCategoryInfoConverter subCategoryInfoConverter;

    @Autowired
    private UnitInfoRepository unitInfoRepository;

    @Autowired
    private UnitInfoConverter unitInfoConverter;

    @Override
    public ProductInfo convertToEntity(ProductInfoDTO dto) {
        return copyConvertToEntity(dto , new ProductInfo());
    }

    @Override
    public ProductInfoDTO convertToDto(ProductInfo entity) {

        if (entity == null){
            return null;
        }

        ProductInfoDTO dto = new ProductInfoDTO();

        dto.setPoductId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setCreatedById(entity.getCreatedBy().getId());
        dto.setCreatedByName(entity.getCreatedBy().getUsername());
        dto.setDescription(entity.getDescription());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setStoreInfoId(entity.getStoreInfo().getId());
        dto.setSubCategoryId(entity.getSubCategoryInfo().getId());
        dto.setSubCategoryInfo(subCategoryInfoConverter.convertToDto(entity.getSubCategoryInfo()));
        dto.setTrendingLevel(entity.getTrendingLevel());
        dto.setUnitId(entity.getUnitInfo().getId());
        dto.setUnitInfo(unitInfoConverter.convertToDto(entity.getUnitInfo()));
        dto.setVersion(entity.getVersion());

        return dto;
    }

    @Override
    public ProductInfo copyConvertToEntity(ProductInfoDTO dto, ProductInfo entity) {

        if (entity == null | dto == null){
            return null;
        }

        entity.setCode(dto.getCode());
        entity.setCreatedBy(userRepository.findOne(dto.getCreatedById()));
        entity.setDescription(dto.getDescription());
        entity.setName(entity.getName());
        entity.setStoreInfo(storeInfoRepository.findOne(dto.getStoreInfoId()));
        entity.setSubCategoryInfo(subCategoryInfoRepository.findById(dto.getSubCategoryId()));
        entity.setTrendingLevel(dto.getTrendingLevel());
        entity.setUnitInfo(unitInfoRepository.findById(dto.getUnitId()));

        return entity;
    }

    @Override
    public List<ProductInfoDTO> convertToDtoList(List<ProductInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductInfo> convertToEntityList(List<ProductInfoDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }

    public List<ProductInfoDTO> convertPageToDtoList(Page<ProductInfo> entities) {

        List<ProductInfoDTO> dtoList = new ArrayList<>();

        for (ProductInfo entity : entities){
            dtoList.add(convertToDto(entity));
        }

        return dtoList;
    }
}
