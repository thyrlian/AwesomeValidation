package com.basgeekball.awesomevalidation.utility.custom;

import com.basgeekball.awesomevalidation.ValidationHolder;

import junit.framework.TestCase;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class CustomValidationTest extends TestCase {

    private CustomValidation mMockCustomValidation;
    private ValidationHolder mMockValidationHolder;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockCustomValidation = mock(CustomValidation.class);
        mMockValidationHolder = mock(ValidationHolder.class);
    }

    public void testCompareReturnTrue() {
        doReturn(true).when(mMockCustomValidation).compare(mMockValidationHolder);
        assertTrue(mMockCustomValidation.compare(mMockValidationHolder));
    }

    public void testCompareReturnFalse() {
        doReturn(false).when(mMockCustomValidation).compare(mMockValidationHolder);
        assertFalse(mMockCustomValidation.compare(mMockValidationHolder));
    }

}
