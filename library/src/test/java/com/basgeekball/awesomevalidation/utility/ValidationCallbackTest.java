package com.basgeekball.awesomevalidation.utility;

import com.basgeekball.awesomevalidation.ValidationHolder;

import junit.framework.TestCase;

import org.powermock.api.mockito.PowerMockito;

import java.util.regex.Matcher;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ValidationCallbackTest extends TestCase {

    private ValidationCallback mMockValidationCallback;
    private ValidationHolder mMockValidationHolder;
    private Matcher mMockMatcher;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockValidationCallback = mock(ValidationCallback.class);
        mMockValidationHolder = mock(ValidationHolder.class);
        mMockMatcher = PowerMockito.mock(Matcher.class);
    }

    public void testExecute() {
        doNothing().when(mMockValidationCallback).execute(any(ValidationHolder.class), any(Matcher.class));
        mMockValidationCallback.execute(mMockValidationHolder, mMockMatcher);
        verify(mMockValidationCallback, times(1)).execute(mMockValidationHolder, mMockMatcher);
    }

}
