package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by dhiraj on 11/22/17.
 */
@Entity
@Table(name = "store_item")
public class StoreItemInfo extends AbstractEntity<Long>{

    @ManyToOne(fetch = FetchType.EAGER)
    private ItemInfo itemInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    private StateInfo stateInfo;

    private double costPrice;

    private double sellingPrice;

    private int inStock;

    private long quantity;

    private int threshold;

    private Status status;

    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(ItemInfo itemInfo) {
        this.itemInfo = itemInfo;
    }

    public StateInfo getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(StateInfo stateInfo) {
        this.stateInfo = stateInfo;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
