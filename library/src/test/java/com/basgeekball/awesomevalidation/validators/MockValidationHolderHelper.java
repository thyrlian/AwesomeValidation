package com.basgeekball.awesomevalidation.validators;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.model.NumericRange;

import org.powermock.api.mockito.PowerMockito;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockValidationHolderHelper {

    public enum ValidationHolderType {
        REGEX,
        RANGE
    }

    private MockValidationHolderHelper() {
        throw new UnsupportedOperationException();
    }

    public static ValidationHolder generate(ValidationHolderType type, boolean validity) {
        ValidationHolder mockedValidationHolder = mock(ValidationHolder.class, RETURNS_DEEP_STUBS);
        String mockedString = PowerMockito.mock(String.class);
        when(mockedValidationHolder.getText()).thenReturn(mockedString);
        if (type == ValidationHolderType.REGEX) {
            when(mockedValidationHolder.isRegexType()).thenReturn(true);
            when(mockedValidationHolder.isRangeType()).thenReturn(false);
            Pattern mockedPattern = PowerMockito.mock(Pattern.class);
            Matcher mockedMatcher = PowerMockito.mock(Matcher.class);
            when(mockedValidationHolder.getPattern()).thenReturn(mockedPattern);
            when(mockedPattern.matcher(mockedString)).thenReturn(mockedMatcher);
            when(mockedMatcher.matches()).thenReturn(validity);
        } else if (type == ValidationHolderType.RANGE) {
            when(mockedValidationHolder.isRegexType()).thenReturn(false);
            when(mockedValidationHolder.isRangeType()).thenReturn(true);
            NumericRange mockedNumericRange = mock(NumericRange.class);
            when(mockedValidationHolder.getNumericRange()).thenReturn(mockedNumericRange);
            when(mockedNumericRange.isValid(mockedString)).thenReturn(validity);
        }
        return mockedValidationHolder;
    }

}
