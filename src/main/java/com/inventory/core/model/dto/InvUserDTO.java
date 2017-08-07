package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;

public class InvUserDTO  {
	
	private Long userId;
	
	private String inventoryuser;
	
	private String userpassword;
	
	private String userrepassword;

	private UserType userType;

	private Status userstatus;
	
	private String userauthority;
	
	private Boolean enable;

	private String secret_key;
	
	private Long storeId;

	public String getInventoryuser() {
		return inventoryuser;
	}

	public void setInventoryuser(String inventoryuser) {
		this.inventoryuser = inventoryuser;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public String getUserrepassword() {
		return userrepassword;
	}

	public void setUserrepassword(String userrepassword) {
		this.userrepassword = userrepassword;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Status getUserstatus() {
		return userstatus;
	}

	public void setUserstatus(Status userstatus) {
		this.userstatus = userstatus;
	}

	public String getUserauthority() {
		return userauthority;
	}

	public void setUserauthority(String userauthority) {
		this.userauthority = userauthority;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getSecret_key() {
		return secret_key;
	}

	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
}
