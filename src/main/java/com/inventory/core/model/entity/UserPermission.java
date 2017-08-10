package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Permission;

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
	private List<Permission> permissionList = new ArrayList<Permission>();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Permission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}
}
