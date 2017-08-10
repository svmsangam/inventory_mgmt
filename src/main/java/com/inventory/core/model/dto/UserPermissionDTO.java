package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.permission;

import java.util.List;

/**
 * Created by dhiraj on 8/10/17.
 */
public class UserPermissionDTO {

    private Long userPermissionId;

    private InvUserDTO userDTO;

    private Long userId;

    private List<permission> permissionList;

    private Integer version;

    public Long getUserPermissionId() {
        return userPermissionId;
    }

    public void setUserPermissionId(Long userPermissionId) {
        this.userPermissionId = userPermissionId;
    }

    public InvUserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(InvUserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<permission> permissionList) {
        this.permissionList = permissionList;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
