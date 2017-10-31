package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by dhiraj on 6/29/17.
 */
@Entity
@Table(name = "order_item_table")
public class OrderItemInfo extends AbstractEntity<Long> {

    @ManyToOne(fetch = FetchType.EAGER)
    private ItemInfo itemInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    private OrderInfo orderInfo;

    private int quantity;
    
    private int returnQuantity;

    private double rate;

    private double discount;

    private double amount;

    private Status status;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(ItemInfo itemInfo) {
        this.itemInfo = itemInfo;
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

    public int getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(int returnQuantity) {
        this.returnQuantity = returnQuantity;
    }
}
