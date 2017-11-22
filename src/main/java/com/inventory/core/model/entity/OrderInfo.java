package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dhiraj on 6/29/17.
 */
@Entity
@Table(name = "invorderinfo")
public class OrderInfo extends AbstractEntity<Long> {
    
    private String orderNo;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Temporal(TemporalType.DATE)
    private Date deliveryDate;

    private SalesOrderStatus saleTrack;

    private PurchaseOrderStatus purchaseTrack;

    private String description;

    private String deliveryAddress;

    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    private long buyerId;

    private BuyerType buyerType;

    @ManyToOne(fetch = FetchType.EAGER)
    private StoreInfo sellerInfo;

    private double totalAmount;

    private double tax;

    private double grandTotal;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public SalesOrderStatus getSaleTrack() {
        return saleTrack;
    }

    public void setSaleTrack(SalesOrderStatus saleTrack) {
        this.saleTrack = saleTrack;
    }

    public PurchaseOrderStatus getPurchaseTrack() {
        return purchaseTrack;
    }

    public void setPurchaseTrack(PurchaseOrderStatus purchaseTrack) {
        this.purchaseTrack = purchaseTrack;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public BuyerType getBuyerType() {
        return buyerType;
    }

    public void setBuyerType(BuyerType buyerType) {
        this.buyerType = buyerType;
    }

    public StoreInfo getSellerInfo() {
        return sellerInfo;
    }

    public void setSellerInfo(StoreInfo sellerInfo) {
        this.sellerInfo = sellerInfo;
    }
}
