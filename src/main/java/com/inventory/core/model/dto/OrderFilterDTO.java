package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.SalesOrderStatus;
import com.inventory.core.model.enumconstant.Status;

import java.util.Date;

public class OrderFilterDTO {

    private Long clientId;

    private SalesOrderStatus track;

    private Date from;

    private Date to;

    private Double amountGt;

    private Double amountLt;

    private Date dlfrom;

    private Date dlto;

    private long storeId;

    private Integer pageNo;

    private int size;

    private Status status;

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public SalesOrderStatus getTrack() {
        return track;
    }

    public void setTrack(SalesOrderStatus track) {
        this.track = track;
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

    public Date getDlfrom() {
        return dlfrom;
    }

    public void setDlfrom(Date dlfrom) {
        this.dlfrom = dlfrom;
    }

    public Date getDlto() {
        return dlto;
    }

    public void setDlto(Date dlto) {
        this.dlto = dlto;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
