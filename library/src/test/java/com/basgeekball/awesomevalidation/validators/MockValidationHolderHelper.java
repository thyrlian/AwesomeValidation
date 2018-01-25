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

    public static ValidationHolder generate(ValidationHolderType type, boolean visibility) {
        ValidationHolder mockValidationHolder = mock(ValidationHolder.class);
        if (type == ValidationHolderType.REGEX) {
            when(mockValidationHolder.isRegexType()).thenReturn(true);
            when(mockValidationHolder.isRangeType()).thenReturn(false);
            when(mockValidationHolder.isConfirmationType()).thenReturn(false);
        } else if (type == ValidationHolderType.RANGE) {
            when(mockValidationHolder.isRegexType()).thenReturn(false);
            when(mockValidationHolder.isRangeType()).thenReturn(true);
            when(mockValidationHolder.isConfirmationType()).thenReturn(false);
        } else if (type == ValidationHolderType.CONFIRMATION) {
            when(mockValidationHolder.isRegexType()).thenReturn(false);
            when(mockValidationHolder.isRangeType()).thenReturn(false);
            when(mockValidationHolder.isConfirmationType()).thenReturn(true);
        }
        when(mockValidationHolder.isVisible()).thenReturn(visibility);
        return mockValidationHolder;
    }

    public static ValidationHolder generate(ValidationHolderType type) {
        return generate(type, true);
    }

}
