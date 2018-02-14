package com.inventory.core.model.liteentity;

public class CategoryDomain {

    private long categoryId;

    private String categoryName;

    private String categoryCode;

    private String description;

    private int depth;

    private Long parentId;

    private String parentName;

    public CategoryDomain(long categoryId , String name , String code , String description , int depth , Long parentId , String parentName){

        this.categoryId = categoryId;
        this.categoryName = name;
        this.categoryCode = code;
        this.description = description;
        this.depth = depth;
        this.parentId = parentId;
        this.parentName = parentName;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
