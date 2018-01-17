package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dhiraj on 1/17/18.
 */
@Entity
@Table(name = "order_return_info")
public class OrderReturnInfo extends AbstractEntity<Long>{

    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderInfo orderInfo;

    private String note;

    private double totalAmount;

    private Status status;

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
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
}
