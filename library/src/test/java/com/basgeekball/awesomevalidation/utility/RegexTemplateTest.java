package com.basgeekball.awesomevalidation.utility;

import junit.framework.TestCase;

import java.util.regex.Pattern;

public class RegexTemplateTest extends TestCase {

    public void testConstantNotEmpty() {
        Pattern pattern = Pattern.compile(RegexTemplate.NOT_EMPTY);
        assertTrue(pattern.matcher("a").matches());
        assertTrue(pattern.matcher("1").matches());
        assertTrue(pattern.matcher(".").matches());
        assertTrue(pattern.matcher("$").matches());
        assertTrue(pattern.matcher("!@#$%^&*()").matches());
        assertTrue(pattern.matcher("a1").matches());
        assertTrue(pattern.matcher("1q2w3e").matches());
        assertTrue(pattern.matcher(" x").matches());
        assertTrue(pattern.matcher("x ").matches());
        assertTrue(pattern.matcher(" x ").matches());
        assertTrue(pattern.matcher("\nx").matches());
        assertTrue(pattern.matcher("x\n").matches());
        assertTrue(pattern.matcher("\nx\n").matches());
        assertTrue(pattern.matcher(" x\n").matches());
        assertTrue(pattern.matcher("\nx ").matches());
        assertTrue(pattern.matcher("a b c").matches());
        assertTrue(pattern.matcher("a\nb\nc").matches());
        assertTrue(pattern.matcher(" a b c ").matches());
        assertFalse(pattern.matcher("").matches());
        assertFalse(pattern.matcher(" ").matches());
        assertFalse(pattern.matcher("  ").matches());
        assertFalse(pattern.matcher("\n").matches());
        assertFalse(pattern.matcher("\n\n").matches());
        assertFalse(pattern.matcher("\n \n").matches());
        assertFalse(pattern.matcher("\n  \n").matches());
        assertFalse(pattern.matcher("\n \n \n").matches());
    }

    public void testConstantTelephone() {
        Pattern pattern = Pattern.compile(RegexTemplate.TELEPHONE);
        assertTrue(pattern.matcher("1234567890").matches());
        assertTrue(pattern.matcher("+49-030-00000001").matches());
        assertTrue(pattern.matcher("+49-(030)-00000001").matches());
        assertTrue(pattern.matcher("+490123456789").matches());
        assertTrue(pattern.matcher("(0)12345678").matches());
        assertTrue(pattern.matcher("030-00000001").matches());
        assertTrue(pattern.matcher("123456789-001").matches());
        assertTrue(pattern.matcher("123 456 789").matches());
        assertFalse(pattern.matcher("49+007").matches());
        assertFalse(pattern.matcher("a123456789").matches());
        assertFalse(pattern.matcher("123456789b").matches());
        assertFalse(pattern.matcher("++49-00000001").matches());
        assertFalse(pattern.matcher("+-00000001").matches());
        assertFalse(pattern.matcher("123*456").matches());
    }

}
