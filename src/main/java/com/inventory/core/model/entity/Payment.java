package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.PaymentMethod;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by dhiraj on 7/19/17.
 */
@Entity
@Table(name = "payment_table")
public class Payment extends AbstractEntity<Long> {

    private static final long serialVersionUID = -6245833303340171164L;

    private Double amount;

    private PaymentMethod paymentMethod;

    private String remark;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
