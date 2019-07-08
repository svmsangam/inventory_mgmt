package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dhiraj on 1/25/18.
 */
@Entity
@Table(name = "subscriber_service_table")
public class SubscriberService extends AbstractEntity<Long>{

    @ManyToOne(fetch = FetchType.LAZY)
    private Subscriber subscriber;

    @ManyToOne(fetch = FetchType.EAGER)
    private ServiceInfo serviceInfo;

    private boolean selected;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expireOn;

    private Status status;

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public ServiceInfo getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(ServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
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
