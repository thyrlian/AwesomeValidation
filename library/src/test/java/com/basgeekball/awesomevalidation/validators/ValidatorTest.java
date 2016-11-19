package com.basgeekball.awesomevalidation.validators;

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
        ValidationHolder mockedValidationHolder = generate(REGEX);
        Pattern mockedPattern = PowerMockito.mock(Pattern.class);
        String mockedString = PowerMockito.mock(String.class);
        Matcher mockedMatcher = PowerMockito.mock(Matcher.class);
        when(mockedValidationHolder.getPattern()).thenReturn(mockedPattern);
        when(mockedValidationHolder.getText()).thenReturn(mockedString);
        when(mockedPattern.matcher(mockedString)).thenReturn(mockedMatcher);
        when(mockedMatcher.matches()).thenReturn(true);
        assertTrue((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkRegexTypeField", mockedValidationHolder, mEmptyValidationCallback));
        verifyPrivate(mSpiedValidator, never()).invoke("executeCallback", any(ValidationCallback.class), any(ValidationHolder.class), any(Matcher.class));
    }

    public void testCheckRegexTypeFieldFail() throws Exception {
        ValidationHolder mockedValidationHolder = generate(REGEX);
        Pattern mockedPattern = PowerMockito.mock(Pattern.class);
        String mockedString = PowerMockito.mock(String.class);
        Matcher mockedMatcher = PowerMockito.mock(Matcher.class);
        when(mockedValidationHolder.getPattern()).thenReturn(mockedPattern);
        when(mockedValidationHolder.getText()).thenReturn(mockedString);
        when(mockedPattern.matcher(mockedString)).thenReturn(mockedMatcher);
        when(mockedMatcher.matches()).thenReturn(false);
        PowerMockito.doNothing().when(mSpiedValidator, "executeCallback", any(ValidationCallback.class), any(ValidationHolder.class), any(Matcher.class));
        assertFalse((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkRegexTypeField", mockedValidationHolder, mEmptyValidationCallback));
        verifyPrivate(mSpiedValidator, times(1)).invoke("executeCallback", mEmptyValidationCallback, mockedValidationHolder, mockedMatcher);
    }

    public void testCheckRangeTypeFieldPass() throws Exception {
        ValidationHolder mockedValidationHolder = generate(RANGE);
        NumericRange mockedNumericRange = mock(NumericRange.class);
        String mockedString = PowerMockito.mock(String.class);
        when(mockedValidationHolder.getText()).thenReturn(mockedString);
        when(mockedValidationHolder.getNumericRange()).thenReturn(mockedNumericRange);
        when(mockedNumericRange.isValid(mockedString)).thenReturn(true);
        assertTrue((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkRangeTypeField", mockedValidationHolder, mEmptyValidationCallback));
        verifyPrivate(mSpiedValidator, never()).invoke("executeCallback", any(ValidationCallback.class), any(ValidationHolder.class), any(Matcher.class));
    }

    public void testCheckRangeTypeFieldFail() throws Exception {
        ValidationHolder mockedValidationHolder = generate(RANGE);
        NumericRange mockedNumericRange = mock(NumericRange.class);
        String mockedString = PowerMockito.mock(String.class);
        when(mockedValidationHolder.getText()).thenReturn(mockedString);
        when(mockedValidationHolder.getNumericRange()).thenReturn(mockedNumericRange);
        when(mockedNumericRange.isValid(mockedString)).thenReturn(false);
        PowerMockito.mockStatic(Pattern.class);
        Pattern mockedPattern = PowerMockito.mock(Pattern.class);
        Matcher mockedMatcher = PowerMockito.mock(Matcher.class);
        when(Pattern.compile(anyString())).thenReturn(mockedPattern);
        when(mockedPattern.matcher(mockedString)).thenReturn(mockedMatcher);
        PowerMockito.doNothing().when(mSpiedValidator, "executeCallback", any(ValidationCallback.class), any(ValidationHolder.class), any(Matcher.class));
        assertFalse((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkRangeTypeField", mockedValidationHolder, mEmptyValidationCallback));
        verifyPrivate(mSpiedValidator, times(1)).invoke("executeCallback", mEmptyValidationCallback, mockedValidationHolder, mockedMatcher);
    }

    public void testCheckRangeTypeFieldFailDueToException() throws Exception {
        ValidationHolder mockedValidationHolder = generate(RANGE);
        NumericRange mockedNumericRange = mock(NumericRange.class);
        String mockedString = PowerMockito.mock(String.class);
        when(mockedValidationHolder.getText()).thenReturn(mockedString);
        when(mockedValidationHolder.getNumericRange()).thenReturn(mockedNumericRange);
        doThrow(NumberFormatException.class).when(mockedNumericRange).isValid(mockedString);
        PowerMockito.mockStatic(Pattern.class);
        Pattern mockedPattern = PowerMockito.mock(Pattern.class);
        Matcher mockedMatcher = PowerMockito.mock(Matcher.class);
        when(Pattern.compile(anyString())).thenReturn(mockedPattern);
        when(mockedPattern.matcher(mockedString)).thenReturn(mockedMatcher);
        PowerMockito.doNothing().when(mSpiedValidator, "executeCallback", any(ValidationCallback.class), any(ValidationHolder.class), any(Matcher.class));
        assertFalse((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkRangeTypeField", mockedValidationHolder, mEmptyValidationCallback));
        verifyPrivate(mSpiedValidator, times(1)).invoke("executeCallback", mEmptyValidationCallback, mockedValidationHolder, mockedMatcher);
    }

    public void testCheckConfirmationTypeFieldPass() throws Exception {
        ValidationHolder mockedValidationHolder = generate(CONFIRMATION);
        String mockedStringA = "aaa";
        String mockedStringB = "aaa";
        when(mockedValidationHolder.getText()).thenReturn(mockedStringA);
        when(mockedValidationHolder.getConfirmationText()).thenReturn(mockedStringB);
        assertTrue((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkConfirmationTypeField", mockedValidationHolder, mEmptyValidationCallback));
        verifyPrivate(mSpiedValidator, never()).invoke("executeCallback", any(ValidationCallback.class), any(ValidationHolder.class), any(Matcher.class));
    }

    public void testCheckConfirmationTypeFieldFail() throws Exception {
        ValidationHolder mockedValidationHolder = generate(CONFIRMATION);
        String mockedStringA = "aaa";
        String mockedStringB = "bbb";
        when(mockedValidationHolder.getText()).thenReturn(mockedStringA);
        when(mockedValidationHolder.getConfirmationText()).thenReturn(mockedStringB);
        PowerMockito.doNothing().when(mSpiedValidator, "executeCallback", any(ValidationCallback.class), any(ValidationHolder.class), any(Matcher.class));
        assertFalse((Boolean) Whitebox.invokeMethod(mSpiedValidator, "checkConfirmationTypeField", mockedValidationHolder, mEmptyValidationCallback));
        verifyPrivate(mSpiedValidator, times(1)).invoke("executeCallback", mEmptyValidationCallback, mockedValidationHolder, null);
    }

    public void testExecuteCallback() throws Exception {
        ValidationHolder mockedValidationHolder = mock(ValidationHolder.class);
        ValidationCallback mockedValidationCallback = mock(ValidationCallback.class);
        Matcher mockedMatcher = PowerMockito.mock(Matcher.class);
        doNothing().when(mockedValidationCallback).execute(any(ValidationHolder.class), any(Matcher.class));
        PowerMockito.doNothing().when(mSpiedValidator, "requestFocus", any(ValidationHolder.class));
        Whitebox.invokeMethod(mSpiedValidator, "executeCallback", mockedValidationCallback, mockedValidationHolder, mockedMatcher);
        verify(mockedValidationCallback).execute(mockedValidationHolder, mockedMatcher);
        verifyPrivate(mSpiedValidator, times(1)).invoke("requestFocus", mockedValidationHolder);
    }

    public void testRequestFocus() throws Exception {
        ValidationHolder mockedValidationHolder = mock(ValidationHolder.class);
        EditText mockedEditText = mock(EditText.class);
        SpannableStringBuilder mockedEditable = PowerMockito.mock(SpannableStringBuilder.class);
        when(mockedValidationHolder.getEditText()).thenReturn(mockedEditText);
        when(mockedEditText.getText()).thenReturn(mockedEditable);
        when(mockedEditable.length()).thenReturn(PowerMockito.mock(Integer.class));
        when(mockedEditText.requestFocus()).thenReturn(true);
        doNothing().when(mockedEditText).setSelection(anyInt());
        Whitebox.setInternalState(mSpiedValidator, "mHasFailed", false);
        Whitebox.invokeMethod(mSpiedValidator, "requestFocus", mockedValidationHolder);
        assertTrue((Boolean) Whitebox.getInternalState(mSpiedValidator, "mHasFailed"));
    }

}
