package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;

import java.util.Date;
import java.util.List;

/**
 * Created by dhiraj on 1/17/18.
 */
public class OrderReturnInfoDTO {

    private long orderReturnInfoId;

    private Date returnDate;

    private OrderInfoDTO orderInfo;

    private Long orderInfoId;

    private String note;

    private double totalAmount;

    private long storeId;

    private long createdById;

    private String createdByName;

    private Status status;

    private List<Long> orderItemInfoIdList;

    private List<Integer> returnQuantityList;

    public long getOrderReturnInfoId() {
        return orderReturnInfoId;
    }

    public void setOrderReturnInfoId(long orderReturnInfoId) {
        this.orderReturnInfoId = orderReturnInfoId;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
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

    public List<Long> getOrderItemInfoIdList() {
        return orderItemInfoIdList;
    }

    public void setOrderItemInfoIdList(List<Long> orderItemInfoIdList) {
        this.orderItemInfoIdList = orderItemInfoIdList;
    }

    public List<Integer> getReturnQuantityList() {
        return returnQuantityList;
    }

    public void setReturnQuantityList(List<Integer> returnQuantityList) {
        this.returnQuantityList = returnQuantityList;
    }
}
