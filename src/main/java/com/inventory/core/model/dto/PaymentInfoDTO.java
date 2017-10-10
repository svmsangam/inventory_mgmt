package com.inventory.core.model.dto;

import java.util.Date;

/**
 * Created by dhiraj on 10/10/17.
 */
public class PaymentInfoDTO {

    private long paymentInfoId;

    private long paymentId;

    private PaymentDTO receivedPayment;

    private String remark;

    private long invoiceInfoId;

    private long storeInfoId;

    private long createdById;

    private String createdByName;

    private Date paymentDate;

    public long getPaymentInfoId() {
        return paymentInfoId;
    }

    public void setPaymentInfoId(long paymentInfoId) {
        this.paymentInfoId = paymentInfoId;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentDTO getReceivedPayment() {
        return receivedPayment;
    }

    public void setReceivedPayment(PaymentDTO receivedPayment) {
        this.receivedPayment = receivedPayment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getInvoiceInfoId() {
        return invoiceInfoId;
    }

    public void setInvoiceInfoId(long invoiceInfoId) {
        this.invoiceInfoId = invoiceInfoId;
    }

    public long getStoreInfoId() {
        return storeInfoId;
    }

    public void setStoreInfoId(long storeInfoId) {
        this.storeInfoId = storeInfoId;
    }

    public long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
