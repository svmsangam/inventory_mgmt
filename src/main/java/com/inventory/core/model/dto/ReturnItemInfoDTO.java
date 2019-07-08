package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;

/**
 * Created by dhiraj on 1/17/18.
 */
public class ReturnItemInfoDTO {

    private long returnItemInfoId;

    private OrderItemInfoDTO orderItemInfo;

    private Long orderItemInfoId;

    private int quantity;

    private double rate;

    private Long orderReturnInfoId;

    private double totalAmount;

    private Status status;

    public long getReturnItemInfoId() {
        return returnItemInfoId;
    }

    public void setReturnItemInfoId(long returnItemInfoId) {
        this.returnItemInfoId = returnItemInfoId;
    }

    public OrderItemInfoDTO getOrderItemInfo() {
        return orderItemInfo;
    }

    public void setOrderItemInfo(OrderItemInfoDTO orderItemInfo) {
        this.orderItemInfo = orderItemInfo;
    }

    public Long getOrderItemInfoId() {
        return orderItemInfoId;
    }

    public void setOrderItemInfoId(Long orderItemInfoId) {
        this.orderItemInfoId = orderItemInfoId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getOrderReturnInfoId() {
        return orderReturnInfoId;
    }

    public void setOrderReturnInfoId(Long orderReturnInfoId) {
        this.orderReturnInfoId = orderReturnInfoId;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
