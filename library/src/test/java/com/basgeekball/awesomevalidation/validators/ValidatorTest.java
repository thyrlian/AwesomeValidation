package com.basgeekball.awesomevalidation.validators;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.expectation.PowerMockitoStubber;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.CONFIRMATION;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.RANGE;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.REGEX;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.generate;
import static org.mockito.Matchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Validator.class)
public class ValidatorTest extends TestCase {

    private Validator mSpiedValidator = PowerMockito.spy(new Validator() {
        @Override
        public boolean trigger() {
            return false;
        }

        @Override
        public void halt() {
            // intentionally empty, no need to test here
        }
    });

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
    private ValidationHolder mMockedValidationHolderConfirmationTypePass;
    private ValidationHolder mMockedValidationHolderConfirmationTypeFail;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockedValidationHolderRegexTypePass = generate(REGEX, true);
        mMockedValidationHolderRegexTypeFail = generate(REGEX, false);
        mMockedValidationHolderRangeTypePass = generate(RANGE, true);
        mMockedValidationHolderRangeTypeFail = generate(RANGE, false);
        mMockedValidationHolderConfirmationTypePass = generate(CONFIRMATION, true);
        mMockedValidationHolderConfirmationTypeFail = generate(CONFIRMATION, false);
    }

    private void mockPrivateMethods() {
        try {
            PowerMockito.doNothing().when(mSpiedValidator, "executeCallBack", any(ValidationCallback.class), any(ValidationHolder.class), any(Matcher.class));
            PowerMockito.doNothing().when(mSpiedValidator, "requestFocus", any(ValidationHolder.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mockCheckRegexTypeField(boolean returnValue) {
        try {
            PowerMockito.doReturn(returnValue).when(mSpiedValidator, "checkRegexTypeField", any(ValidationHolder.class), any(ValidationCallback.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mockCheckRangeTypeField(boolean returnValue) {
        try {
            PowerMockito.doReturn(returnValue).when(mSpiedValidator, "checkRangeTypeField", any(ValidationHolder.class), any(ValidationCallback.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mockCheckConfirmationTypeField(boolean returnValue) {
        try {
            PowerMockito.doReturn(returnValue).when(mSpiedValidator, "checkConfirmationTypeField", any(ValidationHolder.class), any(ValidationCallback.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mockCheckRegexTypeField(List<Boolean> returnValues) {
        try {
            PowerMockitoStubber stubber = PowerMockito.doReturn(returnValues.get(0));
            for (int i = 1; i < returnValues.size(); i++) {
                stubber = (PowerMockitoStubber) stubber.doReturn(returnValues.get(i));
            }
            stubber.when(mSpiedValidator, "checkRegexTypeField", any(ValidationHolder.class), any(ValidationCallback.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mockCheckRangeTypeField(List<Boolean> returnValues) {
        try {
            PowerMockitoStubber stubber = PowerMockito.doReturn(returnValues.get(0));
            for (int i = 1; i < returnValues.size(); i++) {
                stubber = (PowerMockitoStubber) stubber.doReturn(returnValues.get(i));
            }
            stubber.when(mSpiedValidator, "checkRangeTypeField", any(ValidationHolder.class), any(ValidationCallback.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mockCheckConfirmationTypeField(List<Boolean> returnValues) {
        try {
            PowerMockitoStubber stubber = PowerMockito.doReturn(returnValues.get(0));
            for (int i = 1; i < returnValues.size(); i++) {
                stubber = (PowerMockitoStubber) stubber.doReturn(returnValues.get(i));
            }
            stubber.when(mSpiedValidator, "checkConfirmationTypeField", any(ValidationHolder.class), any(ValidationCallback.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testValidator() {
        assertTrue(mSpiedValidator.mValidationHolderList.isEmpty());
    }

    public void testCheckFieldsPassWithOnlyOneRegexValidationHolder() {
        mockPrivateMethods();
        mockCheckRegexTypeField(true);
        mSpiedValidator.mValidationHolderList.add(mMockedValidationHolderRegexTypePass);
        assertTrue(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailWithOnlyOneRegexValidationHolder() {
        mockPrivateMethods();
        mockCheckRegexTypeField(false);
        mSpiedValidator.mValidationHolderList.add(mMockedValidationHolderRegexTypeFail);
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsPassWithOnlyOneRangeValidationHolder() {
        mockPrivateMethods();
        mockCheckRangeTypeField(true);
        mSpiedValidator.mValidationHolderList.add(mMockedValidationHolderRangeTypePass);
        assertTrue(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailWithOnlyOneRangeValidationHolder() {
        mockPrivateMethods();
        mockCheckRangeTypeField(false);
        mSpiedValidator.mValidationHolderList.add(mMockedValidationHolderRangeTypeFail);
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsPassWithOnlyOneConfirmationValidationHolder() {
        mockPrivateMethods();
        mockCheckConfirmationTypeField(true);
        mSpiedValidator.mValidationHolderList.add(mMockedValidationHolderConfirmationTypePass);
        assertTrue(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailWithOnlyOneConfirmationValidationHolder() {
        mockPrivateMethods();
        mockCheckConfirmationTypeField(false);
        mSpiedValidator.mValidationHolderList.add(mMockedValidationHolderConfirmationTypeFail);
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsPassWithManyDifferentValidationHolders() {
        mockPrivateMethods();
        mockCheckRegexTypeField(Arrays.asList(true, true));
        mockCheckRangeTypeField(Arrays.asList(true, true));
        mockCheckConfirmationTypeField(Arrays.asList(true, true));
        mSpiedValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX, true),
                generate(RANGE, true),
                generate(CONFIRMATION, true),
                generate(REGEX, true),
                generate(RANGE, true),
                generate(CONFIRMATION, true)
        ));
        assertTrue(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailDueToOneRegexFromManyDifferentValidationHolders() {
        mockPrivateMethods();
        mockCheckRegexTypeField(Arrays.asList(true, false, true));
        mockCheckRangeTypeField(Arrays.asList(true, true, true));
        mockCheckConfirmationTypeField(Arrays.asList(true, true, true));
        mSpiedValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX, true),
                generate(RANGE, true),
                generate(CONFIRMATION, true),
                generate(REGEX, false),
                generate(RANGE, true),
                generate(CONFIRMATION, true),
                generate(REGEX, true),
                generate(RANGE, true),
                generate(CONFIRMATION, true)
        ));
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailDueToOneRangeFromManyDifferentValidationHolders() {
        mockPrivateMethods();
        mockCheckRegexTypeField(Arrays.asList(true, true, true));
        mockCheckRangeTypeField(Arrays.asList(true, false, true));
        mockCheckConfirmationTypeField(Arrays.asList(true, true, true));
        mSpiedValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX, true),
                generate(RANGE, true),
                generate(CONFIRMATION, true),
                generate(REGEX, true),
                generate(RANGE, false),
                generate(CONFIRMATION, true),
                generate(REGEX, true),
                generate(RANGE, true),
                generate(CONFIRMATION, true)
        ));
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailDueToOneConfirmationFromManyDifferentValidationHolders() {
        mockPrivateMethods();
        mockCheckRegexTypeField(Arrays.asList(true, true, true));
        mockCheckRangeTypeField(Arrays.asList(true, true, true));
        mockCheckConfirmationTypeField(Arrays.asList(true, false, true));
        mSpiedValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX, true),
                generate(RANGE, true),
                generate(CONFIRMATION, true),
                generate(REGEX, true),
                generate(RANGE, true),
                generate(CONFIRMATION, false),
                generate(REGEX, true),
                generate(RANGE, true),
                generate(CONFIRMATION, true)
        ));
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

}
