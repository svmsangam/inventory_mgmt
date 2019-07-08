package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by dhiraj on 1/17/18.
 */
@Entity
@Table(name = "return_item_info")
public class ReturnItemInfo extends AbstractEntity<Long>{

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderItemInfo orderItemInfo;

    private int quantity;

    private double rate;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderReturnInfo orderReturnInfo;

    private double totalAmount;

    private Status status;

    public OrderItemInfo getOrderItemInfo() {
        return orderItemInfo;
    }

    public void setOrderItemInfo(OrderItemInfo orderItemInfo) {
        this.orderItemInfo = orderItemInfo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderReturnInfo getOrderReturnInfo() {
        return orderReturnInfo;
    }

    public void setOrderReturnInfo(OrderReturnInfo orderReturnInfo) {
        this.orderReturnInfo = orderReturnInfo;
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
