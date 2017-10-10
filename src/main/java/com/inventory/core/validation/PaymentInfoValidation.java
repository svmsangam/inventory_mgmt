package com.inventory.core.validation;

import com.inventory.core.model.dto.PaymentInfoDTO;
import com.inventory.core.model.entity.InvoiceInfo;
import com.inventory.core.model.enumconstant.PaymentMethod;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.InvoiceInfoRepository;
import com.inventory.web.error.PaymentInfoError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Date;
import java.util.List;

/**
 * Created by dhiraj on 10/10/17.
 */
@Service
public class PaymentInfoValidation extends GlobalValidation{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InvoiceInfoRepository invoiceInfoRepository;

    PaymentInfoError error;

    public PaymentInfoError onSave(PaymentInfoDTO paymentInfoDTO , long storeId , long invoiceId, BindingResult result) {

        error = new PaymentInfoError();

        boolean valid = true;

        if (result.hasErrors()) {

            List<FieldError> errors = result.getFieldErrors();

            for (FieldError errorResult : errors) {

                if (errorResult.getField().equals("receivedPayment.amount")) {
                    error.setAmount("invalid amount");
                } else if (errorResult.getField().equals("paymentMethod")) {
                    error.setPaymentMethod("receivedPayment.invalid paymentMethod");
                } else if (errorResult.getField().equals("remark")) {
                    error.setRemark("invalid remark");
                } else if (errorResult.getField().equals("receivedPayment.chequeDate")) {
                    error.setChequeDate("invalid chequeDate");
                } else if (errorResult.getField().equals("receivedPayment.commitedDateOfCheque")) {
                    error.setCommitedDateOfCheque("invalid commitedDateOfCheque");
                } else if (errorResult.getField().equals("receivedPayment.bankOfCheque")) {
                    error.setBankOfCheque("invalid bank");
                } else if (errorResult.getField().equals("receivedPayment.bankAccountNumber")) {
                    error.setBankAccountNumber("invalid bankAccountNumber");
                } else if (errorResult.getField().equals("invoiceInfoId")) {
                    error.setInvoice("invalid invoiceInfo");
                }
            }

            error.setValid(false);

            return error;
        }


        valid = valid && checkAmount(paymentInfoDTO.getReceivedPayment().getAmount(), invoiceId);

        valid = valid && checkRemark(paymentInfoDTO.getRemark());

        if (paymentInfoDTO.getReceivedPayment() != null && PaymentMethod.CHEQUE.equals(paymentInfoDTO.getReceivedPayment().getPaymentMethod())){

            valid = valid && checkChequeDate(paymentInfoDTO.getReceivedPayment().getChequeDate());

            valid = valid && checkCommitedDateOfCheque(paymentInfoDTO.getReceivedPayment().getCommitedDateOfCheque());

            valid = valid && checkBankOfCheque(paymentInfoDTO.getReceivedPayment().getBankOfCheque());

            valid = valid && checkBankAccountNo(paymentInfoDTO.getReceivedPayment().getBankAccountNumber());

        } else if(paymentInfoDTO.getReceivedPayment() == null && PaymentMethod.CHEQUE.equals(paymentInfoDTO.getReceivedPayment().getPaymentMethod())){

            error.setBankAccountNumber("enter bankAccountNumber");

            error.setCommitedDateOfCheque("enter commitedDateOfCheque");

            error.setBankOfCheque("enter bank");

            error.setChequeDate("enter chequeDate");

            valid = false;
        }

        valid = valid && checkInvoice(invoiceId , storeId);

        error.setValid(valid);

        return error;
    }

    private boolean checkAmount(Double value , long invoiceId ){

        error.setAmount(checkDouble(value , 1, 3 , "amount" , true));

        if (!"".equals(error.getAmount())){
            return false;
        }else {
            InvoiceInfo invoiceInfo = invoiceInfoRepository.findById(invoiceId);

            if (invoiceInfo != null & value > invoiceInfo.getReceivableAmount()){
                error.setAmount("amount must be less than or equals with " + invoiceInfo.getReceivableAmount());
                return false;
            }
        }

        return true;
    }

    private boolean checkChequeDate(Date value){

        try {
            error.setChequeDate(checkDate(value , "checque Date" , true , false , false));

        }catch (Exception e){
            error.setChequeDate("invalid date");
            return false;
        }

        if (!"".equals(error.getChequeDate())){
            return false;
        }

        return true;
    }

    private boolean checkRemark(String value){

        error.setRemark(checkString(value , 5 , 100 , "remarks" , true));

        if (!"".equals(error.getRemark())){

            return false;
        }

        return true;
    }

    private boolean checkCommitedDateOfCheque(Date value ){

        try {

            error.setCommitedDateOfCheque(checkDate(value, "commited Date" , true , false , true));

        }catch (Exception e){
            error.setChequeDate("invalid date");
            return false;
        }

        if (!"".equals(error.getCommitedDateOfCheque())){

            return false;
        }

        return true;
    }

    private boolean checkBankOfCheque(String value){

        error.setBankOfCheque(checkString(value , 5 , 50 , "bank Name" , true));

        if (!"".equals(error.getBankOfCheque())){
            return false;
        }

        return true;
    }

    private boolean checkBankAccountNo(String value){

        error.setBankAccountNumber(checkString(value , 10 , 30 , "bank account number" , true));

        if (!"".equals(error.getBankAccountNumber())){
            return false;
        }

        return true;
    }

    private boolean checkInvoice(long invoiceId , long storeId){

        if (invoiceInfoRepository.findByIdAndStatusAndStoreInfo(invoiceId , Status.ACTIVE , storeId) == null){
            error.setInvoice("invoice not found");
            return false;
        }

        return true;
    }

}
