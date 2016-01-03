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
    private ValidationHolder mMockedValidationHolderRegexTypePass;
    private ValidationHolder mMockedValidationHolderRegexTypeFail;
    private ValidationHolder mMockedValidationHolderRangeTypePass;
    private ValidationHolder mMockedValidationHolderRangeTypeFail;
    private String mMockedErrMsg;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mColorationValidator = new ColorationValidator();
        mMockedValidationHolderRegexTypePass = generate(REGEX, true);
        mMockedValidationHolderRegexTypeFail = generate(REGEX, false);
        mMockedValidationHolderRangeTypePass = generate(RANGE, true);
        mMockedValidationHolderRangeTypeFail = generate(RANGE, false);
        mMockedErrMsg = PowerMockito.mock(String.class);
        when(mMockedValidationHolderRegexTypePass.getErrMsg()).thenReturn(mMockedErrMsg);
        when(mMockedValidationHolderRegexTypeFail.getErrMsg()).thenReturn(mMockedErrMsg);
        when(mMockedValidationHolderRangeTypePass.getErrMsg()).thenReturn(mMockedErrMsg);
        when(mMockedValidationHolderRangeTypeFail.getErrMsg()).thenReturn(mMockedErrMsg);
        PowerMockito.mockStatic(SpanHelper.class);
        PowerMockito.doNothing().when(SpanHelper.class, "reset", any(EditText.class));
    }

    public void testTriggerOneWithoutError() {
        mColorationValidator.mValidationHolderList.add(mMockedValidationHolderRegexTypePass);
        assertTrue(mColorationValidator.trigger());
        verify(mMockedValidationHolderRegexTypePass.getEditText(), never()).setError(mMockedErrMsg);
        PowerMockito.verifyStatic(never());
        SpanHelper.setColor(any(EditText.class), anyInt(), any(ArrayList.class));
    }

    public void testTriggerManyWithoutError() {
        mColorationValidator.mValidationHolderList.addAll(Arrays.asList(mMockedValidationHolderRegexTypePass, mMockedValidationHolderRangeTypePass));
        assertTrue(mColorationValidator.trigger());
        verify(mMockedValidationHolderRegexTypePass.getEditText(), never()).setError(mMockedErrMsg);
        verify(mMockedValidationHolderRangeTypePass.getEditText(), never()).setError(mMockedErrMsg);
        PowerMockito.verifyStatic(never());
        SpanHelper.setColor(any(EditText.class), anyInt(), any(ArrayList.class));
    }

    public void testTriggerOneWithError() {
        mColorationValidator.mValidationHolderList.add(mMockedValidationHolderRegexTypeFail);
        assertFalse(mColorationValidator.trigger());
        verify(mMockedValidationHolderRegexTypeFail.getEditText(), times(1)).setError(mMockedErrMsg);
        PowerMockito.verifyStatic(times(1));
        SpanHelper.setColor(any(EditText.class), eq(Color.RED), any(ArrayList.class));
    }

    public void testTriggerManyWithError() {
        mColorationValidator.mValidationHolderList.addAll(Arrays.asList(mMockedValidationHolderRegexTypePass,
                mMockedValidationHolderRegexTypeFail,
                mMockedValidationHolderRangeTypePass,
                mMockedValidationHolderRangeTypeFail));
        assertFalse(mColorationValidator.trigger());
        verify(mMockedValidationHolderRegexTypePass.getEditText(), never()).setError(mMockedErrMsg);
        verify(mMockedValidationHolderRegexTypeFail.getEditText(), times(1)).setError(mMockedErrMsg);
        verify(mMockedValidationHolderRangeTypePass.getEditText(), never()).setError(mMockedErrMsg);
        verify(mMockedValidationHolderRangeTypeFail.getEditText(), times(1)).setError(mMockedErrMsg);
        PowerMockito.verifyStatic(never());
        SpanHelper.setColor(eq(mMockedValidationHolderRegexTypePass.getEditText()), anyInt(), any(ArrayList.class));
        SpanHelper.setColor(eq(mMockedValidationHolderRangeTypePass.getEditText()), anyInt(), any(ArrayList.class));
        PowerMockito.verifyStatic(times(1));
        SpanHelper.setColor(eq(mMockedValidationHolderRegexTypeFail.getEditText()), eq(Color.RED), any(ArrayList.class));
        SpanHelper.setColor(eq(mMockedValidationHolderRangeTypeFail.getEditText()), eq(Color.RED), any(ArrayList.class));
    }

    public void testHaltClearErrorForAllValid() {
        mColorationValidator.mValidationHolderList.addAll(Arrays.asList(mMockedValidationHolderRegexTypePass, mMockedValidationHolderRangeTypePass));
        mColorationValidator.halt();
        verify(mMockedValidationHolderRegexTypePass.getEditText(), times(1)).setError(null);
        verify(mMockedValidationHolderRangeTypePass.getEditText(), times(1)).setError(null);
        PowerMockito.verifyStatic(times(1));
        SpanHelper.reset(mMockedValidationHolderRegexTypePass.getEditText());
        SpanHelper.reset(mMockedValidationHolderRangeTypePass.getEditText());
    }

    public void testHaltClearErrorForAllInvalid() {
        mColorationValidator.mValidationHolderList.addAll(Arrays.asList(mMockedValidationHolderRegexTypeFail, mMockedValidationHolderRangeTypeFail));
        mColorationValidator.halt();
        verify(mMockedValidationHolderRegexTypeFail.getEditText(), times(1)).setError(null);
        verify(mMockedValidationHolderRangeTypeFail.getEditText(), times(1)).setError(null);
        PowerMockito.verifyStatic(times(1));
        SpanHelper.reset(mMockedValidationHolderRegexTypeFail.getEditText());
        SpanHelper.reset(mMockedValidationHolderRangeTypeFail.getEditText());
    }

    public void testHaltClearErrorForAllAnyway() {
        mColorationValidator.mValidationHolderList.addAll(Arrays.asList(mMockedValidationHolderRegexTypePass,
                mMockedValidationHolderRegexTypeFail,
                mMockedValidationHolderRangeTypePass,
                mMockedValidationHolderRangeTypeFail));
        mColorationValidator.halt();
        verify(mMockedValidationHolderRegexTypePass.getEditText(), times(1)).setError(null);
        verify(mMockedValidationHolderRegexTypeFail.getEditText(), times(1)).setError(null);
        verify(mMockedValidationHolderRangeTypePass.getEditText(), times(1)).setError(null);
        verify(mMockedValidationHolderRangeTypeFail.getEditText(), times(1)).setError(null);
        PowerMockito.verifyStatic(times(1));
        SpanHelper.reset(mMockedValidationHolderRegexTypePass.getEditText());
        SpanHelper.reset(mMockedValidationHolderRegexTypeFail.getEditText());
        SpanHelper.reset(mMockedValidationHolderRangeTypePass.getEditText());
        SpanHelper.reset(mMockedValidationHolderRangeTypeFail.getEditText());
    }

}
