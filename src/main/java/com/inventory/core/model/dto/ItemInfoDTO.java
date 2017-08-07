package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;

import java.util.Date;

/**
 * Created by dhiraj on 8/6/17.
 */
public class ItemInfoDTO {

    private Long itemId;

    private ProductInfoDTO productInfo;

    private Long productId;

    private double costPrice;

    private double sellingPrice;

    private TagInfoDTO tagInfo;

    private Long tagId;

    private LotInfoDTO lotInfo;

    private Long lotId;

    private Date expireDate;

    private int inStock;

    private int quantity;

    private int threshold;

    private Status status;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public ProductInfoDTO getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfoDTO productInfo) {
        this.productInfo = productInfo;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public TagInfoDTO getTagInfo() {
        return tagInfo;
    }

    public void setTagInfo(TagInfoDTO tagInfo) {
        this.tagInfo = tagInfo;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public LotInfoDTO getLotInfo() {
        return lotInfo;
    }

    public void setLotInfo(LotInfoDTO lotInfo) {
        this.lotInfo = lotInfo;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
