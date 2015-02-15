package com.strohwitwer.awesomevalidation.model;

import java.math.BigDecimal;

public class NumericRange {

    public enum Type {
        INT,
        LONG,
        FLOAT,
        DOUBLE
    }

    private BigDecimal mMin;
    private BigDecimal mMax;
    private boolean mMinInclusive;
    private boolean mMaxInclusive;
    private Type mType;

    public NumericRange(int min, int max, boolean minInclusive, boolean maxInclusive) {
        mMin = new BigDecimal(min);
        mMax = new BigDecimal(max);
        mMinInclusive = minInclusive;
        mMaxInclusive = maxInclusive;
        mType = Type.INT;
    }

    public NumericRange(int min, int max, boolean inclusive) {
        this(min, max, inclusive, inclusive);
    }

    public NumericRange(int min, int max) {
        this(min, max, true, true);
    }

    public boolean isValid(BigDecimal value) {
        boolean valid;

        switch (mType) {
            case INT:
                if (value.scale() > 0) {
                    return false;
                }
                try {
                    value.intValueExact();
                } catch (ArithmeticException e) {
                    return false;
                }
                break;
            case LONG:
                if (value.scale() > 0) {
                    return false;
                }
                try {
                    value.longValueExact();
                } catch (ArithmeticException e) {
                    return false;
                }
                break;
        }

        int resultOfComparison = value.compareTo(mMin);
        valid = (resultOfComparison == 1 || (mMinInclusive && resultOfComparison == 0));
        if (!valid) {
            return valid;
        }
        resultOfComparison = value.compareTo(mMax);
        return resultOfComparison == -1 || (mMaxInclusive && resultOfComparison == 0);
    }

}