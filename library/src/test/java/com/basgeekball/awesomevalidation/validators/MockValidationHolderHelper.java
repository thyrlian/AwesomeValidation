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
        RANGE,
        CONFIRMATION
    }

    private MockValidationHolderHelper() {
        throw new UnsupportedOperationException();
    }

    public static ValidationHolder generate(ValidationHolderType type, boolean validity) {
        ValidationHolder mockedValidationHolder = mock(ValidationHolder.class, RETURNS_DEEP_STUBS);
        String aString = "One";
        String anotherString = "Another";
        when(mockedValidationHolder.getText()).thenReturn(aString);
        if (type == ValidationHolderType.REGEX) {
            when(mockedValidationHolder.isRegexType()).thenReturn(true);
            when(mockedValidationHolder.isRangeType()).thenReturn(false);
            when(mockedValidationHolder.isConfirmationType()).thenReturn(false);
            Pattern mockedPattern = PowerMockito.mock(Pattern.class);
            Matcher mockedMatcher = PowerMockito.mock(Matcher.class);
            when(mockedValidationHolder.getPattern()).thenReturn(mockedPattern);
            when(mockedPattern.matcher(aString)).thenReturn(mockedMatcher);
            when(mockedMatcher.matches()).thenReturn(validity);
        } else if (type == ValidationHolderType.RANGE) {
            when(mockedValidationHolder.isRegexType()).thenReturn(false);
            when(mockedValidationHolder.isRangeType()).thenReturn(true);
            when(mockedValidationHolder.isConfirmationType()).thenReturn(false);
            NumericRange mockedNumericRange = mock(NumericRange.class);
            when(mockedValidationHolder.getNumericRange()).thenReturn(mockedNumericRange);
            when(mockedNumericRange.isValid(aString)).thenReturn(validity);
        } else if (type == ValidationHolderType.CONFIRMATION) {
            when(mockedValidationHolder.isRegexType()).thenReturn(false);
            when(mockedValidationHolder.isRangeType()).thenReturn(false);
            when(mockedValidationHolder.isConfirmationType()).thenReturn(true);
            String confirmationText = validity ? aString : anotherString;
            when(mockedValidationHolder.getConfirmationText()).thenReturn(confirmationText);
        }
        return mockedValidationHolder;
    }

}
