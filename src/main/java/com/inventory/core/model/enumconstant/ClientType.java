package com.inventory.core.model.enumconstant;

/**
 * Created by dhiraj on 6/29/17.
 */
public enum  ClientType {

    CUSTOMER("Customer"), VENDOR("Vendor");

    private final String value;

    ClientType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public static ClientType getEnum(String value) {
        if (value == null)
            throw new IllegalArgumentException();
        for (ClientType v : values())
            if (value.equalsIgnoreCase(v.getValue()))
                return v;
        throw new IllegalArgumentException();
    }
}
