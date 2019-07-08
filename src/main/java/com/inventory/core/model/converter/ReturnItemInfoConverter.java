package com.inventory.core.model.converter;

import com.inventory.core.model.dto.ReturnItemInfoDTO;
import com.inventory.core.model.entity.ReturnItemInfo;
import com.inventory.core.model.repository.OrderItemInfoRepository;
import com.inventory.core.model.repository.OrderReturnInfoRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 1/17/18.
 */
@Service
public class ReturnItemInfoConverter implements IListConvertable<ReturnItemInfo , ReturnItemInfoDTO> , IConvertable<ReturnItemInfo , ReturnItemInfoDTO> {

    @Autowired
    private OrderItemInfoRepository orderItemInfoRepository;

    @Autowired
    private OrderReturnInfoRepository orderReturnInfoRepository;

    @Autowired
    private OrderItemInfoConverter orderItemInfoConverter;

    @Override
    public List<ReturnItemInfoDTO> convertToDtoList(List<ReturnItemInfo> entities) {

        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ReturnItemInfo> convertToEntityList(List<ReturnItemInfoDTO> dtoList) {

        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }

    @Override
    public ReturnItemInfo convertToEntity(ReturnItemInfoDTO dto) {

        return copyConvertToEntity(dto , new ReturnItemInfo());
    }

    @Override
    public ReturnItemInfoDTO convertToDto(ReturnItemInfo entity) {

        if (entity == null) {
            return null;
        }

        ReturnItemInfoDTO dto = new ReturnItemInfoDTO();

        dto.setReturnItemInfoId(entity.getId());
        dto.setOrderItemInfoId(entity.getOrderItemInfo().getId());
        dto.setQuantity(entity.getQuantity());
        dto.setStatus(entity.getStatus());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setOrderItemInfo(orderItemInfoConverter.convertToDto(entity.getOrderItemInfo()));
        dto.setRate(entity.getRate());

        return dto;
    }

    @Override
    public ReturnItemInfo copyConvertToEntity(ReturnItemInfoDTO dto, ReturnItemInfo entity) {

        if (entity == null | dto == null) {
            return null;
        }

        entity.setOrderItemInfo(orderItemInfoRepository.findById(dto.getOrderItemInfoId()));
        entity.setOrderReturnInfo(orderReturnInfoRepository.findById(dto.getOrderReturnInfoId()));
        entity.setQuantity(dto.getQuantity());
        entity.setRate(entity.getOrderItemInfo().getRate());
        entity.setTotalAmount(entity.getQuantity() * entity.getRate());
        entity.setTotalAmount(entity.getTotalAmount() - (entity.getTotalAmount() * entity.getOrderItemInfo().getDiscount() / 100));

        return entity;
    }
}
