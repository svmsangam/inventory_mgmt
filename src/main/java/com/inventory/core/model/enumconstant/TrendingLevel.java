package com.inventory.core.model.enumconstant;

/**
 * Created by dhiraj on 7/3/17.
 */
public enum TrendingLevel {

    VERYLOW("Very low"), LOW("Low"), AVERAGE("Average"),HIGH("Hight") ;

    private final String value;

    TrendingLevel(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }


    public static TrendingLevel getEnum(String value) {
        if (value == null)
            throw new IllegalArgumentException();
        for (TrendingLevel v : values())
            if (value.equalsIgnoreCase(v.getValue()))
                return v;
        throw new IllegalArgumentException();
    }
}
