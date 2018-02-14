package com.basgeekball.awesomevalidation.utility.custom;

import com.basgeekball.awesomevalidation.ValidationHolder;

import junit.framework.TestCase;

import static org.mockito.Mockito.mock;

public class CustomValidationCallbackTest extends TestCase {

    private CustomValidationCallback mEmptyCustomValidationCallback;
    private ValidationHolder mMockValidationHolder;
    private int mCounter;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mCounter = 0;
        mMockValidationHolder = mock(ValidationHolder.class);
        mEmptyCustomValidationCallback = new CustomValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder) {
                mCounter += 1;
            }
        };
    }

    public void testExecute() {
        mEmptyCustomValidationCallback.execute(mMockValidationHolder);
        assertEquals(1, mCounter);
    }

}
