package com.basgeekball.awesomevalidation.utility.custom;

import com.basgeekball.awesomevalidation.ValidationHolder;

import junit.framework.TestCase;

import static org.mockito.Mockito.mock;

public class CustomErrorResetTest extends TestCase {

    private CustomErrorReset mEmptyCustomErrorReset;
    private ValidationHolder mMockValidationHolder;
    private int mCounter;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mCounter = 0;
        mMockValidationHolder = mock(ValidationHolder.class);
        mEmptyCustomErrorReset = new CustomErrorReset() {
            @Override
            public void reset(ValidationHolder validationHolder) {
                mCounter += 1;
            }
        };
    }

    public void testReset() {
        mEmptyCustomErrorReset.reset(mMockValidationHolder);
        assertEquals(1, mCounter);
    }

}
