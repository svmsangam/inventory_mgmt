package com.inventory.core.model.converter;

import com.inventory.core.model.dto.PaymentInfoDTO;
import com.inventory.core.model.entity.PaymentInfo;
import com.inventory.core.model.repository.InvoiceInfoRepository;
import com.inventory.core.model.repository.PaymentRepository;
import com.inventory.core.model.repository.StoreInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import com.inventory.core.util.IConvertable;
import com.inventory.core.util.IListConvertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dhiraj on 10/10/17.
 */
@Service
public class PaymentInfoConverter implements IListConvertable<PaymentInfo , PaymentInfoDTO> , IConvertable<PaymentInfo , PaymentInfoDTO>{

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvoiceInfoRepository invoiceInfoRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentConverter paymentConverter;

    @Override
    public PaymentInfo convertToEntity(PaymentInfoDTO dto) {
        return copyConvertToEntity(dto , new PaymentInfo());
    }

    @Override
    public PaymentInfoDTO convertToDto(PaymentInfo entity) {

        if (entity == null) {
            return null;
        }

        PaymentInfoDTO dto = new PaymentInfoDTO();

        dto.setPaymentInfoId(entity.getId());
        dto.setCreatedById(entity.getCreatedBy().getId());
        dto.setCreatedByName(entity.getCreatedBy().getUsername());
        dto.setInvoiceInfoId(entity.getInvoiceInfo().getId());
        dto.setPaymentDate(entity.getCreated());
        dto.setPaymentId(entity.getId());
        dto.setReceivedPayment(paymentConverter.convertToDto(entity.getReceivedPayment()));
        dto.setRemark(entity.getRemark());
        dto.setStoreInfoId(entity.getStoreInfo().getId());

        return dto;
    }

    @Override
    public PaymentInfo copyConvertToEntity(PaymentInfoDTO dto, PaymentInfo entity) {

        if (entity == null | dto == null) {
            return null;
        }

        entity.setCreatedBy(userRepository.findById(dto.getCreatedById()));
        entity.setInvoiceInfo(invoiceInfoRepository.findById(dto.getInvoiceInfoId()));
        entity.setReceivedPayment(paymentRepository.findById(dto.getPaymentId()));
        entity.setRemark(dto.getRemark());
        entity.setStoreInfo(storeInfoRepository.findById(dto.getStoreInfoId()));
        entity.setRemark(dto.getRemark());

        return entity;
    }

    @Override
    public List<PaymentInfoDTO> convertToDtoList(List<PaymentInfo> entities) {
        return entities.parallelStream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<PaymentInfo> convertToEntityList(List<PaymentInfoDTO> dtoList) {
        return null;
    }
}
