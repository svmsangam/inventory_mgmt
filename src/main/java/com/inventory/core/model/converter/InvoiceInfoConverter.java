package com.inventory.core.model.converter;

import com.inventory.core.model.dto.InvoiceInfoDTO;
import com.inventory.core.model.dto.LedgerInfoDTO;
import com.inventory.core.model.entity.InvoiceInfo;
import com.inventory.core.model.entity.LedgerInfo;
import com.inventory.core.model.entity.OrderInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.FiscalYearInfoRepository;
import com.inventory.core.model.repository.OrderInfoRepository;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 9/13/17.
 */
@Service
public class InvoiceInfoConverter implements IConvertable<InvoiceInfo , InvoiceInfoDTO> , IListConvertable<InvoiceInfo , InvoiceInfoDTO>{

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderInfoConverter orderInfoConverter;

    @Autowired
    private StoreInfoConverter storeInfoConverter;

    @Autowired
    private FiscalYearInfoConverter fiscalYearInfoConverter;

    @Autowired
    private FiscalYearInfoRepository fiscalYearInfoRepository;

    @Override
    public InvoiceInfo convertToEntity(InvoiceInfoDTO dto) {
        return copyConvertToEntity(dto , new InvoiceInfo());
    }

    @Override
    public InvoiceInfoDTO convertToDto(InvoiceInfo entity) {

        if (entity == null) {
            return null;
        }

        InvoiceInfoDTO dto = new InvoiceInfoDTO();

        dto.setInvoiceId(entity.getId());
        dto.setCreatedById(entity.getCreatedBy().getId());
        dto.setCreatedByName(entity.getCreatedBy().getUsername());
        dto.setDescription(entity.getDescription());
        dto.setInvoiceDate(entity.getInvoiceDate());
        dto.setInvoiceNo(entity.getInvoiceNo());
        dto.setOrderInfo(orderInfoConverter.convertToDto(entity.getOrderInfo()));
        dto.setReceivableAmount(entity.getReceivableAmount());
        dto.setOrderInfoId(entity.getOrderInfo().getId());
        dto.setStatus(entity.getStatus());
        dto.setStoreInfoId(entity.getStoreInfo().getId());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setVersion(entity.getVersion());
        dto.setStoreInfoDTO(storeInfoConverter.convertToDto(entity.getStoreInfo()));
        dto.setFiscalYearInfo(fiscalYearInfoConverter.convertToDto(entity.getFiscalYearInfo()));
        dto.setCanceled(entity.isCanceled());
        dto.setCancelNote(entity.getCancelNote());

        return dto;
    }

    @Override
    public InvoiceInfo copyConvertToEntity(InvoiceInfoDTO dto, InvoiceInfo entity) {

        if (entity == null | dto == null) {
            return null;
        }

        entity.setCreatedBy(userRepository.findOne(dto.getCreatedById()));
        entity.setDescription(dto.getDescription());
        entity.setInvoiceDate(dto.getInvoiceDate());
        entity.setInvoiceNo(dto.getInvoiceNo());
        entity.setOrderInfo(orderInfoRepository.findOne(dto.getOrderInfoId()));
        entity.setReceivableAmount(entity.getOrderInfo().getGrandTotal());
        entity.setTotalAmount(entity.getOrderInfo().getGrandTotal());
        entity.setStoreInfo(storeInfoRepository.findById(dto.getStoreInfoId()));
        entity.setFiscalYearInfo(fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE , dto.getStoreInfoId() , true));
        entity.setCanceled(false);

        return entity;
    }

    public InvoiceInfo convertToEntity(long orderInfoId , long createdById) {

        OrderInfo orderInfo = orderInfoRepository.findOne(orderInfoId);

        InvoiceInfo entity = new InvoiceInfo();

        entity.setCreatedBy(userRepository.findOne(createdById));
        entity.setDescription(orderInfo.getDescription());
        entity.setInvoiceDate(new Date());
        entity.setOrderInfo(orderInfo);
        entity.setReceivableAmount(orderInfo.getGrandTotal());
        entity.setStatus(Status.ACTIVE);
        entity.setTotalAmount(orderInfo.getGrandTotal());
        entity.setStoreInfo(orderInfo.getStoreInfo());
        entity.setFiscalYearInfo(fiscalYearInfoRepository.findByStatusAndStoreInfoAndSelected(Status.ACTIVE , orderInfo.getStoreInfo().getId() , true));
        entity.setCanceled(false);

        return entity;
    }

    @Override
    public List<InvoiceInfoDTO> convertToDtoList(List<InvoiceInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceInfo> convertToEntityList(List<InvoiceInfoDTO> dtoList) {
        return dtoList.parallelStream().filter(Objects::nonNull).map(this::convertToEntity).collect(Collectors.toList());
    }

    public List<InvoiceInfoDTO> convertPageToDtoList(Page<InvoiceInfo> entities) {

        List<InvoiceInfoDTO> dtoList = new ArrayList<>();

        for (InvoiceInfo entity : entities) {
            dtoList.add(convertToDto(entity));
        }

        return dtoList;
    }
}
