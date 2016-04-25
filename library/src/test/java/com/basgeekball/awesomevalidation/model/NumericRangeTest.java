package com.basgeekball.awesomevalidation.model;

import com.google.common.collect.Range;

import junit.framework.TestCase;

import java.util.Calendar;

public class NumericRangeTest extends TestCase {

    public void testIsNumberFormatPositive() {
        assertTrue(NumericRange.isNumberFormat("0"));
        assertTrue(NumericRange.isNumberFormat("0.0"));
        assertTrue(NumericRange.isNumberFormat("0.123"));
        assertTrue(NumericRange.isNumberFormat("1.23"));
        assertTrue(NumericRange.isNumberFormat("10.23"));
        assertTrue(NumericRange.isNumberFormat("12.34"));
        assertTrue(NumericRange.isNumberFormat("100.234"));
        assertTrue(NumericRange.isNumberFormat("100.023"));
        assertTrue(NumericRange.isNumberFormat("123.456"));
        assertTrue(NumericRange.isNumberFormat("123"));
        assertTrue(NumericRange.isNumberFormat("-12"));
        assertTrue(NumericRange.isNumberFormat("-0.1"));
        assertTrue(NumericRange.isNumberFormat("-1.2"));
        assertTrue(NumericRange.isNumberFormat("-12.34"));
        assertTrue(NumericRange.isNumberFormat("-10.23"));
        assertTrue(NumericRange.isNumberFormat("-100.234"));
        assertTrue(NumericRange.isNumberFormat("-123.456"));
    }

    public void testIsNumberFormatNegative() {
        assertFalse(NumericRange.isNumberFormat("00.123"));
        assertFalse(NumericRange.isNumberFormat("01.23"));
        assertFalse(NumericRange.isNumberFormat("10."));
        assertFalse(NumericRange.isNumberFormat("--10"));
        assertFalse(NumericRange.isNumberFormat("02012"));
        assertFalse(NumericRange.isNumberFormat("a1000"));
    }

    public void testInvalidNumbers() {
        NumericRange numericRange = new NumericRange(Range.closed(0.0f, 10000.0f));

        assertFalse(numericRange.isValid("00.123"));
        assertFalse(numericRange.isValid("01.23"));
        assertFalse(numericRange.isValid("02012"));
        assertFalse(numericRange.isValid("a1000"));
    }

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

    public void testClosedFloatRange() {
        NumericRange numericRange = new NumericRange(Range.closed(0.0f, 100.0f));

        assertTrue(numericRange.isValid("0.0"));
        assertTrue(numericRange.isValid("0.00000"));
        assertTrue(numericRange.isValid("0.00001"));
        assertTrue(numericRange.isValid("99.99"));
        assertTrue(numericRange.isValid("100.0"));
        assertTrue(numericRange.isValid("100.00000"));
        assertTrue(numericRange.isValid("50.0"));
        assertTrue(numericRange.isValid(Integer.toString(-0)));
        assertTrue(numericRange.isValid(Integer.toString(0)));
        assertTrue(numericRange.isValid(Integer.toString(50)));
        assertTrue(numericRange.isValid(Integer.toString(100)));

        assertFalse(numericRange.isValid("-0.00001"));
        assertFalse(numericRange.isValid("-1.0"));
        assertFalse(numericRange.isValid("100.00001"));
        assertFalse(numericRange.isValid("1000.0"));
        assertFalse(numericRange.isValid(Integer.toString(-1)));
        assertFalse(numericRange.isValid(Integer.toString(101)));
    }

}