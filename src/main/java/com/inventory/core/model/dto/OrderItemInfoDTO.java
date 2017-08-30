package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;

/**
 * Created by dhiraj on 8/30/17.
 */
public class OrderItemInfoDTO {

    private long orderItemInfoId;

    private long itemInfoId;

    private ItemInfoDTO itemInfoDTO;

    private long orderInfoId;

    private int quantity;

    private double rate;

    private double discount;

    private double amount;

    private Status status;

    public long getOrderItemInfoId() {
        return orderItemInfoId;
    }

    public void setOrderItemInfoId(long orderItemInfoId) {
        this.orderItemInfoId = orderItemInfoId;
    }

    public long getItemInfoId() {
        return itemInfoId;
    }

    public void setItemInfoId(long itemInfoId) {
        this.itemInfoId = itemInfoId;
    }

    public long getOrderInfoId() {
        return orderInfoId;
    }

    public void setOrderInfoId(long orderInfoId) {
        this.orderInfoId = orderInfoId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ItemInfoDTO getItemInfoDTO() {
        return itemInfoDTO;
    }

    public void setItemInfoDTO(ItemInfoDTO itemInfoDTO) {
        this.itemInfoDTO = itemInfoDTO;
    }
}
