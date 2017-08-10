package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;

/**
 * Created by dhiraj on 8/10/17.
 */
public class StoreUserInfoDTO {

    private Long storeUserInfoId;

    private StoreInfoDTO storeInfoDTO;

    private Long storeInfoId;

    private InvUserDTO userDTO;

    private Long userId;

    private Integer version;

    private Status status;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getStoreUserInfoId() {
        return storeUserInfoId;
    }

    public void setStoreUserInfoId(Long storeUserInfoId) {
        this.storeUserInfoId = storeUserInfoId;
    }

    public StoreInfoDTO getStoreInfoDTO() {
        return storeInfoDTO;
    }

    public void setStoreInfoDTO(StoreInfoDTO storeInfoDTO) {
        this.storeInfoDTO = storeInfoDTO;
    }

    public Long getStoreInfoId() {
        return storeInfoId;
    }

    public void setStoreInfoId(Long storeInfoId) {
        this.storeInfoId = storeInfoId;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
