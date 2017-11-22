package com.inventory.core.model.enumconstant;

/**
 * Created by dhiraj on 11/22/17.
 */
public enum BuyerType {
    CUSTOMER("Customer"), VENDOR("Vendor") , STORE("Store");

    private final String value;

    BuyerType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public static BuyerType getEnum(String value) {
        if (value == null)
            throw new IllegalArgumentException();
        for (BuyerType v : values())
            if (value.equalsIgnoreCase(v.getValue()))
                return v;
        throw new IllegalArgumentException();
    }
}
