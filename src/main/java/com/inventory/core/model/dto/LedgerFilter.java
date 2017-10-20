package com.inventory.core.model.dto;

import java.util.Date;

/**
 * Created by dhiraj on 10/20/17.
 */
public class LedgerFilter {

    private long clientId ;

    private Date from;

    private Date to;

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
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
}
