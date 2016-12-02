package com.basgeekball.awesomevalidation.exception;

import org.junit.Test;

public class BadLayoutExceptionTest {

    @Test(expected = BadLayoutException.class)
    public void testBadLayoutException() {
        throw new BadLayoutException("Test");
    }

}
