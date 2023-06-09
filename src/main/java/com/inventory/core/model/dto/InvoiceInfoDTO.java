package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;

import java.util.Date;

/**
 * Created by dhiraj on 8/6/17.
 */
public class InvoiceInfoDTO {

    private Long invoiceId;

    private long storeInfoId;

    private String invoiceNo;

    private OrderInfoDTO orderInfo;

    private Long orderInfoId;

    private Date invoiceDate;

    private Status status;

    private double totalAmount;

    private double receivableAmount;

    private String description;

    private long createdById;

    private String createdByName;

    private Integer version;

    private StoreInfoDTO storeInfoDTO;

    private FiscalYearInfoDTO fiscalYearInfo;

    private boolean canceled;

    private String cancelNote;

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public String getCancelNote() {
        return cancelNote;
    }

    public void setCancelNote(String cancelNote) {
        this.cancelNote = cancelNote;
    }

    public StoreInfoDTO getStoreInfoDTO() {
        return storeInfoDTO;
    }

    public void setStoreInfoDTO(StoreInfoDTO storeInfoDTO) {
        this.storeInfoDTO = storeInfoDTO;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public long getStoreInfoId() {
        return storeInfoId;
    }

    public void setStoreInfoId(long storeInfoId) {
        this.storeInfoId = storeInfoId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public OrderInfoDTO getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoDTO orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Long getOrderInfoId() {
        return orderInfoId;
    }

    public void setOrderInfoId(Long orderInfoId) {
        this.orderInfoId = orderInfoId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public FiscalYearInfoDTO getFiscalYearInfo() {
        return fiscalYearInfo;
    }

    public void setFiscalYearInfo(FiscalYearInfoDTO fiscalYearInfo) {
        this.fiscalYearInfo = fiscalYearInfo;
    }
}
