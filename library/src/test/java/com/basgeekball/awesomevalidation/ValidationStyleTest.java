package com.basgeekball.awesomevalidation;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ValidationStyleTest extends TestCase {

    public void testFromValueToBasic() {
        assertEquals(ValidationStyle.BASIC, ValidationStyle.fromValue(0));
    }

    public void testFromValueToColoration() {
        assertEquals(ValidationStyle.COLORATION, ValidationStyle.fromValue(1));
    }

    public void testFromValueToUnderlabel() {
        assertEquals(ValidationStyle.UNDERLABEL, ValidationStyle.fromValue(2));
    }

    public void testFromValueToTextInputLayout() {
        assertEquals(ValidationStyle.TEXT_INPUT_LAYOUT, ValidationStyle.fromValue(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromValueThrowException() {
        ValidationStyle.fromValue(4);
    }

}
