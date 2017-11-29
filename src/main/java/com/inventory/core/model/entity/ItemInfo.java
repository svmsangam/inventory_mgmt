package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "item_table")
public class ItemInfo extends AbstractEntity<Long> {

    @ManyToOne(fetch = FetchType.EAGER)
    private ProductInfo productInfo;

    private double costPrice;

    private double sellingPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    private TagInfo tagInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    private LotInfo lotInfo;

    @Temporal(TemporalType.DATE)
    private Date expireDate;

    private int inStock;

    private long quantity;

    private int threshold;

    private Status status;

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
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

    public TagInfo getTagInfo() {
        return tagInfo;
    }

    public void setTagInfo(TagInfo tagInfo) {
        this.tagInfo = tagInfo;
    }

    public LotInfo getLotInfo() {
        return lotInfo;
    }

    public void setLotInfo(LotInfo lotInfo) {
        this.lotInfo = lotInfo;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
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
