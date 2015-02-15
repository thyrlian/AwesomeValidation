package com.strohwitwer.awesomevalidation.model;

import java.math.BigDecimal;

public class NumericRange {

    private BigDecimal mMin;
    private BigDecimal mMax;
    private boolean mMinInclusive;
    private boolean mMaxInclusive;

    public NumericRange(BigDecimal min, BigDecimal max, boolean minInclusive, boolean maxInclusive) {
        mMin = min;
        mMax = max;
        mMinInclusive = minInclusive;
        mMaxInclusive = maxInclusive;
    }

    public NumericRange(BigDecimal min, BigDecimal max, boolean inclusive) {
        this(min, max, inclusive, inclusive);
    }

    public NumericRange(BigDecimal min, BigDecimal max) {
        this(min, max, true, true);
    }

    public boolean isValid(BigDecimal value) {
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