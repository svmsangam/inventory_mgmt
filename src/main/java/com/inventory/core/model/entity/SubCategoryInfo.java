package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.CategoryType;
import com.inventory.core.model.enumconstant.Status;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "subcategory_table")
public class SubCategoryInfo extends AbstractEntity<Long> {

    private String name;

    private String code;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private StoreInfo storeInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    private User createdBy;

    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private SubCategoryInfo parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<SubCategoryInfo> childList;

    private CategoryType type;

    public List<SubCategoryInfo> getChildList() {
        return childList;
    }

    public void setChildList(List<SubCategoryInfo> childList) {
        this.childList = childList;
    }

    public SubCategoryInfo getParent() {
        return parent;
    }

    public void setParent(SubCategoryInfo parent) {
        this.parent = parent;
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
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

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
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
