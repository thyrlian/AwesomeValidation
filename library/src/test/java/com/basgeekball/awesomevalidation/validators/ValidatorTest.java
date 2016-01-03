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

    private Validator mValidator = new Validator() {
        @Override
        public boolean trigger() {
            return false;
        }

        @Override
        public void halt() {
            // intentionally empty, no need to test here
        }
    };

    private ValidationCallback mEmptyValidationCallback = new ValidationCallback() {
        @Override
        public void execute(ValidationHolder validationHolder, Matcher matcher) {
            // intentionally empty, no need to test here
        }
    };

    private ValidationHolder mMockedValidationHolderRegexTypePass;
    private ValidationHolder mMockedValidationHolderRegexTypeFail;
    private ValidationHolder mMockedValidationHolderRangeTypePass;
    private ValidationHolder mMockedValidationHolderRangeTypeFail;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockedValidationHolderRegexTypePass = generate(REGEX, true);
        mMockedValidationHolderRegexTypeFail = generate(REGEX, false);
        mMockedValidationHolderRangeTypePass = generate(RANGE, true);
        mMockedValidationHolderRangeTypeFail = generate(RANGE, false);
    }

    public void testValidator() {
        assertTrue(mValidator.mValidationHolderList.isEmpty());
    }

    public void testCheckFieldsPassWithOnlyOneRegexValidationHolder() {
        mValidator.mValidationHolderList.add(mMockedValidationHolderRegexTypePass);
        assertTrue(mValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailWithOnlyOneRegexValidationHolder() {
        mValidator.mValidationHolderList.add(mMockedValidationHolderRegexTypeFail);
        assertFalse(mValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsPassWithOnlyOneRangeValidationHolder() {
        mValidator.mValidationHolderList.add(mMockedValidationHolderRangeTypePass);
        assertTrue(mValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailWithOnlyOneRangeValidationHolder() {
        mValidator.mValidationHolderList.add(mMockedValidationHolderRangeTypeFail);
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
