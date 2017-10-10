package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.PaymentMethod;
import com.inventory.core.model.enumconstant.Status;

import java.util.Date;

/**
 * Created by dhiraj on 10/10/17.
 */
public class PaymentDTO {

    private long paymentId;

    private Double amount;

    private PaymentMethod paymentMethod;

    private String remark;

    private Date chequeDate;

    private Date commitedDateOfCheque;

    private String bankOfCheque;

    private String bankAccountNumber;

    private Status status;

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(Date chequeDate) {
        this.chequeDate = chequeDate;
    }

    public Date getCommitedDateOfCheque() {
        return commitedDateOfCheque;
    }

    public void setCommitedDateOfCheque(Date commitedDateOfCheque) {
        this.commitedDateOfCheque = commitedDateOfCheque;
    }

    public String getBankOfCheque() {
        return bankOfCheque;
    }

    public void setBankOfCheque(String bankOfCheque) {
        this.bankOfCheque = bankOfCheque;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
