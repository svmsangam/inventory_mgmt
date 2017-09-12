package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.OrderType;
import com.inventory.core.model.enumconstant.SalesOrderStatus;
import com.inventory.core.model.enumconstant.PurchaseOrderStatus;
import com.inventory.core.model.enumconstant.Status;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by dhiraj on 6/29/17.
 */
@Entity
@Table(name = "invorderinfo")
public class OrderInfo extends AbstractEntity<Long> {
    
    private String orderNo;

    private Date orderDate;

    private Date deliveryDate;

    private OrderType orderType;

    private SalesOrderStatus saleTrack;

    private PurchaseOrderStatus purchaseTrack;

    private String description;

    private String deliveryAddress;

    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    private User createdBy;

    @ManyToOne(fetch = FetchType.EAGER)
    private ClientInfo clientInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    private StoreInfo storeInfo;

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

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
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

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
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
}
