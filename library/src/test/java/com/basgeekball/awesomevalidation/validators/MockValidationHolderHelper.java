package com.basgeekball.awesomevalidation.validators;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidationCallback;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockValidationHolderHelper {

    public enum ValidationHolderType {
        REGEX,
        RANGE,
        CONFIRMATION,
        SIMPLE_CUSTOM,
        CUSTOM
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
            when(mockValidationHolder.isSimpleCustomType()).thenReturn(false);
            when(mockValidationHolder.isCustomType()).thenReturn(false);
        } else if (type == ValidationHolderType.RANGE) {
            when(mockValidationHolder.isRegexType()).thenReturn(false);
            when(mockValidationHolder.isRangeType()).thenReturn(true);
            when(mockValidationHolder.isConfirmationType()).thenReturn(false);
            when(mockValidationHolder.isSimpleCustomType()).thenReturn(false);
            when(mockValidationHolder.isCustomType()).thenReturn(false);
        } else if (type == ValidationHolderType.CONFIRMATION) {
            when(mockValidationHolder.isRegexType()).thenReturn(false);
            when(mockValidationHolder.isRangeType()).thenReturn(false);
            when(mockValidationHolder.isConfirmationType()).thenReturn(true);
            when(mockValidationHolder.isSimpleCustomType()).thenReturn(false);
            when(mockValidationHolder.isCustomType()).thenReturn(false);
        } else if (type == ValidationHolderType.SIMPLE_CUSTOM) {
            when(mockValidationHolder.isRegexType()).thenReturn(false);
            when(mockValidationHolder.isRangeType()).thenReturn(false);
            when(mockValidationHolder.isConfirmationType()).thenReturn(false);
            when(mockValidationHolder.isSimpleCustomType()).thenReturn(true);
            when(mockValidationHolder.isCustomType()).thenReturn(false);
        } else if (type == ValidationHolderType.CUSTOM) {
            when(mockValidationHolder.isRegexType()).thenReturn(false);
            when(mockValidationHolder.isRangeType()).thenReturn(false);
            when(mockValidationHolder.isConfirmationType()).thenReturn(false);
            when(mockValidationHolder.isSimpleCustomType()).thenReturn(false);
            when(mockValidationHolder.isCustomType()).thenReturn(true);
            when(mockValidationHolder.getCustomValidationCallback()).thenReturn(mock(CustomValidationCallback.class));
        }
        when(mockValidationHolder.isVisible()).thenReturn(visibility);
        return mockValidationHolder;
    }

    public static ValidationHolder generate(ValidationHolderType type) {
        return generate(type, true);
    }

}
