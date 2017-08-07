package com.inventory.core.model.dto;

import org.springframework.data.domain.Sort;

/**
 * Created by dhiraj on 8/1/17.
 */
public class PageableDTO {

    private Integer page;

    private Integer size;

    private String properties;

    private Sort.Direction directionSort;

    private String direction;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Sort.Direction getDirectionSort(Sort.Direction desc) {
        return directionSort;
    }

    public void setDirectionSort(Sort.Direction directionSort) {
        this.directionSort = directionSort;
    }
}
