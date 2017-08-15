package com.inventory.web.error;

/**
 * Created by dhiraj on 8/15/17.
 */
public class UnitInfoError {

    private String name;

    private String code;

    private boolean valid;

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

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
