package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "invoice_table")
public class InvoiceInfo extends AbstractEntity<Long> {

    @ManyToOne(fetch = FetchType.EAGER)
    private StoreInfo storeInfo;

    private String invoiceNo;

    @OneToOne(fetch = FetchType.EAGER)
    private OrderInfo orderInfo;

    @Temporal(TemporalType.DATE)
    private Date invoiceDate;

    private Status status;

    private double totalAmount;

    private double receivableAmount;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private User createdBy;

    @ManyToOne(fetch = FetchType.EAGER)
    private FiscalYearInfo fiscalYearInfo;

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getReceivableAmount() {
        return receivableAmount;
    }

    public void setReceivableAmount(double receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public FiscalYearInfo getFiscalYearInfo() {
        return fiscalYearInfo;
    }

    public void setFiscalYearInfo(FiscalYearInfo fiscalYearInfo) {
        this.fiscalYearInfo = fiscalYearInfo;
    }
}
