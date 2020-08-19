package com.basgeekball.awesomevalidation

import junit.framework.TestCase

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidationStyleTest : TestCase() {

    @Test
    fun testFromValueToBasic() {
        assertEquals(ValidationStyle.BASIC, ValidationStyle.fromValue(0))
    }

    @Test
    fun testFromValueToColoration() {
        assertEquals(ValidationStyle.COLORATION, ValidationStyle.fromValue(1))
    }

    @Test
    fun testFromValueToUnderlabel() {
        assertEquals(ValidationStyle.UNDERLABEL, ValidationStyle.fromValue(2))
    }

    @Test
    fun testFromValueToTextInputLayout() {
        assertEquals(ValidationStyle.TEXT_INPUT_LAYOUT, ValidationStyle.fromValue(3))
    }

    @Test(expected = IllegalArgumentException::class)
    fun testFromValueThrowException() {
        ValidationStyle.fromValue(4)
    }
}
