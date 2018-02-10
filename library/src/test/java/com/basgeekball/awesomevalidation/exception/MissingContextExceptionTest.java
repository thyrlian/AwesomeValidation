package com.basgeekball.awesomevalidation.exception;

import org.junit.Test;

public class MissingContextExceptionTest {

    @Test(expected = MissingContextException.class)
    public void testMissingContextException() {
        throw new MissingContextException("Test");
    }

}
