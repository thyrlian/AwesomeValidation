package com.basgeekball.awesomevalidation.validators;

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
import static org.mockito.Mockito.never;
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
    }

    public void testTriggerOneWithoutError() {
        ValidationHolder mockedValidationHolder = generate(REGEX, true);
        String mockedErrMsg = PowerMockito.mock(String.class);
        when(mockedValidationHolder.getErrMsg()).thenReturn(mockedErrMsg);
        mColorationValidator.mValidationHolderList.add(mockedValidationHolder);
        PowerMockito.mockStatic(SpanHelper.class);
        PowerMockito.doNothing().when(SpanHelper.class);
        SpanHelper.reset(any(EditText.class));
        mColorationValidator.trigger();
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
        PowerMockito.mockStatic(SpanHelper.class);
        PowerMockito.doNothing().when(SpanHelper.class);
        SpanHelper.reset(any(EditText.class));
        mColorationValidator.trigger();
        verify(mockedValidationHolderRegexType.getEditText(), never()).setError(mockedErrMsg);
        verify(mockedValidationHolderRangeType.getEditText(), never()).setError(mockedErrMsg);
        PowerMockito.verifyStatic(never());
        SpanHelper.setColor(any(EditText.class), anyInt(), any(ArrayList.class));
    }

}
