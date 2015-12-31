package com.basgeekball.awesomevalidation.validators;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.regex.Matcher;

import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.RANGE;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.REGEX;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.generate;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Validator.class, MockValidationHolderHelper.class})
public class ValidatorTest extends TestCase {

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
        ValidationHolder mockedValidationHolder = generate(REGEX, true);
        mValidator.mValidationHolderList.add(mockedValidationHolder);
        assertTrue(mValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailWithOnlyOneRegexValidationHolder() {
        ValidationHolder mockedValidationHolder = generate(REGEX, false);
        mValidator.mValidationHolderList.add(mockedValidationHolder);
        assertFalse(mValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsPassWithOnlyOneRangeValidationHolder() {
        ValidationHolder mockedValidationHolder = generate(RANGE, true);
        mValidator.mValidationHolderList.add(mockedValidationHolder);
        assertTrue(mValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailWithOnlyOneRangeValidationHolder() {
        ValidationHolder mockedValidationHolder = generate(RANGE, false);
        mValidator.mValidationHolderList.add(mockedValidationHolder);
        assertFalse(mValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsPassWithManyDifferentValidationHolders() {
        mValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX, true),
                generate(RANGE, true),
                generate(REGEX, true),
                generate(RANGE, true)
        ));
        assertTrue(mValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailDueToOneRegexFromManyDifferentValidationHolders() {
        mValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX, true),
                generate(RANGE, true),
                generate(REGEX, false),
                generate(RANGE, true),
                generate(REGEX, true),
                generate(RANGE, true)
        ));
        assertFalse(mValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailDueToOneRangeFromManyDifferentValidationHolders() {
        mValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX, true),
                generate(RANGE, true),
                generate(REGEX, true),
                generate(RANGE, false),
                generate(REGEX, true),
                generate(RANGE, true)
        ));
        assertFalse(mValidator.checkFields(mEmptyValidationCallback));
    }

}
