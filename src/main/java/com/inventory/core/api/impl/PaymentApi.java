package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IPaymentApi;
import com.inventory.core.model.converter.PaymentConverter;
import com.inventory.core.model.dto.PaymentDTO;
import com.inventory.core.model.entity.Payment;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dhiraj on 10/10/17.
 */
@Service
public class PaymentApi implements IPaymentApi{

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentConverter paymentConverter;

    @Override
    public PaymentDTO save(PaymentDTO paymentDTO) {

        Payment payment = paymentConverter.convertToEntity(paymentDTO);

        payment.setStatus(Status.ACTIVE);

        return paymentConverter.convertToDto(paymentRepository.save(payment));
    }

    @Override
    public PaymentDTO show(long paymentId, Status status) {
        return paymentConverter.convertToDto(paymentRepository.findByIdAndStatus(paymentId , status));
    }

    @Override
    public PaymentDTO getById(long paymentId) {
        return paymentConverter.convertToDto(paymentRepository.findById(paymentId));
    }
}
