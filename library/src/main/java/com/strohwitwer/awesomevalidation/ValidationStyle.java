package com.strohwitwer.awesomevalidation;

public enum ValidationStyle {

    COLORATION(0);

    private int mValue;

    private ValidationStyle(int value) {
        mValue = value;
    }

    public int value() {
        return mValue;
    }

    public static ValidationStyle fromValue(int value) {
        switch (value) {
            case 0:
                return ValidationStyle.COLORATION;
            default:
                throw new IllegalArgumentException("Unknown ValidationStyle value.");
        }
    }

}