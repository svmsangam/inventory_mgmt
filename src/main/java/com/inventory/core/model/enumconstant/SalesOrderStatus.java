package com.inventory.core.model.enumconstant;

public enum SalesOrderStatus {

    PENDDING("Pendding"), ACCEPTED("Accepted"), PACKED("Packed"), SHIPPED("Shipped"), DELIVERED("Delivered"), CANCEL("Cancelled");

    private final String value;

    SalesOrderStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public static SalesOrderStatus getEnum(String value) {
        if (value == null)
            throw new IllegalArgumentException();
        for (SalesOrderStatus v : values())
            if (value.equalsIgnoreCase(v.getValue()))
                return v;
        throw new IllegalArgumentException();
    }
}



