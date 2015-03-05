package com.strohwitwer.awesomevalidation;

public enum ValidationStyle {

    BASIC(0),
    COLORATION(1),
    UNDERLABEL(2),
    TOASTMSG(3);

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
                return ValidationStyle.BASIC;
            case 1:
                return ValidationStyle.COLORATION;
            case 2:
                return ValidationStyle.UNDERLABEL;
            case 3:
                return ValidationStyle.TOASTMSG;
            default:
                throw new IllegalArgumentException("Unknown ValidationStyle value.");
        }
    }

}