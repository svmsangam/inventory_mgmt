package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.OrderType;
import com.inventory.core.model.enumconstant.PurchaseOrderStatus;
import com.inventory.core.model.enumconstant.SalesOrderStatus;
import com.inventory.core.model.enumconstant.Status;

import java.util.Date;

/**
 * Created by dhiraj on 8/6/17.
 */
public class OrderInfoDTO {

    private Long orderId;

    private String orderNo;

    private Date orderDate;

    private Date deliveryDate;

    private OrderType orderType;

    private SalesOrderStatus saleTrack;

    private PurchaseOrderStatus purchaseTrack;

    private String description;

    private String deliveryAddress;

    private Status status;

    private long createdById;

    private String createdByName;

    private ClientInfoDTO clientInfo;

    private Long clientId;

    private long storeInfoId;

    private double totalAmount;

    private double tax;

    private double grandTotal;

    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

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

    public ClientInfoDTO getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfoDTO clientInfo) {
        this.clientInfo = clientInfo;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public long getStoreInfoId() {
        return storeInfoId;
    }

    public void setStoreInfoId(long storeInfoId) {
        this.storeInfoId = storeInfoId;
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
