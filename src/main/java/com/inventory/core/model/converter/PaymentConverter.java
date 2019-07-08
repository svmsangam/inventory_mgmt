package com.inventory.core.model.converter;

import com.inventory.core.model.dto.PaymentDTO;
import com.inventory.core.model.entity.Payment;
import com.inventory.core.model.enumconstant.PaymentMethod;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.util.IConvertable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by dhiraj on 10/10/17.
 */
@Service
public class PaymentConverter implements IConvertable<Payment , PaymentDTO> {

    @Override
    public Payment convertToEntity(PaymentDTO dto) {
        return copyConvertToEntity(dto , new Payment());
    }

    @Override
    public PaymentDTO convertToDto(Payment entity) {

        if (entity == null) {
            return null;
        }


        PaymentDTO dto = new PaymentDTO();

        dto.setPaymentId(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setRemark(entity.getRemark());
        dto.setStatus(entity.getStatus());

        if (PaymentMethod.CHEQUE.equals(entity.getPaymentMethod())) {
            dto.setBankAccountNumber(entity.getBankAccountNumber());
            dto.setBankOfCheque(entity.getBankOfCheque());
            dto.setChequeDate(entity.getChequeDate());
            dto.setCommitedDateOfCheque(entity.getCommitedDateOfCheque());
        }

        return dto;
    }

    @Override
    public Payment copyConvertToEntity(PaymentDTO dto, Payment entity) {

        if (entity == null | dto == null) {
            return null;
        }

        entity.setAmount(dto.getAmount());
        entity.setPaymentMethod(dto.getPaymentMethod());
        entity.setRemark(dto.getRemark());

        if (PaymentMethod.CHEQUE.equals(dto.getPaymentMethod())) {
            entity.setBankAccountNumber(dto.getBankAccountNumber());
            entity.setBankOfCheque(dto.getBankOfCheque());
            entity.setChequeDate(dto.getChequeDate());
            entity.setCommitedDateOfCheque(dto.getCommitedDateOfCheque());
            entity.setStatus(Status.INACTIVE);
        }else {
            entity.setStatus(Status.ACTIVE);
            entity.setPaymentDate(new Date());
        }

        return entity;
    }
}
