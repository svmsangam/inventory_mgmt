package com.inventory.core.api.impl;

import com.inventory.core.model.liteentity.CategoryDomain;

import java.util.ArrayList;
import java.util.List;

public class CategoryTreeService {

    private List<CategoryDomain> childList = new ArrayList<>();

    public CategoryTreeService(List<CategoryDomain> childList){
        this.childList = childList;
    }

    public List<CategoryDomain> getTree(List<CategoryDomain> parentList){

        if (parentList == null | childList == null){
            return null;
        }

        List<CategoryDomain> tree = new ArrayList<>();

        for (CategoryDomain categoryDomain : parentList){
            tree.add(categoryDomain);
            tree.addAll(getChild(categoryDomain , new ArrayList<>()));
        }

        return tree;
    }

    private List<CategoryDomain> getChild(CategoryDomain parent , List<CategoryDomain> tree){

        for (CategoryDomain categoryDomain : childList){
            if (categoryDomain.getParentId() == parent.getCategoryId()){
               // childList.remove(categoryDomain);
                categoryDomain.setCategoryName(getName(categoryDomain));
                tree.add(categoryDomain);
                getChild(categoryDomain , tree);
            }
        }

        return tree;
    }

    private String getName(CategoryDomain categoryDomain){

        StringBuilder prefix = new StringBuilder();

        for (int i = 0 ; i< categoryDomain.getDepth() ; i++){
            prefix.append("-");
        }

        return prefix + " " + categoryDomain.getCategoryName();
    }

}
