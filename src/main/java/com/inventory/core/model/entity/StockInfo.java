package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.*;

@Entity
@Table(name = "stock_table")
public class StockInfo extends AbstractEntity<Long> {

    private int inStock;

    private long quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    private ProductInfo productInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    private StoreInfo storeInfo;

    private Status status;

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

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
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
