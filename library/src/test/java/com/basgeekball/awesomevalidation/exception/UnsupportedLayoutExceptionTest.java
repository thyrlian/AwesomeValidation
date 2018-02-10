package com.basgeekball.awesomevalidation.exception;

import org.junit.Test;

public class UnsupportedLayoutExceptionTest {

    @Test(expected = UnsupportedLayoutException.class)
    public void testUnsupportedLayoutException() {
        throw new UnsupportedLayoutException("Test");
    }

}
