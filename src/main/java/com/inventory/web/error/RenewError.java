package com.inventory.web.error;

/**
 * Created by dhiraj on 2/7/18.
 */
public class RenewError {

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
