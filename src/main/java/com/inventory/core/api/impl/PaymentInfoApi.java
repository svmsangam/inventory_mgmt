package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IPaymentApi;
import com.inventory.core.api.iapi.IPaymentInfoApi;
import com.inventory.core.model.converter.PaymentInfoConverter;
import com.inventory.core.model.dto.PaymentDTO;
import com.inventory.core.model.dto.PaymentInfoDTO;
import com.inventory.core.model.entity.PaymentInfo;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.PaymentInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dhiraj on 10/10/17.
 */

@Service
@Transactional
public class PaymentInfoApi implements IPaymentInfoApi{

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private PaymentInfoConverter paymentInfoConverter;

    @Autowired
    private IPaymentApi paymentApi;

    @Autowired

    @Override
    public PaymentInfoDTO save(PaymentInfoDTO paymentInfoDTO) {

        PaymentDTO paymentDTO = paymentApi.save(paymentInfoDTO.getReceivedPayment());

        paymentInfoDTO.setPaymentId(paymentDTO.getPaymentId());

        paymentInfoDTO.setRemark("payment made");

        PaymentInfo paymentInfo = paymentInfoConverter.convertToEntity(paymentInfoDTO);

        return paymentInfoConverter.convertToDto(paymentInfoRepository.save(paymentInfo));
    }

    @Override
    public PaymentInfoDTO getById(long paymentInfoId) {
        return paymentInfoConverter.convertToDto(paymentInfoRepository.findById(paymentInfoId));
    }

    @Override
    public PaymentInfoDTO getByIdAndStatus(long paymentInfoId, Status status) {
        return paymentInfoConverter.convertToDto(paymentInfoRepository.findByIdAndStatus(paymentInfoId , status));
    }

    @Override
    public PaymentInfoDTO show(long paymentInfoId, Status status, long storeId) {
        return paymentInfoConverter.convertToDto(paymentInfoRepository.findByIdAndStatusAndStore(paymentInfoId , status , storeId));
    }

    @Override
    public PaymentInfoDTO getByIdAndStatusAndStoreAndInvoiceInfo(long paymentInfoId, Status status, long storeId, long invoiceInfoId) {
        return paymentInfoConverter.convertToDto(paymentInfoRepository.findByIdAndStatusAndStoreAndInvoiceInfo(paymentInfoId , status , storeId , invoiceInfoId));
    }

    @Override
    public List<PaymentInfoDTO> getByStatusAndStoreAndInvoiceInfo(Status status, long storeId, long invoiceInfoId) {
        return paymentInfoConverter.convertToDtoList(paymentInfoRepository.findByStatusAndStoreAndInvoiceInfo(status , storeId , invoiceInfoId));
    }
}
