package com.inventory.core.model.enumconstant;

/**
 * Created by dhiraj on 8/1/17.
 */
public enum AccountAssociateType {

    EMPLOYEE("Employee"), CUSTOMER("Customer"), STORE("Store") , VENDOR("Vendor");

    private final String value;

    AccountAssociateType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public static AccountAssociateType getEnum(String value) {
        if (value == null)
            throw new IllegalArgumentException();
        for (AccountAssociateType v : values())
            if (value.equalsIgnoreCase(v.getValue()))
                return v;
        throw new IllegalArgumentException();
    }
}
