package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by dhiraj on 8/10/17.
 */

@Entity
@Table(name = "store_user_table")
public class StoreUserInfo extends AbstractEntity<Long> {

    @ManyToOne(fetch = FetchType.EAGER)
    private StoreInfo storeInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private Status status;

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
