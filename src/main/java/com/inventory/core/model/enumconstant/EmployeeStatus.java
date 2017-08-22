package com.inventory.core.model.enumconstant;

/**
 * Created by dhiraj on 8/1/17.
 */
public enum EmployeeStatus {

    PENDING("Pending"), CONFIRM("Confirm"), REGINED("Regined"), FIRED("Fired"),;

    private final String value;

    EmployeeStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public static EmployeeStatus getEnum(String value) {
        if (value == null)
            throw new IllegalArgumentException();
        for (EmployeeStatus v : values())
            if (value.equalsIgnoreCase(v.getValue()))
                return v;
        throw new IllegalArgumentException();
    }
}
