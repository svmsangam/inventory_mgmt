package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;

/**
 * Created by dhiraj on 8/6/17.
 */
public class StockInfoDTO {

    private Long stockId;

    private int inStock;

    private long quantity;

    private ProductInfoDTO productInfo;

    private Long productId;

    private Status status;

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
