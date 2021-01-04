package com.basgeekball.awesomevalidation.validators;

import android.app.Activity;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.model.NumericRange;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;
import com.basgeekball.awesomevalidation.utility.custom.CustomErrorReset;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidation;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidationCallback;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.google.android.material.textfield.TextInputLayout;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.CONFIRMATION;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.CUSTOM;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.RANGE;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.REGEX;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.SIMPLE_CUSTOM;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.generate;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Validator.class, Pattern.class})
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

    private CustomValidationCallback mEmptyCustomValidationCallback = new CustomValidationCallback() {
        @Override
        public void execute(ValidationHolder validationHolder) {
            // intentionally empty, no need to test here
        }
    };

    private void mockPrivateMethods() {
        try {
            PowerMockito.doNothing().when(mSpiedValidator, "executeCallback", any(ValidationCallback.class), any(ValidationHolder.class), any(Matcher.class));
            PowerMockito.doNothing().when(mSpiedValidator, "requestFocus", any(ValidationHolder.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mockCheckSomeCertainTypeField(String methodName, Boolean... returnValues) {
        try {
            ArrayList<Boolean> booleans = new ArrayList<>(Arrays.asList(returnValues));
            Boolean firstBoolean = booleans.remove(0);
            Boolean[] otherBooleans = booleans.toArray(new Boolean[booleans.size()]);
            // WORKAROUND: pass firstBoolean, otherBooleans instead of single returnValues
            // otherwise it would cause problem with varargs for doReturn(...)
            // org.mockito.exceptions.misusing.WrongTypeOfReturnValue:
            // Boolean[] cannot be returned by checkXxxTypeField()
            // checkXxxTypeField() should return boolean
            Class<?> typeOfCallback = methodName.equals("checkCustomTypeField") ? CustomValidationCallback.class : ValidationCallback.class;
            PowerMockito.doReturn(firstBoolean, otherBooleans).when(mSpiedValidator, methodName, ArgumentMatchers.any(ValidationHolder.class), ArgumentMatchers.any(typeOfCallback));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mockCheckRegexTypeField(Boolean... returnValues) {
        mockCheckSomeCertainTypeField("checkRegexTypeField", returnValues);
    }

    private void mockCheckRangeTypeField(Boolean... returnValues) {
        mockCheckSomeCertainTypeField("checkRangeTypeField", returnValues);
    }

    private void mockCheckConfirmationTypeField(Boolean... returnValues) {
        mockCheckSomeCertainTypeField("checkConfirmationTypeField", returnValues);
    }

    private void mockCheckSimpleCustomTypeField(Boolean... returnValues) {
        mockCheckSomeCertainTypeField("checkSimpleCustomTypeField", returnValues);
    }

    private void mockCheckCustomTypeField(Boolean... returnValues) {
        mockCheckSomeCertainTypeField("checkCustomTypeField", returnValues);
    }

    public void testValidator() {
        assertTrue(mSpiedValidator.mValidationHolderList.isEmpty());
    }

    public void testSetEditTextWithActivityAndRegex() {
        Activity mockActivity = mock(Activity.class, RETURNS_DEEP_STUBS);
        int viewId = 1;
        String regex = "OK";
        int errMsgId = 9;
        String errMsg = "Error";
        EditText mockEditText = mock(EditText.class);
        Pattern mockPattern = PowerMockito.mock(Pattern.class);
        PowerMock.mockStatic(Pattern.class);
        EasyMock.expect(Pattern.compile(regex)).andReturn(mockPattern);
        PowerMock.replay(Pattern.class);
        when(mockActivity.findViewById(eq(viewId))).thenReturn(mockEditText);
        when(mockActivity.getResources().getString(eq(errMsgId))).thenReturn(errMsg);
        mSpiedValidator.set(mockActivity, viewId, regex, errMsgId);
        assertEquals(1, mSpiedValidator.mValidationHolderList.size());
        ValidationHolder validationHolder = mSpiedValidator.mValidationHolderList.get(0);
        assertEquals(mockEditText, validationHolder.getEditText());
        assertEquals(mockPattern, validationHolder.getPattern());
        assertEquals(errMsg, validationHolder.getErrMsg());
    }

    public void testSetTextInputLayoutWithActivityAndRegex() {
        Activity mockActivity = mock(Activity.class, RETURNS_DEEP_STUBS);
        int viewId = 1;
        String regex = "OK";
        int errMsgId = 9;
        String errMsg = "Error";
        TextInputLayout mockTextInputLayout = mock(TextInputLayout.class);
        Pattern mockPattern = PowerMockito.mock(Pattern.class);
        PowerMock.mockStatic(Pattern.class);
        EasyMock.expect(Pattern.compile(regex)).andReturn(mockPattern);
        PowerMock.replay(Pattern.class);
        when(mockActivity.findViewById(eq(viewId))).thenReturn(mockTextInputLayout);
        when(mockActivity.getResources().getString(eq(errMsgId))).thenReturn(errMsg);
        mSpiedValidator.set(mockActivity, viewId, regex, errMsgId);
        assertEquals(1, mSpiedValidator.mValidationHolderList.size());
        ValidationHolder validationHolder = mSpiedValidator.mValidationHolderList.get(0);
        assertEquals(mockTextInputLayout, validationHolder.getTextInputLayout());
        assertEquals(mockPattern, validationHolder.getPattern());
        assertEquals(errMsg, validationHolder.getErrMsg());
    }

    public void testSetEditTextWithActivityAndPattern() {
        Activity mockActivity = mock(Activity.class, RETURNS_DEEP_STUBS);
        int viewId = 1;
        Pattern mockPattern = PowerMockito.mock(Pattern.class);
        int errMsgId = 9;
        String errMsg = "Error";
        EditText mockEditText = mock(EditText.class);
        when(mockActivity.findViewById(eq(viewId))).thenReturn(mockEditText);
        when(mockActivity.getResources().getString(eq(errMsgId))).thenReturn(errMsg);
        mSpiedValidator.set(mockActivity, viewId, mockPattern, errMsgId);
        assertEquals(1, mSpiedValidator.mValidationHolderList.size());
        ValidationHolder validationHolder = mSpiedValidator.mValidationHolderList.get(0);
        assertEquals(mockEditText, validationHolder.getEditText());
        assertEquals(mockPattern, validationHolder.getPattern());
        assertEquals(errMsg, validationHolder.getErrMsg());
    }

    public void testSetTextInputLayoutWithActivityAndPattern() {
        Activity mockActivity = mock(Activity.class, RETURNS_DEEP_STUBS);
        int viewId = 1;
        Pattern mockPattern = PowerMockito.mock(Pattern.class);
        int errMsgId = 9;
        String errMsg = "Error";
        TextInputLayout mockTextInputLayout = mock(TextInputLayout.class);
        when(mockActivity.findViewById(eq(viewId))).thenReturn(mockTextInputLayout);
        when(mockActivity.getResources().getString(eq(errMsgId))).thenReturn(errMsg);
        mSpiedValidator.set(mockActivity, viewId, mockPattern, errMsgId);
        assertEquals(1, mSpiedValidator.mValidationHolderList.size());
        ValidationHolder validationHolder = mSpiedValidator.mValidationHolderList.get(0);
        assertEquals(mockTextInputLayout, validationHolder.getTextInputLayout());
        assertEquals(mockPattern, validationHolder.getPattern());
        assertEquals(errMsg, validationHolder.getErrMsg());
    }

    public void testSetEditTextWithActivityAndNumericRange() {
        Activity mockActivity = mock(Activity.class, RETURNS_DEEP_STUBS);
        int viewId = 1;
        NumericRange mockNumericRange = mock(NumericRange.class);
        int errMsgId = 9;
        String errMsg = "Error";
        EditText mockEditText = mock(EditText.class);
        when(mockActivity.findViewById(eq(viewId))).thenReturn(mockEditText);
        when(mockActivity.getResources().getString(eq(errMsgId))).thenReturn(errMsg);
        mSpiedValidator.set(mockActivity, viewId, mockNumericRange, errMsgId);
        assertEquals(1, mSpiedValidator.mValidationHolderList.size());
        ValidationHolder validationHolder = mSpiedValidator.mValidationHolderList.get(0);
        assertEquals(mockEditText, validationHolder.getEditText());
        assertEquals(mockNumericRange, validationHolder.getNumericRange());
        assertEquals(errMsg, validationHolder.getErrMsg());
    }

    public void testSetTextInputLayoutWithActivityAndNumericRange() {
        Activity mockActivity = mock(Activity.class, RETURNS_DEEP_STUBS);
        int viewId = 1;
        NumericRange mockNumericRange = mock(NumericRange.class);
        int errMsgId = 9;
        String errMsg = "Error";
        TextInputLayout mockTextInputLayout = mock(TextInputLayout.class);
        when(mockActivity.findViewById(eq(viewId))).thenReturn(mockTextInputLayout);
        when(mockActivity.getResources().getString(eq(errMsgId))).thenReturn(errMsg);
        mSpiedValidator.set(mockActivity, viewId, mockNumericRange, errMsgId);
        assertEquals(1, mSpiedValidator.mValidationHolderList.size());
        ValidationHolder validationHolder = mSpiedValidator.mValidationHolderList.get(0);
        assertEquals(mockTextInputLayout, validationHolder.getTextInputLayout());
        assertEquals(mockNumericRange, validationHolder.getNumericRange());
        assertEquals(errMsg, validationHolder.getErrMsg());
    }

    public void testSetEditTextWithActivityAndConfirmation() {
        Activity mockActivity = mock(Activity.class, RETURNS_DEEP_STUBS);
        int confirmationViewId = 0;
        int viewId = 1;
        int errMsgId = 9;
        String errMsg = "Error";
        EditText mockConfirmationEditText = mock(EditText.class);
        EditText mockEditText = mock(EditText.class);
        when(mockActivity.findViewById(eq(confirmationViewId))).thenReturn(mockConfirmationEditText);
        when(mockActivity.findViewById(eq(viewId))).thenReturn(mockEditText);
        when(mockActivity.getResources().getString(eq(errMsgId))).thenReturn(errMsg);
        mSpiedValidator.set(mockActivity, confirmationViewId, viewId, errMsgId);
        assertEquals(1, mSpiedValidator.mValidationHolderList.size());
        ValidationHolder validationHolder = mSpiedValidator.mValidationHolderList.get(0);
        assertEquals(mockConfirmationEditText, validationHolder.getEditText());
        assertEquals(errMsg, validationHolder.getErrMsg());
    }

    public void testSetTextInputLayoutWithActivityAndConfirmation() {
        Activity mockActivity = mock(Activity.class, RETURNS_DEEP_STUBS);
        int confirmationViewId = 0;
        int viewId = 1;
        int errMsgId = 9;
        String errMsg = "Error";
        TextInputLayout mockConfirmationTextInputLayout = mock(TextInputLayout.class);
        TextInputLayout mockTextInputLayout = mock(TextInputLayout.class);
        when(mockActivity.findViewById(eq(confirmationViewId))).thenReturn(mockConfirmationTextInputLayout);
        when(mockActivity.findViewById(eq(viewId))).thenReturn(mockTextInputLayout);
        when(mockActivity.getResources().getString(eq(errMsgId))).thenReturn(errMsg);
        mSpiedValidator.set(mockActivity, confirmationViewId, viewId, errMsgId);
        assertEquals(1, mSpiedValidator.mValidationHolderList.size());
        ValidationHolder validationHolder = mSpiedValidator.mValidationHolderList.get(0);
        assertEquals(mockConfirmationTextInputLayout, validationHolder.getTextInputLayout());
        assertEquals(errMsg, validationHolder.getErrMsg());
    }

    public void testSetEditTextWithActivityAndSimpleCustomValidation() {
        Activity mockActivity = mock(Activity.class, RETURNS_DEEP_STUBS);
        int viewId = 1;
        int errMsgId = 9;
        String errMsg = "Error";
        EditText mockEditText = mock(EditText.class);
        when(mockActivity.findViewById(eq(viewId))).thenReturn(mockEditText);
        when(mockActivity.getResources().getString(eq(errMsgId))).thenReturn(errMsg);
        SimpleCustomValidation simpleCustomValidation = new SimpleCustomValidation() {
            @Override
            public boolean compare(String input) {
                return false;
            }
        };
        mSpiedValidator.set(mockActivity, viewId, simpleCustomValidation, errMsgId);
        assertEquals(1, mSpiedValidator.mValidationHolderList.size());
        ValidationHolder validationHolder = mSpiedValidator.mValidationHolderList.get(0);
        assertEquals(mockEditText, validationHolder.getEditText());
        assertEquals(simpleCustomValidation, validationHolder.getSimpleCustomValidation());
        assertEquals(errMsg, validationHolder.getErrMsg());
    }

    public void testSetTextInputLayoutWithActivityAndSimpleCustomValidation() {
        Activity mockActivity = mock(Activity.class, RETURNS_DEEP_STUBS);
        int viewId = 1;
        int errMsgId = 9;
        String errMsg = "Error";
        TextInputLayout mockTextInputLayout = mock(TextInputLayout.class);
        when(mockActivity.findViewById(eq(viewId))).thenReturn(mockTextInputLayout);
        when(mockActivity.getResources().getString(eq(errMsgId))).thenReturn(errMsg);
        SimpleCustomValidation simpleCustomValidation = new SimpleCustomValidation() {
            @Override
            public boolean compare(String input) {
                return false;
            }
        };
        mSpiedValidator.set(mockActivity, viewId, simpleCustomValidation, errMsgId);
        assertEquals(1, mSpiedValidator.mValidationHolderList.size());
        ValidationHolder validationHolder = mSpiedValidator.mValidationHolderList.get(0);
        assertEquals(mockTextInputLayout, validationHolder.getTextInputLayout());
        assertEquals(simpleCustomValidation, validationHolder.getSimpleCustomValidation());
        assertEquals(errMsg, validationHolder.getErrMsg());
    }

    public void testSetViewWithActivityAndCustomValidation() {
        Activity mockActivity = mock(Activity.class, RETURNS_DEEP_STUBS);
        int viewId = 1;
        int errMsgId = 9;
        String errMsg = "Error";
        View mockView = mock(View.class);
        when(mockActivity.findViewById(eq(viewId))).thenReturn(mockView);
        when(mockActivity.getResources().getString(eq(errMsgId))).thenReturn(errMsg);
        CustomValidation customValidation = new CustomValidation() {
            @Override
            public boolean compare(ValidationHolder validationHolder) {
                return false;
            }
        };
        CustomValidationCallback customValidationCallback = new CustomValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder) {
                // do nothing
            }
        };
        CustomErrorReset customErrorReset = new CustomErrorReset() {
            @Override
            public void reset(ValidationHolder validationHolder) {
                // do nothing
            }
        };
        mSpiedValidator.set(mockActivity, viewId, customValidation, customValidationCallback, customErrorReset, errMsgId);
        assertEquals(1, mSpiedValidator.mValidationHolderList.size());
        ValidationHolder validationHolder = mSpiedValidator.mValidationHolderList.get(0);
        assertEquals(mockView, validationHolder.getView());
        assertEquals(customValidation, validationHolder.getCustomValidation());
        assertEquals(customValidationCallback, validationHolder.getCustomValidationCallback());
        assertEquals(errMsg, validationHolder.getErrMsg());
    }

    public void testCheckFieldsPassWithOnlyOneRegexValidationHolder() {
        mockPrivateMethods();
        mockCheckRegexTypeField(true);
        mSpiedValidator.mValidationHolderList.add(generate(REGEX));
        assertTrue(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailWithOnlyOneRegexValidationHolder() {
        mockPrivateMethods();
        mockCheckRegexTypeField(false);
        mSpiedValidator.mValidationHolderList.add(generate(REGEX));
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsPassWithOnlyOneRangeValidationHolder() {
        mockPrivateMethods();
        mockCheckRangeTypeField(true);
        mSpiedValidator.mValidationHolderList.add(generate(RANGE));
        assertTrue(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailWithOnlyOneRangeValidationHolder() {
        mockPrivateMethods();
        mockCheckRangeTypeField(false);
        mSpiedValidator.mValidationHolderList.add(generate(RANGE));
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsPassWithOnlyOneConfirmationValidationHolder() {
        mockPrivateMethods();
        mockCheckConfirmationTypeField(true);
        mSpiedValidator.mValidationHolderList.add(generate(CONFIRMATION));
        assertTrue(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailWithOnlyOneConfirmationValidationHolder() {
        mockPrivateMethods();
        mockCheckConfirmationTypeField(false);
        mSpiedValidator.mValidationHolderList.add(generate(CONFIRMATION));
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsPassWithOnlyOneSimpleCustomValidationHolder() {
        mockPrivateMethods();
        mockCheckSimpleCustomTypeField(true);
        mSpiedValidator.mValidationHolderList.add(generate(SIMPLE_CUSTOM));
        assertTrue(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailWithOnlyOneSimpleCustomValidationHolder() {
        mockPrivateMethods();
        mockCheckSimpleCustomTypeField(false);
        mSpiedValidator.mValidationHolderList.add(generate(SIMPLE_CUSTOM));
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsPassWithOnlyOneCustomValidationHolder() {
        mockPrivateMethods();
        mockCheckCustomTypeField(true);
        mSpiedValidator.mValidationHolderList.add(generate(CUSTOM));
        assertTrue(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailWithOnlyOneCustomValidationHolder() {
        mockPrivateMethods();
        mockCheckCustomTypeField(false);
        mSpiedValidator.mValidationHolderList.add(generate(CUSTOM));
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsPassWithManyDifferentValidationHolders() {
        mockPrivateMethods();
        mockCheckRegexTypeField(true, true);
        mockCheckRangeTypeField(true, true);
        mockCheckConfirmationTypeField(true, true);
        mockCheckSimpleCustomTypeField(true, true);
        mockCheckCustomTypeField(true, true);
        mSpiedValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(SIMPLE_CUSTOM),
                generate(CUSTOM),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(SIMPLE_CUSTOM),
                generate(CUSTOM)
        ));
        assertTrue(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailDueToOneRegexFromManyDifferentValidationHolders() {
        mockPrivateMethods();
        mockCheckRegexTypeField(true, false, true);
        mockCheckRangeTypeField(true, true, true);
        mockCheckConfirmationTypeField(true, true, true);
        mockCheckSimpleCustomTypeField(true, true, true);
        mockCheckCustomTypeField(true, true, true);
        mSpiedValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(SIMPLE_CUSTOM),
                generate(CUSTOM),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(SIMPLE_CUSTOM),
                generate(CUSTOM),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(SIMPLE_CUSTOM),
                generate(CUSTOM)
        ));
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailDueToOneRangeFromManyDifferentValidationHolders() {
        mockPrivateMethods();
        mockCheckRegexTypeField(true, true, true);
        mockCheckRangeTypeField(true, false, true);
        mockCheckConfirmationTypeField(true, true, true);
        mockCheckSimpleCustomTypeField(true, true, true);
        mockCheckCustomTypeField(true, true, true);
        mSpiedValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(SIMPLE_CUSTOM),
                generate(CUSTOM),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(SIMPLE_CUSTOM),
                generate(CUSTOM),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(SIMPLE_CUSTOM),
                generate(CUSTOM)
        ));
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailDueToOneConfirmationFromManyDifferentValidationHolders() {
        mockPrivateMethods();
        mockCheckRegexTypeField(true, true, true);
        mockCheckRangeTypeField(true, true, true);
        mockCheckConfirmationTypeField(true, false, true);
        mockCheckSimpleCustomTypeField(true, true, true);
        mockCheckCustomTypeField(true, true, true);
        mSpiedValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(SIMPLE_CUSTOM),
                generate(CUSTOM),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(SIMPLE_CUSTOM),
                generate(CUSTOM),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(SIMPLE_CUSTOM),
                generate(CUSTOM)
        ));
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailDueToOneSimpleCustomFromManyDifferentValidationHolders() {
        mockPrivateMethods();
        mockCheckRegexTypeField(true, true, true);
        mockCheckRangeTypeField(true, true, true);
        mockCheckConfirmationTypeField(true, true, true);
        mockCheckSimpleCustomTypeField(true, false, true);
        mockCheckCustomTypeField(true, true, true);
        mSpiedValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(SIMPLE_CUSTOM),
                generate(CUSTOM),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(SIMPLE_CUSTOM),
                generate(CUSTOM),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(SIMPLE_CUSTOM),
                generate(CUSTOM)
        ));
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailDueToOneCustomFromManyDifferentValidationHolders() {
        mockPrivateMethods();
        mockCheckRegexTypeField(true, true, true);
        mockCheckRangeTypeField(true, true, true);
        mockCheckConfirmationTypeField(true, true, true);
        mockCheckSimpleCustomTypeField(true, true, true);
        mockCheckCustomTypeField(true, false, true);
        mSpiedValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(SIMPLE_CUSTOM),
                generate(CUSTOM),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(SIMPLE_CUSTOM),
                generate(CUSTOM),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(SIMPLE_CUSTOM),
                generate(CUSTOM)
        ));
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckRegexTypeFieldPass() throws Exception {
        ValidationHolder mockValidationHolder = generate(REGEX);
        Pattern mockPattern = PowerMockito.mock(Pattern.class);
        String mockString = PowerMockito.mock(String.class);
        Matcher mockMatcher = PowerMockito.mock(Matcher.class);
        when(mockValidationHolder.getPattern()).thenReturn(mockPattern);
        when(mockValidationHolder.getText()).thenReturn(mockString);
        when(mockPattern.matcher(mockString)).thenReturn(mockMatcher);
        when(mockMatcher.matches()).thenReturn(true);
        assertTrue((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkRegexTypeField", mockValidationHolder, mEmptyValidationCallback));
        verifyPrivate(mSpiedValidator, never()).invoke("executeCallback", any(ValidationCallback.class), any(ValidationHolder.class), any(Matcher.class));
    }

    public void testCheckRegexTypeFieldFail() throws Exception {
        ValidationHolder mockValidationHolder = generate(REGEX);
        Pattern mockPattern = PowerMockito.mock(Pattern.class);
        String mockString = PowerMockito.mock(String.class);
        Matcher mockMatcher = PowerMockito.mock(Matcher.class);
        when(mockValidationHolder.getPattern()).thenReturn(mockPattern);
        when(mockValidationHolder.getText()).thenReturn(mockString);
        when(mockPattern.matcher(mockString)).thenReturn(mockMatcher);
        when(mockMatcher.matches()).thenReturn(false);
        PowerMockito.doNothing().when(mSpiedValidator, "executeCallback", any(ValidationCallback.class), any(ValidationHolder.class), any(Matcher.class));
        assertFalse((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkRegexTypeField", mockValidationHolder, mEmptyValidationCallback));
        verifyPrivate(mSpiedValidator, times(1)).invoke("executeCallback", mEmptyValidationCallback, mockValidationHolder, mockMatcher);
    }

    public void testCheckRangeTypeFieldPass() throws Exception {
        ValidationHolder mockValidationHolder = generate(RANGE);
        NumericRange mockNumericRange = mock(NumericRange.class);
        String mockString = PowerMockito.mock(String.class);
        when(mockValidationHolder.getText()).thenReturn(mockString);
        when(mockValidationHolder.getNumericRange()).thenReturn(mockNumericRange);
        when(mockNumericRange.isValid(mockString)).thenReturn(true);
        assertTrue((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkRangeTypeField", mockValidationHolder, mEmptyValidationCallback));
        verifyPrivate(mSpiedValidator, never()).invoke("executeCallback", any(ValidationCallback.class), any(ValidationHolder.class), any(Matcher.class));
    }

    public void testCheckRangeTypeFieldFail() throws Exception {
        ValidationHolder mockValidationHolder = generate(RANGE);
        NumericRange mockNumericRange = mock(NumericRange.class);
        String mockString = PowerMockito.mock(String.class);
        when(mockValidationHolder.getText()).thenReturn(mockString);
        when(mockValidationHolder.getNumericRange()).thenReturn(mockNumericRange);
        when(mockNumericRange.isValid(mockString)).thenReturn(false);
        Matcher mockMatcher = PowerMockito.mock(Matcher.class);
        Pattern mockPattern = PowerMockito.mock(Pattern.class);
        PowerMock.mockStatic(Pattern.class);
        EasyMock.expect(Pattern.compile("±*~=")).andReturn(mockPattern);
        PowerMock.replay(Pattern.class);
        when(mockPattern.matcher(mockString)).thenReturn(mockMatcher);
        PowerMockito.doNothing().when(mSpiedValidator, "executeCallback", any(ValidationCallback.class), any(ValidationHolder.class), any(Matcher.class));
        assertFalse((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkRangeTypeField", mockValidationHolder, mEmptyValidationCallback));
        verifyPrivate(mSpiedValidator, times(1)).invoke("executeCallback", mEmptyValidationCallback, mockValidationHolder, mockMatcher);
    }

    public void testCheckRangeTypeFieldFailDueToException() throws Exception {
        ValidationHolder mockValidationHolder = generate(RANGE);
        NumericRange mockNumericRange = mock(NumericRange.class);
        String mockString = PowerMockito.mock(String.class);
        when(mockValidationHolder.getText()).thenReturn(mockString);
        when(mockValidationHolder.getNumericRange()).thenReturn(mockNumericRange);
        doThrow(NumberFormatException.class).when(mockNumericRange).isValid(mockString);
        Matcher mockMatcher = PowerMockito.mock(Matcher.class);
        Pattern mockPattern = PowerMockito.mock(Pattern.class);
        PowerMock.mockStatic(Pattern.class);
        EasyMock.expect(Pattern.compile("±*~=")).andReturn(mockPattern);
        PowerMock.replay(Pattern.class);
        when(mockPattern.matcher(mockString)).thenReturn(mockMatcher);
        PowerMockito.doNothing().when(mSpiedValidator, "executeCallback", any(ValidationCallback.class), any(ValidationHolder.class), any(Matcher.class));
        assertFalse((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkRangeTypeField", mockValidationHolder, mEmptyValidationCallback));
        verifyPrivate(mSpiedValidator, times(1)).invoke("executeCallback", mEmptyValidationCallback, mockValidationHolder, mockMatcher);
    }

    public void testCheckConfirmationTypeFieldPass() throws Exception {
        ValidationHolder mockValidationHolder = generate(CONFIRMATION);
        String mockStringA = "aaa";
        String mockStringB = "aaa";
        when(mockValidationHolder.getText()).thenReturn(mockStringA);
        when(mockValidationHolder.getConfirmationText()).thenReturn(mockStringB);
        assertTrue((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkConfirmationTypeField", mockValidationHolder, mEmptyValidationCallback));
        verifyPrivate(mSpiedValidator, never()).invoke("executeCallback", any(ValidationCallback.class), any(ValidationHolder.class), any(Matcher.class));
    }

    public void testCheckConfirmationTypeFieldFail() throws Exception {
        mockPrivateMethods();
        ValidationHolder mockValidationHolder = generate(CONFIRMATION);
        String mockStringA = "aaa";
        String mockStringB = "bbb";
        when(mockValidationHolder.getText()).thenReturn(mockStringA);
        when(mockValidationHolder.getConfirmationText()).thenReturn(mockStringB);
        PowerMockito.doNothing().when(mSpiedValidator, "executeCallback", any(ValidationCallback.class), any(ValidationHolder.class), any(Matcher.class));
        assertFalse((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkConfirmationTypeField", mockValidationHolder, mEmptyValidationCallback));
        verifyPrivate(mSpiedValidator, times(1)).invoke("executeCallback", mEmptyValidationCallback, mockValidationHolder, null);
    }

    public void testCheckSimpleCustomTypeFieldPass() throws Exception {
        mockPrivateMethods();
        ValidationHolder mockValidationHolder = generate(SIMPLE_CUSTOM);
        String mockString = "aaa";
        SimpleCustomValidation mockSimpleCustomValidation = mock(SimpleCustomValidation.class);
        when(mockValidationHolder.getSimpleCustomValidation()).thenReturn(mockSimpleCustomValidation);
        when(mockValidationHolder.getText()).thenReturn(mockString);
        when(mockSimpleCustomValidation.compare(mockString)).thenReturn(true);
        assertTrue((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkSimpleCustomTypeField", mockValidationHolder, mEmptyValidationCallback));
        verifyPrivate(mSpiedValidator, never()).invoke("executeCallback", mEmptyValidationCallback, mockValidationHolder, null);
    }

    public void testCheckSimpleCustomTypeFieldFail() throws Exception {
        mockPrivateMethods();
        ValidationHolder mockValidationHolder = generate(SIMPLE_CUSTOM);
        String mockString = "aaa";
        SimpleCustomValidation mockSimpleCustomValidation = mock(SimpleCustomValidation.class);
        when(mockValidationHolder.getSimpleCustomValidation()).thenReturn(mockSimpleCustomValidation);
        when(mockValidationHolder.getText()).thenReturn(mockString);
        when(mockSimpleCustomValidation.compare(mockString)).thenReturn(false);
        assertFalse((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkSimpleCustomTypeField", mockValidationHolder, mEmptyValidationCallback));
        verifyPrivate(mSpiedValidator, times(1)).invoke("executeCallback", mEmptyValidationCallback, mockValidationHolder, null);
    }

    public void testCheckCustomTypeFieldPass() throws Exception {
        mockPrivateMethods();
        ValidationHolder mockValidationHolder = generate(CUSTOM);
        CustomValidation mockCustomValidation = mock(CustomValidation.class);
        when(mockValidationHolder.getCustomValidation()).thenReturn(mockCustomValidation);
        when(mockCustomValidation.compare(mockValidationHolder)).thenReturn(true);
        assertTrue((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkCustomTypeField", mockValidationHolder, mEmptyCustomValidationCallback));
        verifyPrivate(mSpiedValidator, never()).invoke("executeCustomCallback", mEmptyCustomValidationCallback, mockValidationHolder);
    }

    public void testCheckCustomTypeFieldFail() throws Exception {
        mockPrivateMethods();
        ValidationHolder mockValidationHolder = generate(CUSTOM);
        CustomValidation mockCustomValidation = mock(CustomValidation.class);
        when(mockValidationHolder.getCustomValidation()).thenReturn(mockCustomValidation);
        when(mockCustomValidation.compare(mockValidationHolder)).thenReturn(false);
        assertFalse((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkCustomTypeField", mockValidationHolder, mEmptyCustomValidationCallback));
        verifyPrivate(mSpiedValidator, times(1)).invoke("executeCustomCallback", mEmptyCustomValidationCallback, mockValidationHolder);
    }

    public void testCheckFieldsPassWithValidInvisibleField() {
        mockPrivateMethods();
        mockCheckRegexTypeField(true);
        mSpiedValidator.mValidationHolderList.add(generate(REGEX, false));
        assertTrue(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsPassWithInValidInvisibleField() {
        mockPrivateMethods();
        mockCheckRegexTypeField(false);
        mSpiedValidator.mValidationHolderList.add(generate(REGEX, false));
        assertTrue(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsPassWithMultipleValidAndInValidInvisibleFields() {
        mockPrivateMethods();
        mockCheckRegexTypeField(false, true);
        mockCheckRangeTypeField(true, false);
        mockCheckConfirmationTypeField(false, true);
        mockCheckSimpleCustomTypeField(true, false);
        mockCheckCustomTypeField(false, true);
        mSpiedValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX, false),
                generate(RANGE, false),
                generate(CONFIRMATION, false),
                generate(SIMPLE_CUSTOM, false),
                generate(CUSTOM, false),
                generate(REGEX, false),
                generate(RANGE, false),
                generate(CONFIRMATION, false),
                generate(SIMPLE_CUSTOM, false),
                generate(CUSTOM, false)
        ));
        assertTrue(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testExecuteCallback() throws Exception {
        ValidationHolder mockValidationHolder = mock(ValidationHolder.class);
        ValidationCallback mockValidationCallback = mock(ValidationCallback.class);
        Matcher mockMatcher = PowerMockito.mock(Matcher.class);
        doNothing().when(mockValidationCallback).execute(any(ValidationHolder.class), any(Matcher.class));
        PowerMockito.doNothing().when(mSpiedValidator, "requestFocus", any(ValidationHolder.class));
        Whitebox.invokeMethod(mSpiedValidator, "executeCallback", mockValidationCallback, mockValidationHolder, mockMatcher);
        verify(mockValidationCallback).execute(mockValidationHolder, mockMatcher);
        verifyPrivate(mSpiedValidator, times(1)).invoke("requestFocus", mockValidationHolder);
    }

    public void testExecuteCustomCallback() throws Exception {
        ValidationHolder mockValidationHolder = mock(ValidationHolder.class);
        CustomValidationCallback mockCustomValidationCallback = mock(CustomValidationCallback.class);
        doNothing().when(mockCustomValidationCallback).execute(any(ValidationHolder.class));
        Whitebox.invokeMethod(mSpiedValidator, "executeCustomCallback", mockCustomValidationCallback, mockValidationHolder);
        verify(mockCustomValidationCallback).execute(mockValidationHolder);
    }

    public void testRequestFocus() throws Exception {
        ValidationHolder mockValidationHolder = mock(ValidationHolder.class);
        EditText mockEditText = mock(EditText.class);
        SpannableStringBuilder mockEditable = PowerMockito.mock(SpannableStringBuilder.class);
        when(mockValidationHolder.getEditText()).thenReturn(mockEditText);
        when(mockEditText.getText()).thenReturn(mockEditable);
        when(mockEditable.length()).thenReturn(PowerMockito.mock(Integer.class));
        when(mockEditText.requestFocus()).thenReturn(true);
        doNothing().when(mockEditText).setSelection(anyInt());
        Whitebox.setInternalState(mSpiedValidator, "mHasFailed", false);
        Whitebox.invokeMethod(mSpiedValidator, "requestFocus", mockValidationHolder);
        assertTrue((Boolean) Whitebox.getInternalState(mSpiedValidator, "mHasFailed"));
    }

}
