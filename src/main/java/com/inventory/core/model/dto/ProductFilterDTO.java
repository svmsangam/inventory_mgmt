package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.TrendingLevel;

public class ProductFilterDTO {

	private String name;

	private TrendingLevel trendingLevel;

	private int greaterThanInStock;

	private int lessThanInStock;

	private long subCategoryId;

	private Integer page;
	
	private Long storeInfoId;

	private int size;

	private Status status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TrendingLevel getTrendingLevel() {
		return trendingLevel;
	}

	public void setTrendingLevel(TrendingLevel trendingLevel) {
		this.trendingLevel = trendingLevel;
	}

	public int getGreaterThanInStock() {
		return greaterThanInStock;
	}

	public void setGreaterThanInStock(int greaterThanInStock) {
		this.greaterThanInStock = greaterThanInStock;
	}

	public int getLessThanInStock() {
		return lessThanInStock;
	}

	public void setLessThanInStock(int lessThanInStock) {
		this.lessThanInStock = lessThanInStock;
	}

	public long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getStoreInfoId() {
		return storeInfoId;
	}

	public void setStoreInfoId(Long storeInfoId) {
		this.storeInfoId = storeInfoId;
	}

}
