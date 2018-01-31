package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IInvoiceInfoApi;
import com.inventory.core.api.iapi.ILedgerInfoApi;
import com.inventory.core.api.iapi.IPaymentApi;
import com.inventory.core.api.iapi.IPaymentInfoApi;
import com.inventory.core.model.converter.PaymentInfoConverter;
import com.inventory.core.model.dto.PaymentDTO;
import com.inventory.core.model.dto.PaymentInfoDTO;
import com.inventory.core.model.entity.InvoiceInfo;
import com.inventory.core.model.entity.Payment;
import com.inventory.core.model.entity.PaymentInfo;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.enumconstant.PaymentMethod;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.InvoiceInfoRepository;
import com.inventory.core.model.repository.PaymentInfoRepository;
import com.inventory.core.model.repository.PaymentRepository;
import com.inventory.core.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.ArrayList;
import java.util.Date;
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
    private ILedgerInfoApi ledgerInfoApi;

    @Autowired
    private IInvoiceInfoApi invoiceInfoApi;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private InvoiceInfoRepository invoiceInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Lock(LockModeType.OPTIMISTIC)
    public PaymentInfoDTO save(PaymentInfoDTO paymentInfoDTO) {

        PaymentDTO paymentDTO = paymentApi.save(paymentInfoDTO.getReceivedPayment());

        paymentInfoDTO.setPaymentId(paymentDTO.getPaymentId());

        PaymentInfo paymentInfo = paymentInfoConverter.convertToEntity(paymentInfoDTO);

        paymentInfo = paymentInfoRepository.save(paymentInfo);

        if (PaymentMethod.CASH.equals(paymentInfo.getReceivedPayment().getPaymentMethod())){
            ledgerInfoApi.saveOnPayment(paymentInfo.getId());
            invoiceInfoApi.updateOnPayment(paymentInfo.getId());
        } else if (PaymentMethod.CHEQUE.equals(paymentInfo.getReceivedPayment().getPaymentMethod())){
            invoiceInfoApi.updateVersion(paymentInfoDTO.getInvoiceInfoId());
        }

        return paymentInfoConverter.convertToDto(paymentInfo);
    }

    @Override
    public void refundOnInvoiceCancel(long invoiceId, long createdById){

        InvoiceInfo invoiceInfo = invoiceInfoRepository.findById(invoiceId);

        Double amount = paymentInfoRepository.findReceivedAmountByStatusAndInvoiceInfo(Status.ACTIVE , invoiceId);

        if (amount != null) {

            Payment payment = paymentApi.save(amount);

            PaymentInfo paymentInfo = new PaymentInfo();

            paymentInfo.setCreatedBy(userRepository.findById(createdById));
            paymentInfo.setInvoiceInfo(invoiceInfo);
            paymentInfo.setRefundPayment(payment);
            paymentInfo.setStoreInfo(invoiceInfo.getStoreInfo());
            paymentInfo.setRemark("cash returned due to of cancel invoice");

            paymentInfoRepository.save(paymentInfo);
        }


    }

    @Override
    @Lock(LockModeType.OPTIMISTIC)
    public long collectChuque(long paymentInfoId) {

        PaymentInfo paymentInfo = paymentInfoRepository.findById(paymentInfoId);

        Payment payment = paymentInfo.getReceivedPayment();

        payment.setStatus(Status.ACTIVE);

        payment.setPaymentDate(new Date());

        paymentRepository.save(payment);

        invoiceInfoApi.updateOnPayment(paymentInfoId);

        ledgerInfoApi.saveOnPayment(paymentInfoId);

        return paymentInfo.getInvoiceInfo().getId();
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
    public List<PaymentInfoDTO> getAllByStatusInAndStoreAndInvoiceInfo(List<Status> status, long storeId, long invoiceInfoId) {

        List<PaymentInfoDTO> paymentInfoDTOList = new ArrayList<>();

        paymentInfoDTOList = paymentInfoConverter.convertToDtoList(paymentInfoRepository.findByStatusInAndStoreAndInvoiceInfo(status , storeId , invoiceInfoId));

        List<PaymentInfoDTO> refundInfoDTOList = new ArrayList<>();

        refundInfoDTOList = paymentInfoConverter.convertToDtoList(paymentInfoRepository.findRefundByStatusAndStoreAndInvoiceInfo(Status.ACTIVE , storeId , invoiceInfoId));

        if (refundInfoDTOList != null) {
            paymentInfoDTOList.addAll(refundInfoDTOList);
        }

        return paymentInfoDTOList;
    }

    @Override
    public double getTotalPaymentByStoreInfoAndStatus(long storeInfoId, Status status) {

        Double amount = paymentInfoRepository.findTotalPaymentByStoreInfoAndStatus(storeInfoId , status);

        if (amount == null) {
            return 0;
        }

        return amount;
    }

    @Override
    public double getToDayTotalPaymentByStoreInfoAndStatus(long storeInfoId, Status status) {

        Double amount = paymentInfoRepository.findToDayTotalPaymentByStoreInfoAndStatus(storeInfoId , status);

        if (amount == null) {
            return 0;
        }

        return amount;
    }
}
