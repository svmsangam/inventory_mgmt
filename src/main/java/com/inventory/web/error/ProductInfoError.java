package com.inventory.web.error;

/**
 * Created by dhiraj on 8/22/17.
 */
public class ProductInfoError {

    private String name;

    private String code;

    private String description;

    private String trendingLevel;

    private String subCategoryId;

    private String unitId;

    private String version;

    private boolean valid;

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

    public String getTrendingLevel() {
        return trendingLevel;
    }

    public void setTrendingLevel(String trendingLevel) {
        this.trendingLevel = trendingLevel;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
