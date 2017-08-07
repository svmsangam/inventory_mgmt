package com.inventory.core.model.enumconstant;

/**
 * Created by dhiraj on 8/1/17.
 */
public enum ProfileAssociateType {
    EMPLOYEE("Employee");

    private final String value;

    ProfileAssociateType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public static ProfileAssociateType getEnum(String value) {
        if (value == null)
            throw new IllegalArgumentException();
        for (ProfileAssociateType v : values())
            if (value.equalsIgnoreCase(v.getValue()))
                return v;
        throw new IllegalArgumentException();
    }
}
