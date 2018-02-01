package com.inventory.core.model.dto;

import java.util.Date;

/**
 * Created by dhiraj on 1/31/18.
 */
public class InvoiceListDTO {

    private String invoiceNo;

    private String fiscalYearInfo;

    private String clientInfo;

    private double totalAmount;

    private double receivableAmount;

    private String invoiceDate;

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getFiscalYearInfo() {
        return fiscalYearInfo;
    }

    public void setFiscalYearInfo(String fiscalYearInfo) {
        this.fiscalYearInfo = fiscalYearInfo;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getReceivableAmount() {
        return receivableAmount;
    }

    public void setReceivableAmount(double receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}
