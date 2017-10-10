package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.PaymentMethod;
import com.inventory.core.model.enumconstant.Status;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by dhiraj on 7/19/17.
 */
@Entity
@Table(name = "payment_table")
public class Payment extends AbstractEntity<Long> {

    private double amount;

    private PaymentMethod paymentMethod;

    private String remark;

    private Date chequeDate;

    private Date commitedDateOfCheque;

    private String bankOfCheque;

    private String bankAccountNumber;

    private Status status;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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
