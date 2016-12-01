package com.basgeekball.awesomevalidation.model;

import com.google.common.collect.Range;

import junit.framework.TestCase;

import org.powermock.reflect.Whitebox;

import java.util.Calendar;

public class NumericRangeTest extends TestCase {

    public void testIsNumberFormatPositive() throws Exception {
        assertTrue(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "0"));
        assertTrue(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "0.0"));
        assertTrue(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "0.123"));
        assertTrue(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "1.23"));
        assertTrue(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "10.23"));
        assertTrue(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "12.34"));
        assertTrue(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "100.234"));
        assertTrue(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "100.023"));
        assertTrue(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "123.456"));
        assertTrue(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "123"));
        assertTrue(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "-12"));
        assertTrue(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "-0.1"));
        assertTrue(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "-1.2"));
        assertTrue(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "-12.34"));
        assertTrue(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "-10.23"));
        assertTrue(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "-100.234"));
        assertTrue(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "-123.456"));
    }

    public void testIsNumberFormatNegative() throws Exception {
        assertFalse(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "00.123"));
        assertFalse(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "01.23"));
        assertFalse(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "10."));
        assertFalse(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "--10"));
        assertFalse(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "02012"));
        assertFalse(Whitebox.<Boolean> invokeMethod(NumericRange.class, "isNumberFormat", "a1000"));
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