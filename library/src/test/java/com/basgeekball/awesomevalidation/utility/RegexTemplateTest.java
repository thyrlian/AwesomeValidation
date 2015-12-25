package com.basgeekball.awesomevalidation.utility;

import junit.framework.TestCase;

import java.util.regex.Pattern;

public class RegexTemplateTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

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
        assertFalse(pattern.matcher("").matches());
        assertFalse(pattern.matcher(" ").matches());
        assertFalse(pattern.matcher("  ").matches());
    }

    public void testConstantTelephone() {
        Pattern pattern = Pattern.compile(RegexTemplate.TELEPHONE);
        assertTrue(pattern.matcher("1234567890").matches());
        assertTrue(pattern.matcher("+49-030-00000001").matches());
        assertFalse(pattern.matcher("a123456789").matches());
    }

}
