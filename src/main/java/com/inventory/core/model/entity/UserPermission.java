package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.permission;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "userpermission")
public class UserPermission extends AbstractEntity<Long> {
	
	@OneToOne(fetch = FetchType.EAGER)
	private User user;

	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name="permissions" )
	private List<permission> permissionList = new ArrayList<permission>();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<permission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<permission> permissionList) {
		this.permissionList = permissionList;
	}
}
