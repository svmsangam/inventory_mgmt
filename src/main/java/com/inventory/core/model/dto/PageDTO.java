package com.inventory.core.model.dto;

import java.util.List;

/**
 * Created by dhiraj on 8/2/17.
 */
public class PageDTO {

    private int lastpage;

    private int currentPage;

    private List<Integer> pagelist;

    private String directionPage;

    private String direction;

    private String sort;

    public int getLastpage() {
        return lastpage;
    }

    public void setLastpage(int lastpage) {
        this.lastpage = lastpage;
    }

    public List<Integer> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<Integer> pagelist) {
        this.pagelist = pagelist;
    }

    public String getDirectionPage() {
        return directionPage;
    }

    public void setDirectionPage(String directionPage) {
        this.directionPage = directionPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
