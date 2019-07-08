package com.inventory.core.model.liteentity;

public class ItemDomain {

    private long itemId;

    private long productId;

    private String itemName;

    private String productName;

    private String productCode;

    private double sellingPrice;

    private int instock;

    public ItemDomain(long itemId , long productId , String itemName , String productName , String productCode , double sellingPrice , int instock){
        this.itemId = itemId;
        this.productId = productId;
        this.itemName = itemName;
        this.productName = productName;
        this.productCode = productCode;
        this.sellingPrice = sellingPrice;
        this.instock = instock;
    }

    public int getInstock() {
        return instock;
    }

    public void setInstock(int instock) {
        this.instock = instock;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
