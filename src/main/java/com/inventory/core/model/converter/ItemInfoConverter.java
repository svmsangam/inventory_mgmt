package com.inventory.core.model.converter;

import com.inventory.core.api.iapi.IOrderItemInfoApi;
import com.inventory.core.model.dto.ItemInfoDTO;
import com.inventory.core.model.entity.ItemInfo;
import com.inventory.core.model.repository.ClientInfoRepository;
import com.inventory.core.model.repository.LotInfoRepository;
import com.inventory.core.model.repository.ProductInfoRepository;
import com.inventory.core.model.repository.TagInfoRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 8/12/17.
 */
@Service
public class ItemInfoConverter implements IConvertable<ItemInfo, ItemInfoDTO>, IListConvertable<ItemInfo, ItemInfoDTO> {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private ProductInfoConverter productInfoConverter;

    @Autowired
    private TagInfoRepository tagInfoRepository;

    @Autowired
    private TagInfoConverter tagInfoConverter;

    @Autowired
    private LotInfoConverter lotInfoConverter;

    @Autowired
    private LotInfoRepository lotInfoRepository;

    @Autowired
    private IOrderItemInfoApi orderItemInfoApi;

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    @Override
    public ItemInfo convertToEntity(ItemInfoDTO dto) {
        return copyConvertToEntity(dto, new ItemInfo());
    }

    @Override
    public ItemInfoDTO convertToDto(ItemInfo entity) {

        if (entity == null) {
            return null;
        }

        ItemInfoDTO dto = new ItemInfoDTO();

        dto.setItemId(entity.getId());
        dto.setTagInfo(tagInfoConverter.convertToDto(entity.getTagInfo()));
        dto.setCostPrice(entity.getCostPrice());
        dto.setExpireDate(entity.getExpireDate());
        dto.setInStock(entity.getInStock());
        dto.setLotId(entity.getLotInfo().getId());
        dto.setLotInfo(lotInfoConverter.convertToDto(entity.getLotInfo()));
        dto.setProductId(entity.getProductInfo().getId());
        dto.setProductInfo(productInfoConverter.convertToDto(entity.getProductInfo()));
        dto.setQuantity(entity.getQuantity());
        dto.setSellingPrice(entity.getSellingPrice());
        dto.setStatus(entity.getStatus());
        dto.setTagId(entity.getTagInfo().getId());
        dto.setThreshold(entity.getThreshold());
        dto.setVersion(entity.getVersion());
        dto.setCode(entity.getCode());

        if (entity.getVendorInfo() != null){
            dto.setVendorId(entity.getVendorInfo().getId());
            dto.setVendorName(entity.getVendorInfo().getCompanyName());
        }
        //dto.setTotalCost(entity.getCostPrice() * entity.getQuantity());

        return dto;
    }


    public ItemInfoDTO convertToDtoWithSaleAmount(ItemInfo entity) {

        if (entity == null) {
            return null;
        }

        ItemInfoDTO dto = new ItemInfoDTO();

        dto.setItemId(entity.getId());
        dto.setTagInfo(tagInfoConverter.convertToDto(entity.getTagInfo()));
        dto.setCostPrice(entity.getCostPrice());
        dto.setExpireDate(entity.getExpireDate());
        dto.setInStock(entity.getInStock());
        dto.setLotId(entity.getLotInfo().getId());
        dto.setLotInfo(lotInfoConverter.convertToDto(entity.getLotInfo()));
        dto.setProductId(entity.getProductInfo().getId());
        dto.setProductInfo(productInfoConverter.convertToDto(entity.getProductInfo()));
        dto.setQuantity(entity.getQuantity());
        dto.setSellingPrice(entity.getSellingPrice());
        dto.setStatus(entity.getStatus());
        dto.setTagId(entity.getTagInfo().getId());
        dto.setThreshold(entity.getThreshold());
        dto.setVersion(entity.getVersion());
        dto.setTotalCost(entity.getCostPrice() * entity.getQuantity());
        dto.setTotalSale(orderItemInfoApi.getTotalSaleAmountOfItem(entity.getId()));
        dto.setCode(entity.getCode());
        if (entity.getVendorInfo() != null) {
            dto.setVendorId(entity.getVendorInfo().getId());
            if (entity.getVendorInfo().getCompanyName() != null && !entity.getVendorInfo().getCompanyName().isEmpty()){
                dto.setVendorName(entity.getVendorInfo().getCompanyName());
            }else {
                dto.setVendorName(entity.getVendorInfo().getName());
            }
        }

        return dto;
    }

    @Override
    public ItemInfo copyConvertToEntity(ItemInfoDTO dto, ItemInfo entity) {

        if (entity == null | dto == null) {
            return null;
        }

        entity.setCostPrice(dto.getCostPrice());
        entity.setExpireDate(dto.getExpireDate());
        entity.setInStock(Math.toIntExact(dto.getQuantity()));
        entity.setLotInfo(lotInfoRepository.findById(dto.getLotId()).orElse(null));
        entity.setProductInfo(productInfoRepository.findById(dto.getProductId()).orElse(null));
        entity.setQuantity(dto.getQuantity());
        entity.setSellingPrice(dto.getSellingPrice());
        entity.setTagInfo(tagInfoRepository.findById(dto.getTagId()).orElse(null));
        entity.setThreshold(dto.getThreshold());
        entity.setCode(dto.getCode());

        if (dto.getVendorId() != null){
            entity.setVendorInfo(clientInfoRepository.findById(dto.getVendorId()).orElse(null));
        }

        return entity;
    }

    @Override
    public List<ItemInfoDTO> convertToDtoList(List<ItemInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<ItemInfoDTO> convertToDtoListWithSaleAmount(List<ItemInfo> entities) {
        return entities.parallelStream().map(this::convertToDtoWithSaleAmount).collect(Collectors.toList());
    }

    @Override
    public List<ItemInfo> convertToEntityList(List<ItemInfoDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
