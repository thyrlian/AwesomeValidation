package com.basgeekball.awesomevalidation.validators;

import com.basgeekball.awesomevalidation.ValidationHolder;

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

    public static ValidationHolder generate(ValidationHolderType type) {
        ValidationHolder mockedValidationHolder = mock(ValidationHolder.class);
        if (type == ValidationHolderType.REGEX) {
            when(mockedValidationHolder.isRegexType()).thenReturn(true);
            when(mockedValidationHolder.isRangeType()).thenReturn(false);
            when(mockedValidationHolder.isConfirmationType()).thenReturn(false);
        } else if (type == ValidationHolderType.RANGE) {
            when(mockedValidationHolder.isRegexType()).thenReturn(false);
            when(mockedValidationHolder.isRangeType()).thenReturn(true);
            when(mockedValidationHolder.isConfirmationType()).thenReturn(false);
        } else if (type == ValidationHolderType.CONFIRMATION) {
            when(mockedValidationHolder.isRegexType()).thenReturn(false);
            when(mockedValidationHolder.isRangeType()).thenReturn(false);
            when(mockedValidationHolder.isConfirmationType()).thenReturn(true);
        }
        return mockedValidationHolder;
    }

}
