package com.basgeekball.awesomevalidation.helper;

import junit.framework.TestCase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class CustomValidationTest extends TestCase {

    private CustomValidation mMockCustomValidation;
    private String mMockString;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockCustomValidation = mock(CustomValidation.class);
        mMockString = "";
    }

    public void testCompareReturnTrue() {
        doReturn(true).when(mMockCustomValidation).compare(any(String.class));
        assertTrue(mMockCustomValidation.compare(mMockString));
    }

    public void testCompareReturnFalse() {
        doReturn(false).when(mMockCustomValidation).compare(any(String.class));
        assertFalse(mMockCustomValidation.compare(mMockString));
    }

}
