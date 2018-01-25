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

    @ManyToOne(fetch = FetchType.LAZY)
    private ServiceInfo serviceInfo;

    private boolean selected;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expireOn;

    private Status status;
}
