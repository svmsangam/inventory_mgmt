package com.inventory.core.model.liteentity;

import com.inventory.core.model.enumconstant.Status;

import java.util.Date;
import java.util.List;

/**
 * Created by dhiraj on 2/8/18.
 */
public class OrderReturnInfoDomain {

    private long orderReturnId;

    private Status status;

    private Date returnDate;

    private double netTotal;

    private double totalAmount;

    private long orderInfoId;

    private String orderInfoOrderNo;

    private String clientInfoName;

    private String clientInfoCompanyName;

    private double tax;

    private String description;

    private List<ReturnItemInfoDomain> returnItemInfoList;

    public OrderReturnInfoDomain(long orderReturnId , Status status , Date returnDate , double totalAmount , long orderInfoId , String orderInfoOrderNo , String clientInfoName , String clientInfoCompanyName){

        this.orderReturnId = orderReturnId;
        this.status = status;
        this.returnDate = returnDate;
        this.totalAmount = totalAmount;
        this.orderInfoId = orderInfoId;
        this.orderInfoOrderNo = orderInfoOrderNo;
        this.clientInfoName = clientInfoName;
        this.clientInfoCompanyName = clientInfoCompanyName;
    }

    public OrderReturnInfoDomain(long orderReturnId , Status status , Date returnDate , double totalAmount , long orderInfoId , String orderInfoOrderNo , String clientInfoName , String clientInfoCompanyName , double tax ,  String description){

        this.orderReturnId = orderReturnId;
        this.status = status;
        this.returnDate = returnDate;
        this.totalAmount = totalAmount;
        this.orderInfoId = orderInfoId;
        this.orderInfoOrderNo = orderInfoOrderNo;
        this.clientInfoName = clientInfoName;
        this.clientInfoCompanyName = clientInfoCompanyName;
        this.tax = tax;
        this.netTotal = totalAmount - totalAmount * tax / 100;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(double netTotal) {
        this.netTotal = netTotal;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public List<ReturnItemInfoDomain> getReturnItemInfoList() {
        return returnItemInfoList;
    }

    public void setReturnItemInfoList(List<ReturnItemInfoDomain> returnItemInfoList) {
        this.returnItemInfoList = returnItemInfoList;
    }

    public long getOrderReturnId() {
        return orderReturnId;
    }

    public void setOrderReturnId(long orderReturnId) {
        this.orderReturnId = orderReturnId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getOrderInfoId() {
        return orderInfoId;
    }

    public void setOrderInfoId(long orderInfoId) {
        this.orderInfoId = orderInfoId;
    }

    public String getOrderInfoOrderNo() {
        return orderInfoOrderNo;
    }

    public void setOrderInfoOrderNo(String orderInfoOrderNo) {
        this.orderInfoOrderNo = orderInfoOrderNo;
    }

    public String getClientInfoName() {
        return clientInfoName;
    }

    public void setClientInfoName(String clientInfoName) {
        this.clientInfoName = clientInfoName;
    }

    public String getClientInfoCompanyName() {
        return clientInfoCompanyName;
    }

    public void setClientInfoCompanyName(String clientInfoCompanyName) {
        this.clientInfoCompanyName = clientInfoCompanyName;
    }
}
