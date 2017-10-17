package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.LockResponse;

/**
 * Created by dhiraj on 10/17/17.
 */
public class LockResponseDTO {

    private LockResponse response;

    private String message;

    private Object detail;

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LockResponse getResponse() {
        return response;
    }

    public void setResponse(LockResponse response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }
}
