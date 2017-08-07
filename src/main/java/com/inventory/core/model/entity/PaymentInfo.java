package com.inventory.core.model.entity;

import javax.persistence.*;

/**
 * Created by dhiraj on 7/18/17.
 */
@Entity
@Table(name = "paymentdetail_tables")
public class PaymentInfo extends AbstractEntity<Long>{

    private static final long serialVersionUID = -6245833303340171164L;

    @OneToOne
    private Payment receivedPayment;

    @OneToOne
    private Payment returnedPayment;

    private String remark;

    @ManyToOne(fetch = FetchType.EAGER)
    private InvoiceInfo invoiceInfo;

    @ManyToOne
    private StoreInfo storeInfo;

    @ManyToOne
    private User createdBy;

    public Payment getReceivedPayment() {
        return receivedPayment;
    }

    public void setReceivedPayment(Payment receivedPayment) {
        this.receivedPayment = receivedPayment;
    }

    public Payment getReturnedPayment() {
        return returnedPayment;
    }

    public void setReturnedPayment(Payment returnedPayment) {
        this.returnedPayment = returnedPayment;
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
