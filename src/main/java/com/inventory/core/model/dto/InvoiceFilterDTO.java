package com.inventory.core.model.dto;

import java.util.Date;

/**
 * Created by dhiraj on 1/28/18.
 */
public class InvoiceFilterDTO {

    private Long fiscalYearId;

    private Long clientId;

    private Date from;

    private Date to;

    private double amountGt;

    private double amountLt;

    private double receivableGt;

    private double receivableLt;

    public Long getFiscalYearId() {
        return fiscalYearId;
    }

    public void setFiscalYearId(Long fiscalYearId) {
        this.fiscalYearId = fiscalYearId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public double getAmountGt() {
        return amountGt;
    }

    public void setAmountGt(double amountGt) {
        this.amountGt = amountGt;
    }

    public double getAmountLt() {
        return amountLt;
    }

    public void setAmountLt(double amountLt) {
        this.amountLt = amountLt;
    }

    public double getReceivableGt() {
        return receivableGt;
    }

    public void setReceivableGt(double receivableGt) {
        this.receivableGt = receivableGt;
    }

    public double getReceivableLt() {
        return receivableLt;
    }

    public void setReceivableLt(double receivableLt) {
        this.receivableLt = receivableLt;
    }
}
