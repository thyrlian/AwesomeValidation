package com.basgeekball.awesomevalidation.utility;

import com.basgeekball.awesomevalidation.ValidationHolder;

import junit.framework.TestCase;

import java.util.regex.Matcher;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class ValidationCallbackTest extends TestCase {

    public void testExecute() {
        doNothing().when(mock(ValidationCallback.class)).execute(any(ValidationHolder.class), any(Matcher.class));
    }

}
