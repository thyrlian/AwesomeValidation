package com.basgeekball.awesomevalidation.utility.custom;

import junit.framework.TestCase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class SimpleCustomValidationTest extends TestCase {

    private SimpleCustomValidation mMockSimpleCustomValidation;
    private String mMockString;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockSimpleCustomValidation = mock(SimpleCustomValidation.class);
        mMockString = "";
    }

    public void testCompareReturnTrue() {
        doReturn(true).when(mMockSimpleCustomValidation).compare(any(String.class));
        assertTrue(mMockSimpleCustomValidation.compare(mMockString));
    }

    public void testCompareReturnFalse() {
        doReturn(false).when(mMockSimpleCustomValidation).compare(any(String.class));
        assertFalse(mMockSimpleCustomValidation.compare(mMockString));
    }

}
