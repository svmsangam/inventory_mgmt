package com.inventory.core.model.liteentity;

import com.inventory.core.model.enumconstant.Status;

/**
 * Created by dhiraj on 2/8/18.
 */
public class ReturnItemInfoDomain {

    private long returnItemInfoId;

    private int quantity;

    private double rate;

    private double discount;

    private double totalAmount;

    private long orderItemInfoId;

    private long itemInfoId;

    private String itemName;

    private Status status;

    public ReturnItemInfoDomain(long returnItemInfoId , int quantity , double rate , double totalAmount , long orderItemInfoId , long itemInfoId , String itemName , Status status , double discount){

        this.orderItemInfoId = returnItemInfoId;
        this.quantity = quantity;
        this.rate = rate;
        this.totalAmount = totalAmount;
        this.orderItemInfoId = orderItemInfoId;
        this.itemInfoId = itemInfoId;
        this.itemName = itemName;
        this.status = status;
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public long getReturnItemInfoId() {
        return returnItemInfoId;
    }

    public void setReturnItemInfoId(long returnItemInfoId) {
        this.returnItemInfoId = returnItemInfoId;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
