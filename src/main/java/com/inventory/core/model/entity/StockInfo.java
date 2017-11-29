package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.*;

@Entity
@Table(name = "stock_table")
public class StockInfo extends AbstractEntity<Long> {

    private static final long serialVersionUID = -4641958444066651153L;

    private int inStock;

    private long quantity;

    @OneToOne(fetch = FetchType.EAGER)
    private ProductInfo productInfo;

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
}
