package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class CategoryInfo extends AbstractEntity< Long> {

	private static final long serialVersionUID = 2013540395981142796L;

	private String name;

	private String code;

	private String description;

	private Status status;

	@ManyToOne(fetch = FetchType.EAGER)
	private StoreInfo storeInfo;

	@ManyToOne(fetch = FetchType.EAGER)
	private User createdBy;

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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public StoreInfo getStoreInfo() {
		return storeInfo;
	}

	public void setStoreInfo(StoreInfo storeInfo) {
		this.storeInfo = storeInfo;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
}
