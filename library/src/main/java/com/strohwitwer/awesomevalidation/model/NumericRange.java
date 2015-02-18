package com.strohwitwer.awesomevalidation.model;

import com.google.common.collect.Range;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumericRange {

    private Range mRange;

    public NumericRange(Range range) {
        mRange = range;
    }

    public boolean isValid(String valueText) {
        // check if leading character is not a number (1-9)
        Matcher matcher = Pattern.compile("^[^1-9]").matcher(valueText);
        if (matcher.find()) {
            return false;
        }

        boolean valid;
        BigDecimal value;
        try {
            value = new BigDecimal(valueText);
        } catch (Exception e) {
            return false;
        }

        if (value.scale() == 0) {
            try {
                valid = mRange.contains(value.intValueExact());
                return valid;
            } catch (Exception e) {
            }
            try {
                valid = mRange.contains(value.longValueExact());
                return valid;
            } catch (Exception e) {
            }
        } else if (value.scale() > 0) {
            try {
                valid = mRange.contains(value.floatValue());
                return valid;
            } catch (Exception e) {
            }
            try {
                valid = mRange.contains(value.doubleValue());
                return valid;
            } catch (Exception e) {
            }
        }
        return false;
    }

}