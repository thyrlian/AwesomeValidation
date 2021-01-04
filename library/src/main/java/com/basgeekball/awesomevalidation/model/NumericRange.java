package com.basgeekball.awesomevalidation.model;

import androidx.annotation.Nullable;

import com.google.common.collect.Range;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class NumericRange {

    private Range mRange;

    public NumericRange(Range range) {
        mRange = range;
    }

    private static boolean isNumberFormat(String text) {
        return Pattern.compile("^-?(([1-9]\\d*(\\.\\d+)?)||(0\\.\\d+)||0)$").matcher(text).find();
    }

    public boolean isValid(String valueText) {
        if (!isNumberFormat(valueText)) return false;

        BigDecimal value;
        try {
            value = new BigDecimal(valueText);
        } catch (Exception e) {
            return false;
        }

        Boolean validityAsInteger = isInteger(value);
        if (validityAsInteger != null) return validityAsInteger;
        Boolean validityAsDecimal = isDecimal(value);
        if (validityAsDecimal != null) return validityAsDecimal;

        return false;
    }

    @Nullable
    private Boolean isInteger(BigDecimal value) {
        if (value.scale() == 0) {
            try {
                return mRange.contains(value.intValueExact());
            } catch (Exception e) {
            }
            try {
                return mRange.contains(value.longValueExact());
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Nullable
    private Boolean isDecimal(BigDecimal value) {
        try {
            return mRange.contains(value.floatValue());
        } catch (Exception e) {
        }
        try {
            return mRange.contains(value.doubleValue());
        } catch (Exception e) {
        }
        return null;
    }

}