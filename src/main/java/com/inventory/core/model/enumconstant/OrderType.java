package com.inventory.core.model.enumconstant;

/**
 * Created by dhiraj on 6/29/17.
 */
public enum OrderType {

    Purchase("purchase order"), Sale("sales order");

    private final String value;

    OrderType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public static OrderType getEnum(String value) {
        if (value == null)
            throw new IllegalArgumentException();
        for (OrderType v : values())
            if (value.equalsIgnoreCase(v.getValue()))
                return v;
        throw new IllegalArgumentException();
    }
}
