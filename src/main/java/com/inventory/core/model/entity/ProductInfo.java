package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.TrendingLevel;
import com.inventory.core.model.enumconstant.Status;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by dhiraj on 6/25/17.
 */
@Entity
@Table(name = "product_table")
public class ProductInfo extends AbstractEntity<Long> {

    private static final long serialVersionUID = -6245833303340171164L;

    private String name;

    private String description;

    private TrendingLevel trendingLevel;

    @ManyToOne(fetch = FetchType.EAGER)
    private SubCategoryInfo subCategoryInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    private StoreInfo storeInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    private UnitInfo unitInfo;

    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    private User createdBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TrendingLevel getTrendingLevel() {
        return trendingLevel;
    }

    public void setTrendingLevel(TrendingLevel trendingLevel) {
        this.trendingLevel = trendingLevel;
    }

    public SubCategoryInfo getSubCategoryInfo() {
        return subCategoryInfo;
    }

    public void setSubCategoryInfo(SubCategoryInfo subCategoryInfo) {
        this.subCategoryInfo = subCategoryInfo;
    }

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    public UnitInfo getUnitInfo() {
        return unitInfo;
    }

    public void setUnitInfo(UnitInfo unitInfo) {
        this.unitInfo = unitInfo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
