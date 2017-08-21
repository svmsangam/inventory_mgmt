package com.inventory.core.model.dto;

import com.inventory.core.model.enumconstant.Status;

public class WebServiceDTO {

    private String api_user;

    private String api_password;

    private String access_key;

    private Status status;

    public String getApi_user() {
        return api_user;
    }

    public void setApi_user(String api_user) {
        this.api_user = api_user;
    }

    public String getApi_password() {
        return api_password;
    }

    public void setApi_password(String api_password) {
        this.api_password = api_password;
    }

    public String getAccess_key() {
        return access_key;
    }

    public void setAccess_key(String access_key) {
        this.access_key = access_key;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}
