package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;

import java.util.Date;

/**
 * Created by dhiraj on 1/28/18.
 */
public class InvoiceFilterDTO {

    private Long fiscalYearId;

    private Long clientId;

    private Date from;

    private Date to;

    private Double amountGt;

    private Double amountLt;

    private Double receivableGt;

    private Double receivableLt;

    private Integer pageNo;

    private Status status;

    private Long storeInfoId;

    public Long getFiscalYearId() {
        return fiscalYearId;
    }

    public void setFiscalYearId(Long fiscalYearId) {
        this.fiscalYearId = fiscalYearId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Double getAmountGt() {
        return amountGt;
    }

    public void setAmountGt(Double amountGt) {
        this.amountGt = amountGt;
    }

    public Double getAmountLt() {
        return amountLt;
    }

    public void setAmountLt(Double amountLt) {
        this.amountLt = amountLt;
    }

    public Double getReceivableGt() {
        return receivableGt;
    }

    public void setReceivableGt(Double receivableGt) {
        this.receivableGt = receivableGt;
    }

    public Double getReceivableLt() {
        return receivableLt;
    }

    public void setReceivableLt(Double receivableLt) {
        this.receivableLt = receivableLt;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getStoreInfoId() {
        return storeInfoId;
    }

    public void setStoreInfoId(Long storeInfoId) {
        this.storeInfoId = storeInfoId;
    }
}
