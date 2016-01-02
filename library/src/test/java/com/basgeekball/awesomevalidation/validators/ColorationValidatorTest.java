package com.basgeekball.awesomevalidation.validators;

import android.graphics.Color;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.helper.SpanHelper;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.RANGE;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.REGEX;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.generate;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ColorationValidator.class, MockValidationHolderHelper.class, SpanHelper.class})
public class ColorationValidatorTest extends TestCase {

    private ColorationValidator mColorationValidator;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mColorationValidator = new ColorationValidator();
        PowerMockito.mockStatic(SpanHelper.class);
        PowerMockito.doNothing().when(SpanHelper.class);
        SpanHelper.reset(any(EditText.class));
    }

    public void testTriggerOneWithoutError() {
        ValidationHolder mockedValidationHolder = generate(REGEX, true);
        String mockedErrMsg = PowerMockito.mock(String.class);
        when(mockedValidationHolder.getErrMsg()).thenReturn(mockedErrMsg);
        mColorationValidator.mValidationHolderList.add(mockedValidationHolder);
        assertTrue(mColorationValidator.trigger());
        verify(mockedValidationHolder.getEditText(), never()).setError(mockedErrMsg);
        PowerMockito.verifyStatic(never());
        SpanHelper.setColor(any(EditText.class), anyInt(), any(ArrayList.class));
    }

    public void testTriggerManyWithoutError() {
        ValidationHolder mockedValidationHolderRegexType = generate(REGEX, true);
        ValidationHolder mockedValidationHolderRangeType = generate(RANGE, true);
        String mockedErrMsg = PowerMockito.mock(String.class);
        when(mockedValidationHolderRegexType.getErrMsg()).thenReturn(mockedErrMsg);
        when(mockedValidationHolderRangeType.getErrMsg()).thenReturn(mockedErrMsg);
        mColorationValidator.mValidationHolderList.addAll(Arrays.asList(mockedValidationHolderRegexType, mockedValidationHolderRangeType));
        assertTrue(mColorationValidator.trigger());
        verify(mockedValidationHolderRegexType.getEditText(), never()).setError(mockedErrMsg);
        verify(mockedValidationHolderRangeType.getEditText(), never()).setError(mockedErrMsg);
        PowerMockito.verifyStatic(never());
        SpanHelper.setColor(any(EditText.class), anyInt(), any(ArrayList.class));
    }

    public void testTriggerOneWithError() {
        ValidationHolder mockedValidationHolder = generate(REGEX, false);
        String mockedErrMsg = PowerMockito.mock(String.class);
        when(mockedValidationHolder.getErrMsg()).thenReturn(mockedErrMsg);
        mColorationValidator.mValidationHolderList.add(mockedValidationHolder);
        assertFalse(mColorationValidator.trigger());
        verify(mockedValidationHolder.getEditText(), times(1)).setError(mockedErrMsg);
        PowerMockito.verifyStatic(times(1));
        SpanHelper.setColor(any(EditText.class), eq(Color.RED), any(ArrayList.class));
    }

    public void testTriggerManyWithError() {
        ValidationHolder mockedValidationHolderRegexTypePass = generate(REGEX, true);
        ValidationHolder mockedValidationHolderRegexTypeFail = generate(REGEX, false);
        ValidationHolder mockedValidationHolderRangeTypePass = generate(RANGE, true);
        ValidationHolder mockedValidationHolderRangeTypeFail = generate(RANGE, false);
        String mockedErrMsg = PowerMockito.mock(String.class);
        when(mockedValidationHolderRegexTypePass.getErrMsg()).thenReturn(mockedErrMsg);
        when(mockedValidationHolderRegexTypeFail.getErrMsg()).thenReturn(mockedErrMsg);
        when(mockedValidationHolderRangeTypePass.getErrMsg()).thenReturn(mockedErrMsg);
        when(mockedValidationHolderRangeTypeFail.getErrMsg()).thenReturn(mockedErrMsg);
        mColorationValidator.mValidationHolderList.addAll(Arrays.asList(mockedValidationHolderRegexTypePass,
                mockedValidationHolderRegexTypeFail,
                mockedValidationHolderRangeTypePass,
                mockedValidationHolderRangeTypeFail));
        assertFalse(mColorationValidator.trigger());
        verify(mockedValidationHolderRegexTypePass.getEditText(), never()).setError(mockedErrMsg);
        verify(mockedValidationHolderRangeTypePass.getEditText(), never()).setError(mockedErrMsg);
        verify(mockedValidationHolderRegexTypeFail.getEditText(), times(1)).setError(mockedErrMsg);
        verify(mockedValidationHolderRangeTypeFail.getEditText(), times(1)).setError(mockedErrMsg);
        PowerMockito.verifyStatic(never());
        SpanHelper.setColor(eq(mockedValidationHolderRegexTypePass.getEditText()), anyInt(), any(ArrayList.class));
        SpanHelper.setColor(eq(mockedValidationHolderRangeTypePass.getEditText()), anyInt(), any(ArrayList.class));
        PowerMockito.verifyStatic(times(1));
        SpanHelper.setColor(eq(mockedValidationHolderRegexTypeFail.getEditText()), eq(Color.RED), any(ArrayList.class));
        SpanHelper.setColor(eq(mockedValidationHolderRangeTypeFail.getEditText()), eq(Color.RED), any(ArrayList.class));
    }

    public void testHaltClearErrorForAllValid() {
        ValidationHolder mockedValidationHolderRegexType = generate(REGEX, true);
        ValidationHolder mockedValidationHolderRangeType = generate(RANGE, true);
        mColorationValidator.mValidationHolderList.addAll(Arrays.asList(mockedValidationHolderRegexType, mockedValidationHolderRangeType));
        mColorationValidator.halt();
        verify(mockedValidationHolderRegexType.getEditText(), times(1)).setError(null);
        verify(mockedValidationHolderRangeType.getEditText(), times(1)).setError(null);
        PowerMockito.verifyStatic(times(1));
        SpanHelper.reset(mockedValidationHolderRegexType.getEditText());
        SpanHelper.reset(mockedValidationHolderRangeType.getEditText());
    }

    public void testHaltClearErrorForAllInvalid() {
        ValidationHolder mockedValidationHolderRegexType = generate(REGEX, false);
        ValidationHolder mockedValidationHolderRangeType = generate(RANGE, false);
        mColorationValidator.mValidationHolderList.addAll(Arrays.asList(mockedValidationHolderRegexType, mockedValidationHolderRangeType));
        mColorationValidator.halt();
        verify(mockedValidationHolderRegexType.getEditText(), times(1)).setError(null);
        verify(mockedValidationHolderRangeType.getEditText(), times(1)).setError(null);
        PowerMockito.verifyStatic(times(1));
        SpanHelper.reset(mockedValidationHolderRegexType.getEditText());
        SpanHelper.reset(mockedValidationHolderRangeType.getEditText());
    }

    public void testHaltClearErrorForAllAnyway() {
        ValidationHolder mockedValidationHolderRegexTypePass = generate(REGEX, true);
        ValidationHolder mockedValidationHolderRegexTypeFail = generate(REGEX, false);
        ValidationHolder mockedValidationHolderRangeTypePass = generate(RANGE, true);
        ValidationHolder mockedValidationHolderRangeTypeFail = generate(RANGE, false);
        mColorationValidator.mValidationHolderList.addAll(Arrays.asList(mockedValidationHolderRegexTypePass,
                mockedValidationHolderRegexTypeFail,
                mockedValidationHolderRangeTypePass,
                mockedValidationHolderRangeTypeFail));
        mColorationValidator.halt();
        verify(mockedValidationHolderRegexTypePass.getEditText(), times(1)).setError(null);
        verify(mockedValidationHolderRegexTypeFail.getEditText(), times(1)).setError(null);
        verify(mockedValidationHolderRangeTypePass.getEditText(), times(1)).setError(null);
        verify(mockedValidationHolderRangeTypeFail.getEditText(), times(1)).setError(null);
        PowerMockito.verifyStatic(times(1));
        SpanHelper.reset(mockedValidationHolderRegexTypePass.getEditText());
        SpanHelper.reset(mockedValidationHolderRegexTypeFail.getEditText());
        SpanHelper.reset(mockedValidationHolderRangeTypePass.getEditText());
        SpanHelper.reset(mockedValidationHolderRangeTypeFail.getEditText());
    }

}
