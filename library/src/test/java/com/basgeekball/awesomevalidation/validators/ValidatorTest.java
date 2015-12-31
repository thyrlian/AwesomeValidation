package com.basgeekball.awesomevalidation.validators;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.model.NumericRange;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Validator.class)
public class ValidatorTest extends TestCase {

    private enum ValidationHolderType {
        REGEX,
        RANGE
    }

    private Validator mValidator;
    private ValidationCallback mEmptyValidationCallback = new ValidationCallback() {
        @Override
        public void execute(ValidationHolder validationHolder, Matcher matcher) {
        }
    };

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mValidator = new Validator() {
            @Override
            public boolean trigger() {
                return false;
            }

            @Override
            public void halt() {
            }
        };
    }

    public void testValidator() {
        assertTrue(mValidator.mValidationHolderList.isEmpty());
    }

    public void testCheckFieldsPassWithOnlyOneRegexValidationHolder() {
        ValidationHolder mockedValidationHolder = mockValidationHolder(ValidationHolderType.REGEX, true);
        mValidator.mValidationHolderList.add(mockedValidationHolder);
        assertTrue(mValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailWithOnlyOneRegexValidationHolder() {
        ValidationHolder mockedValidationHolder = mockValidationHolder(ValidationHolderType.REGEX, false);
        mValidator.mValidationHolderList.add(mockedValidationHolder);
        assertFalse(mValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsPassWithOnlyOneRangeValidationHolder() {
        ValidationHolder mockedValidationHolder = mockValidationHolder(ValidationHolderType.RANGE, true);
        mValidator.mValidationHolderList.add(mockedValidationHolder);
        assertTrue(mValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailWithOnlyOneRangeValidationHolder() {
        ValidationHolder mockedValidationHolder = mockValidationHolder(ValidationHolderType.RANGE, false);
        mValidator.mValidationHolderList.add(mockedValidationHolder);
        assertFalse(mValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsPassWithManyDifferentValidationHolders() {
        mValidator.mValidationHolderList.addAll(Arrays.asList(
                mockValidationHolder(ValidationHolderType.REGEX, true),
                mockValidationHolder(ValidationHolderType.RANGE, true),
                mockValidationHolder(ValidationHolderType.REGEX, true),
                mockValidationHolder(ValidationHolderType.RANGE, true)
        ));
        assertTrue(mValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailDueToOneRegexFromManyDifferentValidationHolders() {
        mValidator.mValidationHolderList.addAll(Arrays.asList(
                mockValidationHolder(ValidationHolderType.REGEX, true),
                mockValidationHolder(ValidationHolderType.RANGE, true),
                mockValidationHolder(ValidationHolderType.REGEX, false),
                mockValidationHolder(ValidationHolderType.RANGE, true),
                mockValidationHolder(ValidationHolderType.REGEX, true),
                mockValidationHolder(ValidationHolderType.RANGE, true)
        ));
        assertFalse(mValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailDueToOneRangeFromManyDifferentValidationHolders() {
        mValidator.mValidationHolderList.addAll(Arrays.asList(
                mockValidationHolder(ValidationHolderType.REGEX, true),
                mockValidationHolder(ValidationHolderType.RANGE, true),
                mockValidationHolder(ValidationHolderType.REGEX, true),
                mockValidationHolder(ValidationHolderType.RANGE, false),
                mockValidationHolder(ValidationHolderType.REGEX, true),
                mockValidationHolder(ValidationHolderType.RANGE, true)
        ));
        assertFalse(mValidator.checkFields(mEmptyValidationCallback));
    }

    private ValidationHolder mockValidationHolder(ValidationHolderType type, boolean validity) {
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
