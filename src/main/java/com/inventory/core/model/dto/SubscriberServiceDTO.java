package com.inventory.core.model.dto;

import com.inventory.core.model.entity.ServiceInfo;
import com.inventory.core.model.enumconstant.Status;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by dhiraj on 1/26/18.
 */
public class SubscriberServiceDTO {

    private long subscriberServiceId;

    private ServiceDTO serviceInfo;

    private long serviceInfoId;

    private boolean selected;

    private boolean expired;

    private Date expireOn;

    private Status status;

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public long getSubscriberServiceId() {
        return subscriberServiceId;
    }

    public void setSubscriberServiceId(long subscriberServiceId) {
        this.subscriberServiceId = subscriberServiceId;
    }

    public ServiceDTO getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(ServiceDTO serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public long getServiceInfoId() {
        return serviceInfoId;
    }

    public void setServiceInfoId(long serviceInfoId) {
        this.serviceInfoId = serviceInfoId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Date getExpireOn() {
        return expireOn;
    }

    public void setExpireOn(Date expireOn) {
        this.expireOn = expireOn;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
