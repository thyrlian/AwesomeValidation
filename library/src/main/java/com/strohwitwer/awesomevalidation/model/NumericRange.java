package com.strohwitwer.awesomevalidation.model;

public class NumericRange {

    private int mMin;
    private int mMax;
    private boolean mMinInclusive;
    private boolean mMaxInclusive;

    public NumericRange(int min, int max, boolean minInclusive, boolean maxInclusive) {
        mMin = min;
        mMax = max;
        mMinInclusive = minInclusive;
        mMaxInclusive = maxInclusive;
    }

    public NumericRange(int min, int max, boolean inclusive) {
        this(min, max, inclusive, inclusive);
    }

    public NumericRange(int min, int max) {
        this(min, max, true, true);
    }

    public boolean isValid(int value) {
        boolean valid;
        valid = (mMinInclusive ? value >= mMin : value > mMin);
        if (!valid) {
            return valid;
        }
        return (mMaxInclusive ? value <= mMax : value < mMax);
    }

}