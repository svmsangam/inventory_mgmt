package com.inventory.core.validation;

import com.inventory.core.model.dto.PaymentInfoDTO;
import com.inventory.core.model.entity.InvoiceInfo;
import com.inventory.core.model.entity.OrderInfo;
import com.inventory.core.model.enumconstant.PaymentMethod;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.repository.InvoiceInfoRepository;
import com.inventory.core.model.repository.OrderInfoRepository;
import com.inventory.core.util.ConvertUtil;
import com.inventory.web.error.PaymentInfoError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * Created by dhiraj on 10/10/17.
 */
@Service
public class PaymentInfoValidation extends GlobalValidation{



    @Autowired
    private InvoiceInfoRepository invoiceInfoRepository;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    PaymentInfoError error;

    public synchronized PaymentInfoError onSave(PaymentInfoDTO paymentInfoDTO , long storeId , long invoiceId, BindingResult result) {

        error = new PaymentInfoError();

        boolean valid = true;

        if (result.hasErrors()) {

            valid = true;

            List<FieldError> errors = result.getFieldErrors();

            for (FieldError errorResult : errors) {

                if (errorResult.getField().equals("receivedPayment.amount")) {
                    error.setAmount("invalid amount");
                    valid = false;
                } else if (errorResult.getField().equals("paymentMethod")) {
                    error.setPaymentMethod("receivedPayment.invalid paymentMethod");
                    valid = false;
                } else if (errorResult.getField().equals("remark")) {
                    error.setRemark("invalid remark");
                    valid = false;
                } else if (errorResult.getField().equals("receivedPayment.chequeDate")) {
                   if (paymentInfoDTO.getReceivedPayment() != null){
                       if (PaymentMethod.CHEQUE.equals(paymentInfoDTO.getReceivedPayment().getPaymentMethod())){
                           error.setChequeDate("invalid chequeDate");
                           valid = false;
                       }
                   }

                } else if (errorResult.getField().equals("receivedPayment.commitedDateOfCheque")) {

                    if (paymentInfoDTO.getReceivedPayment() != null){
                        if (PaymentMethod.CHEQUE.equals(paymentInfoDTO.getReceivedPayment().getPaymentMethod())){
                            error.setCommitedDateOfCheque("invalid commitedDateOfCheque");
                            valid = false;
                        }
                    }

                } else if (errorResult.getField().equals("receivedPayment.bankOfCheque")) {
                    error.setBankOfCheque("invalid bank");
                    valid = false;
                } else if (errorResult.getField().equals("receivedPayment.bankAccountNumber")) {
                    error.setBankAccountNumber("invalid bankAccountNumber");
                    valid = false;
                } else if (errorResult.getField().equals("invoiceInfoId")) {
                    error.setInvoice("invalid invoiceInfo");
                    valid = false;
                } else if (errorResult.getField().equals("invoiceVersion")) {
                    error.setInvoice("invalid invoice version");
                    valid = false;
                }
            }

            if (!valid) {
                error.setValid(valid);

                return error;
            }
        }

        valid = valid && checkInvoice(invoiceId , storeId , paymentInfoDTO.getInvoiceVersion());

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


        error.setValid(valid);

        return error;
    }

    public synchronized PaymentInfoError onQuickSave(PaymentInfoDTO paymentInfoDTO , BindingResult result) {

        error = new PaymentInfoError();

        boolean valid = true;

        if (result.hasErrors()) {

            valid = true;

            List<FieldError> errors = result.getFieldErrors();

            for (FieldError errorResult : errors) {

                if (errorResult.getField().equals("receivedPayment.amount")) {
                    error.setAmount("invalid amount");
                    valid = false;
                } else if (errorResult.getField().equals("paymentMethod")) {
                    error.setPaymentMethod("receivedPayment.invalid paymentMethod");
                    valid = false;
                } else if (errorResult.getField().equals("remark")) {
                    error.setRemark("invalid remark");
                    valid = false;
                } else if (errorResult.getField().equals("receivedPayment.chequeDate")) {
                    if (paymentInfoDTO.getReceivedPayment() != null){
                        if (PaymentMethod.CHEQUE.equals(paymentInfoDTO.getReceivedPayment().getPaymentMethod())){
                            error.setChequeDate("invalid chequeDate");
                            valid = false;
                        }
                    }

                } else if (errorResult.getField().equals("receivedPayment.commitedDateOfCheque")) {

                    if (paymentInfoDTO.getReceivedPayment() != null){
                        if (PaymentMethod.CHEQUE.equals(paymentInfoDTO.getReceivedPayment().getPaymentMethod())){
                            error.setCommitedDateOfCheque("invalid commitedDateOfCheque");
                            valid = false;
                        }
                    }

                } else if (errorResult.getField().equals("receivedPayment.bankOfCheque")) {
                    error.setBankOfCheque("invalid bank");
                    valid = false;
                } else if (errorResult.getField().equals("receivedPayment.bankAccountNumber")) {
                    error.setBankAccountNumber("invalid bankAccountNumber");
                    valid = false;
                } else if (errorResult.getField().equals("orderInfoId")) {
                    error.setInvoice("invalid orderInfo");
                    valid = false;
                }
            }

            if (!valid) {
                error.setValid(valid);

                return error;
            }
        }

        OrderInfo orderInfo = orderInfoRepository.findById(paymentInfoDTO.getOrderInfoId()).orElse(null);

        valid = valid && checkAmount(paymentInfoDTO.getReceivedPayment().getAmount(), orderInfo.getGrandTotal());

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

        error.setValid(valid);

        return error;
    }

    private boolean checkAmount(Double value , long invoiceId ) {

        error.setAmount(checkDouble(value, 0, 3, "amount", true));

        if (!"".equals(error.getAmount())) {
            return false;
        }

        InvoiceInfo invoiceInfo = invoiceInfoRepository.findById(invoiceId);

        return invoiceInfo == null || checkAmount(value, invoiceInfo.getReceivableAmount());

    }

    private boolean checkAmount(Double value , double checker){

        error.setAmount(checkDouble(value , 0, 3 , "amount" , true));

        if (!"".equals(error.getAmount())){
            return false;
        }else if (value > checker){
                error.setAmount("amount must be less than or equals with " + ConvertUtil.roundUpDoubleToString(checker , 0 ));
                return false;
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

    private boolean checkInvoice(long invoiceId , long storeId , long invoiceVersion){

        InvoiceInfo invoiceInfo = invoiceInfoRepository.findByIdAndStatusAndStoreInfo(invoiceId , Status.ACTIVE , storeId);

        if (invoiceInfo == null){
            error.setInvoice("invoice not found");
            return false;
        }else if (invoiceInfo.getVersion() != invoiceVersion){
            error.setInvoice("payment of this invoice is already updated");
            return false;
        } else if (invoiceInfo.isCanceled()){
            error.setInvoice("invoice already canceled");
            return false;
        }else if (invoiceInfo.getReceivableAmount() == 0){
            error.setInvoice("no receivable for payment");
            return false;
        }

        return true;
    }

}
