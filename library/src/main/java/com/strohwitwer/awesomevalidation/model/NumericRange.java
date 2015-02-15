package com.strohwitwer.awesomevalidation.model;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public boolean isValid(String valueText) {
        Matcher matcher = Pattern.compile("^[^1-9]").matcher(valueText);
        if (matcher.find()) {
            return false;
        }

        BigDecimal value = new BigDecimal(valueText);
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

        boolean valid;
        int resultOfComparison = value.compareTo(mMin);
        valid = (resultOfComparison == 1 || (mMinInclusive && resultOfComparison == 0));
        if (!valid) {
            return valid;
        }
        resultOfComparison = value.compareTo(mMax);
        return resultOfComparison == -1 || (mMaxInclusive && resultOfComparison == 0);
    }

}