package com.inventory.core.model.converter;

import com.inventory.core.model.dto.StockInfoDTO;
import com.inventory.core.model.entity.StockInfo;
import com.inventory.core.model.repository.ProductInfoRepository;
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
public class StockInfoConverter implements IConvertable<StockInfo, StockInfoDTO>, IListConvertable<StockInfo, StockInfoDTO> {

    @Autowired
    private ProductInfoConverter productInfoConverter;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public StockInfo convertToEntity(StockInfoDTO dto) {

        return copyConvertToEntity(dto, new StockInfo());
    }

    @Override
    public StockInfoDTO convertToDto(StockInfo entity) {

        if (entity == null) {
            return null;
        }

        StockInfoDTO dto = new StockInfoDTO();

        dto.setProductId(entity.getId());
        dto.setInStock(entity.getInStock());
        dto.setProductId(entity.getProductInfo().getId());
        dto.setProductInfo(productInfoConverter.convertToDto(entity.getProductInfo()));
        dto.setQuantity(entity.getQuantity());
        dto.setStatus(entity.getStatus());

        return dto;
    }

    @Override
    public StockInfo copyConvertToEntity(StockInfoDTO dto, StockInfo entity) {

        if (entity == null | dto == null) {
            return null;
        }

        entity.setInStock(dto.getInStock());
        entity.setQuantity(dto.getQuantity());
        entity.setProductInfo(productInfoRepository.findById(dto.getProductId()));

        return entity;
    }

    @Override
    public List<StockInfoDTO> convertToDtoList(List<StockInfo> entities) {

        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());

    }

    @Override
    public List<StockInfo> convertToEntityList(List<StockInfoDTO> dtoList) {

        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());

    }

    public List<StockInfoDTO> convertPageToDtoList(Page<StockInfo> entities) {

        List<StockInfoDTO> dtoList = new ArrayList<>();

        for (StockInfo entity : entities) {
            dtoList.add(convertToDto(entity));
        }

        return dtoList;
    }
}
