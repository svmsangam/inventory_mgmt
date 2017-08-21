package com.inventory.core.model.enumconstant;

public enum PurchaseOrderStatus {

    PENDDING("Pendding"), ISSUED("Issued"), RECEIVED("Received"), CANCELLED("Cancelled");

    private final String value;

    PurchaseOrderStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public static PurchaseOrderStatus getEnum(String value) {
        if (value == null)
            throw new IllegalArgumentException();
        for (PurchaseOrderStatus v : values())
            if (value.equalsIgnoreCase(v.getValue()))
                return v;
        throw new IllegalArgumentException();
    }
}
