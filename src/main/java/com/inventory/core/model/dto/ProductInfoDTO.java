package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.TrendingLevel;

/**
 * Created by dhiraj on 8/6/17.
 */
public class ProductInfoDTO {

    private Long poductId;

    private String name;

    private String code;

    private String description;

    private TrendingLevel trendingLevel;

    private SubCategoryInfoDTO subCategoryInfo;

    private Long subCategoryId;

    private long storeInfoId;

    private UnitInfoDTO unitInfo;

    private Long unitId;

    private Status status;

    private long createdById;

    private String createdByName;

    public Long getPoductId() {
        return poductId;
    }

    public void setPoductId(Long poductId) {
        this.poductId = poductId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public SubCategoryInfoDTO getSubCategoryInfo() {
        return subCategoryInfo;
    }

    public void setSubCategoryInfo(SubCategoryInfoDTO subCategoryInfo) {
        this.subCategoryInfo = subCategoryInfo;
    }

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public long getStoreInfoId() {
        return storeInfoId;
    }

    public void setStoreInfoId(long storeInfoId) {
        this.storeInfoId = storeInfoId;
    }

    public UnitInfoDTO getUnitInfo() {
        return unitInfo;
    }

    public void setUnitInfo(UnitInfoDTO unitInfo) {
        this.unitInfo = unitInfo;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }
}
