package com.inventory.web.error;

/**
 * Created by dhiraj on 8/16/17.
 */
public class UserManageError {

    private String error;

    private boolean valid;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
