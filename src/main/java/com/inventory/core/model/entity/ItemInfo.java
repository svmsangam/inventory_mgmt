package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "item_table")
public class ItemInfo extends AbstractEntity<Long> {

    @ManyToOne(fetch = FetchType.EAGER)
    private ProductInfo productInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    private TagInfo tagInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    private LotInfo lotInfo;

    @Temporal(TemporalType.DATE)
    private Date expireDate;

    private Status status;

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
