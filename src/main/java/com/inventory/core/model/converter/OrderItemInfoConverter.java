package com.inventory.core.model.converter;

import com.inventory.core.model.dto.OrderItemInfoDTO;
import com.inventory.core.model.entity.OrderItemInfo;
import com.inventory.core.model.repository.ItemInfoRepository;
import com.inventory.core.model.repository.OrderInfoRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 8/30/17.
 */
@Service
public class OrderItemInfoConverter implements IListConvertable<OrderItemInfo , OrderItemInfoDTO> , IConvertable<OrderItemInfo , OrderItemInfoDTO> {

    @Autowired
    private ItemInfoRepository itemInfoRepository;

    @Autowired
    private ItemInfoConverter itemInfoConverter;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Override
    public OrderItemInfo convertToEntity(OrderItemInfoDTO dto) {
        return copyConvertToEntity(dto , new OrderItemInfo());
    }

    @Override
    public OrderItemInfoDTO convertToDto(OrderItemInfo entity) {

        if (entity == null) {
            return null;
        }

        OrderItemInfoDTO dto = new OrderItemInfoDTO();

        dto.setOrderItemInfoId(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setDiscount(entity.getDiscount());
        dto.setItemInfoDTO(itemInfoConverter.convertToDto(entity.getItemInfo()));
        dto.setItemInfoId(entity.getItemInfo().getId());
        dto.setOrderInfoId(entity.getOrderInfo().getId());
        dto.setQuantity(entity.getQuantity());
        dto.setRate(entity.getRate());
        dto.setStatus(entity.getStatus());

        return dto;
    }

    @Override
    public OrderItemInfo copyConvertToEntity(OrderItemInfoDTO dto, OrderItemInfo entity) {
        
        if (entity == null | dto == null) {
            return null;
        }

        entity.setAmount((dto.getRate() * dto.getQuantity()) - ((dto.getRate() * dto.getQuantity()) * dto.getDiscount() / 100));
        entity.setDiscount(dto.getDiscount());
        entity.setItemInfo(itemInfoRepository.findById(dto.getItemInfoId()));
        entity.setOrderInfo(orderInfoRepository.findOne(dto.getOrderInfoId()));
        entity.setQuantity(dto.getQuantity());
        entity.setRate(dto.getRate());
        
        return entity;
    }

    @Override
    public List<OrderItemInfoDTO> convertToDtoList(List<OrderItemInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderItemInfo> convertToEntityList(List<OrderItemInfoDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }
}
