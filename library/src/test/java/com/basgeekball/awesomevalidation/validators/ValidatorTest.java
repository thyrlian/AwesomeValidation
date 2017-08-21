package com.basgeekball.awesomevalidation.validators;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.text.SpannableStringBuilder;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.model.NumericRange;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.expectation.PowerMockitoStubber;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.CONFIRMATION;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.RANGE;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.REGEX;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.generate;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
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

    private void mockPrivateMethods() {
        try {
            PowerMockito.doNothing().when(mSpiedValidator, "executeCallback", any(ValidationCallback.class), any(ValidationHolder.class), any(Matcher.class));
            PowerMockito.doNothing().when(mSpiedValidator, "requestFocus", any(ValidationHolder.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mockCheckSomeCertainTypeField(String methodName, List<Boolean> returnValues) {
        try {
            PowerMockitoStubber stubber = PowerMockito.doReturn(returnValues.get(0));
            for (int i = 1; i < returnValues.size(); i++) {
                stubber = (PowerMockitoStubber) stubber.doReturn(returnValues.get(i));
            }
            stubber.when(mSpiedValidator, methodName, any(ValidationHolder.class), any(ValidationCallback.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mockCheckRegexTypeField(List<Boolean> returnValues) {
        mockCheckSomeCertainTypeField("checkRegexTypeField", returnValues);
    }

    private void mockCheckRangeTypeField(List<Boolean> returnValues) {
        mockCheckSomeCertainTypeField("checkRangeTypeField", returnValues);
    }

    private void mockCheckConfirmationTypeField(List<Boolean> returnValues) {
        mockCheckSomeCertainTypeField("checkConfirmationTypeField", returnValues);
    }

    private void mockCheckRegexTypeField(boolean returnValue) {
        mockCheckRegexTypeField(Arrays.asList(returnValue));
    }

    private void mockCheckRangeTypeField(boolean returnValue) {
        mockCheckRangeTypeField(Arrays.asList(returnValue));
    }

    private void mockCheckConfirmationTypeField(boolean returnValue) {
        mockCheckConfirmationTypeField(Arrays.asList(returnValue));
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
        when(mockActivity.findViewById(eq(viewId))).thenReturn(mockEditText);
        when(mockActivity.getResources().getString(eq(errMsgId))).thenReturn(errMsg);
        mSpiedValidator.set(mockActivity, viewId, regex, errMsgId);
        verify(mSpiedValidator, times(1)).set(mockEditText, regex, errMsg);
    }

    public void testSetTextInputLayoutWithActivityAndRegex() {
        Activity mockActivity = mock(Activity.class, RETURNS_DEEP_STUBS);
        int viewId = 1;
        String regex = "OK";
        int errMsgId = 9;
        String errMsg = "Error";
        TextInputLayout mockTextInputLayout = mock(TextInputLayout.class);
        when(mockActivity.findViewById(eq(viewId))).thenReturn(mockTextInputLayout);
        when(mockActivity.getResources().getString(eq(errMsgId))).thenReturn(errMsg);
        mSpiedValidator.set(mockActivity, viewId, regex, errMsgId);
        verify(mSpiedValidator, times(1)).set(mockTextInputLayout, regex, errMsg);
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
        verify(mSpiedValidator, times(1)).set(mockEditText, mockPattern, errMsg);
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
        verify(mSpiedValidator, times(1)).set(mockTextInputLayout, mockPattern, errMsg);
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
        verify(mSpiedValidator, times(1)).set(mockEditText, mockNumericRange, errMsg);
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
        verify(mSpiedValidator, times(1)).set(mockTextInputLayout, mockNumericRange, errMsg);
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
        verify(mSpiedValidator, times(1)).set(mockConfirmationEditText, mockEditText, errMsg);
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
        verify(mSpiedValidator, times(1)).set(mockConfirmationTextInputLayout, mockTextInputLayout, errMsg);
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

    public void testCheckFieldsPassWithManyDifferentValidationHolders() {
        mockPrivateMethods();
        mockCheckRegexTypeField(Arrays.asList(true, true));
        mockCheckRangeTypeField(Arrays.asList(true, true));
        mockCheckConfirmationTypeField(Arrays.asList(true, true));
        mSpiedValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION)
        ));
        assertTrue(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailDueToOneRegexFromManyDifferentValidationHolders() {
        mockPrivateMethods();
        mockCheckRegexTypeField(Arrays.asList(true, false, true));
        mockCheckRangeTypeField(Arrays.asList(true, true, true));
        mockCheckConfirmationTypeField(Arrays.asList(true, true, true));
        mSpiedValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION)
        ));
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailDueToOneRangeFromManyDifferentValidationHolders() {
        mockPrivateMethods();
        mockCheckRegexTypeField(Arrays.asList(true, true, true));
        mockCheckRangeTypeField(Arrays.asList(true, false, true));
        mockCheckConfirmationTypeField(Arrays.asList(true, true, true));
        mSpiedValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION)
        ));
        assertFalse(mSpiedValidator.checkFields(mEmptyValidationCallback));
    }

    public void testCheckFieldsFailDueToOneConfirmationFromManyDifferentValidationHolders() {
        mockPrivateMethods();
        mockCheckRegexTypeField(Arrays.asList(true, true, true));
        mockCheckRangeTypeField(Arrays.asList(true, true, true));
        mockCheckConfirmationTypeField(Arrays.asList(true, false, true));
        mSpiedValidator.mValidationHolderList.addAll(Arrays.asList(
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION),
                generate(REGEX),
                generate(RANGE),
                generate(CONFIRMATION)
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
        PowerMockito.mockStatic(Pattern.class);
        Pattern mockPattern = PowerMockito.mock(Pattern.class);
        Matcher mockMatcher = PowerMockito.mock(Matcher.class);
        when(Pattern.compile(anyString())).thenReturn(mockPattern);
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
        PowerMockito.mockStatic(Pattern.class);
        Pattern mockPattern = PowerMockito.mock(Pattern.class);
        Matcher mockMatcher = PowerMockito.mock(Matcher.class);
        when(Pattern.compile(anyString())).thenReturn(mockPattern);
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
