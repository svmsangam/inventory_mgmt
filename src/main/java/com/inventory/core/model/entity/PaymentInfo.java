package com.inventory.core.model.entity;

import javax.persistence.*;

/**
 * Created by dhiraj on 7/18/17.
 */
@Entity
@Table(name = "paymentdetail_tables")
public class PaymentInfo extends AbstractEntity<Long> {

    @OneToOne(fetch = FetchType.EAGER)
    private Payment receivedPayment;

    @OneToOne(fetch = FetchType.EAGER)
    private Payment refundPayment;

    private String remark;

    @ManyToOne(fetch = FetchType.EAGER)
    private InvoiceInfo invoiceInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    private StoreInfo storeInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    private User createdBy;

    public Payment getRefundPayment() {
        return refundPayment;
    }

    public void setRefundPayment(Payment refundPayment) {
        this.refundPayment = refundPayment;
    }

    public Payment getReceivedPayment() {
        return receivedPayment;
    }

    public void setReceivedPayment(Payment receivedPayment) {
        this.receivedPayment = receivedPayment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public InvoiceInfo getInvoiceInfo() {
        return invoiceInfo;
    }

    public void setInvoiceInfo(InvoiceInfo invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
