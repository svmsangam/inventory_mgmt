package com.inventory.core.model.converter;

import com.inventory.core.model.dto.InvoiceInfoDTO;
import com.inventory.core.model.dto.OrderInfoDTO;
import com.inventory.core.model.entity.InvoiceInfo;
import com.inventory.core.model.entity.OrderInfo;
import com.inventory.core.model.repository.ClientInfoRepository;
import com.inventory.core.model.repository.StoreInfoRepository;
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
 * Created by dhiraj on 8/27/17.
 */
@Service
public class OrderInfoConverter implements IListConvertable<OrderInfo , OrderInfoDTO> , IConvertable<OrderInfo , OrderInfoDTO>{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    @Autowired
    private ClientInfoConverter clientInfoConverter;

    @Override
    public OrderInfo convertToEntity(OrderInfoDTO dto) {
        return copyConvertToEntity(dto , new OrderInfo());
    }

    @Override
    public OrderInfoDTO convertToDto(OrderInfo entity) {

        if (entity == null){
            return null;
        }

        OrderInfoDTO dto = new OrderInfoDTO();

        dto.setOrderId(entity.getId());
        dto.setClientId(entity.getClientInfo().getId());
        dto.setCreatedById(entity.getCreatedBy().getId());
        dto.setClientInfo(clientInfoConverter.convertToDto(entity.getClientInfo()));
        dto.setCreatedByName(entity.getCreatedBy().getUsername());
        dto.setDeliveryAddress(entity.getDeliveryAddress());
        dto.setDeliveryDate(entity.getDeliveryDate());
        dto.setDescription(entity.getDescription());
        dto.setGrandTotal(entity.getGrandTotal());
        dto.setOrderDate(entity.getOrderDate());
        dto.setOrderNo(entity.getOrderNo());
        dto.setOrderType(entity.getOrderType());
        dto.setPurchaseTrack(entity.getPurchaseTrack());
        dto.setSaleTrack(entity.getSaleTrack());
        dto.setStatus(entity.getStatus());
        dto.setStoreInfoId(entity.getStoreInfo().getId());
        dto.setTax(entity.getTax());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setVersion(entity.getVersion());

        return dto;
    }

    @Override
    public OrderInfo copyConvertToEntity(OrderInfoDTO dto, OrderInfo entity) {

        if (entity == null | dto == null) {
            return null;
        }

        entity.setClientInfo(clientInfoRepository.findById(dto.getClientId()).orElseThrow());
        entity.setCreatedBy(userRepository.findById(dto.getCreatedById()));
        entity.setDeliveryAddress(dto.getDeliveryAddress());
        entity.setDeliveryDate(dto.getDeliveryDate());
        entity.setDescription(dto.getDescription());
        entity.setGrandTotal(dto.getGrandTotal());
        entity.setOrderDate(dto.getOrderDate());
        entity.setOrderNo(dto.getOrderNo());
        entity.setOrderType(dto.getOrderType());
        entity.setStoreInfo(storeInfoRepository.findById(dto.getStoreInfoId()));
        entity.setTax(dto.getTax());
        entity.setTotalAmount(dto.getTotalAmount());

        return entity;
    }

    @Override
    public List<OrderInfoDTO> convertToDtoList(List<OrderInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderInfo> convertToEntityList(List<OrderInfoDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }

    public List<OrderInfoDTO> convertPageToDtoList(Page<OrderInfo> entities) {

        List<OrderInfoDTO> dtoList = new ArrayList<>();

        for (OrderInfo entity : entities) {
            dtoList.add(convertToDto(entity));
        }

        return dtoList;
    }
}
