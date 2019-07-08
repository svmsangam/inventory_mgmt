package com.inventory.core.model.converter;

import com.inventory.core.model.dto.OrderReturnInfoDTO;
import com.inventory.core.model.entity.InvoiceInfo;
import com.inventory.core.model.entity.OrderReturnInfo;
import com.inventory.core.model.repository.InvoiceInfoRepository;
import com.inventory.core.model.repository.OrderInfoRepository;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 1/17/18.
 */
@Service
public class OrderReturnInfoConverter implements IListConvertable<OrderReturnInfo , OrderReturnInfoDTO> , IConvertable<OrderReturnInfo , OrderReturnInfoDTO> {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvoiceInfoRepository invoiceInfoRepository;

    @Override
    public List<OrderReturnInfoDTO> convertToDtoList(List<OrderReturnInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderReturnInfo> convertToEntityList(List<OrderReturnInfoDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }

    @Override
    public OrderReturnInfo convertToEntity(OrderReturnInfoDTO dto) {

        return copyConvertToEntity(dto , new OrderReturnInfo());
    }

    @Override
    public OrderReturnInfoDTO convertToDto(OrderReturnInfo entity) {

        if (entity == null) {

            return null;
        }

        OrderReturnInfoDTO dto = new OrderReturnInfoDTO();

        dto.setCreatedById(entity.getCreatedBy().getId());
        dto.setCreatedByName(entity.getCreatedBy().getUsername());
        dto.setNote(entity.getNote());
        //dto.setOrderInfoId(entity.getOrderInfo().getId());
        //dto.setOrderInfo(orderInfoConverter.convertToDto(entity.getOrderInfo()));
        dto.setOrderReturnInfoId(entity.getId());
        dto.setReturnDate(entity.getReturnDate());
        dto.setStatus(entity.getStatus());
        dto.setStoreId(entity.getStoreInfo().getId());
        dto.setTotalAmount(entity.getTotalAmount());

        return dto;
    }

    @Override
    public OrderReturnInfo copyConvertToEntity(OrderReturnInfoDTO dto, OrderReturnInfo entity) {

        if (entity == null | dto == null){
            return null;
        }

        entity.setCreatedBy(userRepository.findById(dto.getCreatedById()));
        entity.setNote(entity.getNote());
        entity.setOrderInfo(orderInfoRepository.findOne(dto.getOrderInfoId()));
        entity.setReturnDate(dto.getReturnDate());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setStoreInfo(storeInfoRepository.findById(dto.getStoreId()));

        return entity;
    }

    public OrderReturnInfo convertToEntity(long invoiceId , long createdBy){

        InvoiceInfo invoiceInfo = invoiceInfoRepository.findById(invoiceId);

        OrderReturnInfo entity = new OrderReturnInfo();

        entity.setCreatedBy(userRepository.findById(createdBy));
        entity.setNote(invoiceInfo.getCancelNote());
        entity.setOrderInfo(invoiceInfo.getOrderInfo());
        entity.setReturnDate(new Date());
        entity.setTotalAmount(invoiceInfo.getTotalAmount());
        entity.setStoreInfo(invoiceInfo.getStoreInfo());

        return entity;
    }
}
