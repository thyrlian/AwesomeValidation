package com.strohwitwer.awesomevalidation.test.model;

import android.test.AndroidTestCase;

import com.google.common.collect.Range;
import com.strohwitwer.awesomevalidation.model.NumericRange;

import java.util.Calendar;

public class NumericRangeTest extends AndroidTestCase {

    public void testClosedIntRange() {
        int min = 1900;
        int max = Calendar.getInstance().get(Calendar.YEAR);
        NumericRange numericRange = new NumericRange(Range.closed(min, max));

        assertTrue(numericRange.isValid(Integer.toString(min)));
        assertTrue(numericRange.isValid(Integer.toString(max)));
        assertTrue(numericRange.isValid(Integer.toString(Math.round((min + max) / 2))));

        assertFalse(numericRange.isValid(Integer.toString(min - 1)));
        assertFalse(numericRange.isValid(Integer.toString(max + 1)));
        assertFalse(numericRange.isValid("0" + Integer.toString(min)));
        assertFalse(numericRange.isValid(Integer.toString(min) + ".0"));
        assertFalse(numericRange.isValid("a" + Integer.toString(min)));
        assertFalse(numericRange.isValid(Integer.toString(min) + "a"));
        assertFalse(numericRange.isValid("0"));
        assertFalse(numericRange.isValid("9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"));
        assertFalse(numericRange.isValid("abcd"));
    }

}