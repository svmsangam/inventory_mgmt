package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.LogType;
import com.inventory.core.model.enumconstant.Status;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by dhiraj on 12/17/17.
 */
@Entity
@Table(name = "logger")
public class Logger extends AbstractEntity<Long>{

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private String log;

    private long associateId;

    private LogType type;

    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private StoreInfo storeInfo;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public long getAssociateId() {
        return associateId;
    }

    public void setAssociateId(long associateId) {
        this.associateId = associateId;
    }

    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }
}
