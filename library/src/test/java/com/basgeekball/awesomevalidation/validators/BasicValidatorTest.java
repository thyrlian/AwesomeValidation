package com.basgeekball.awesomevalidation.validators;

import com.basgeekball.awesomevalidation.ValidationHolder;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;

import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.RANGE;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.REGEX;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.generate;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BasicValidator.class, MockValidationHolderHelper.class})
public class BasicValidatorTest extends TestCase {

    private BasicValidator mBasicValidator;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mBasicValidator = new BasicValidator();
    }

    public void testTriggerRegexTypeWithoutError() {
        ValidationHolder mockedValidationHolder = generate(REGEX, true);
        mBasicValidator.mValidationHolderList.add(mockedValidationHolder);
        mBasicValidator.trigger();
        verify(mockedValidationHolder.getEditText(), never()).setError(anyString());
    }

    public void testTriggerRegexTypeWithError() {
        ValidationHolder mockedValidationHolder = generate(REGEX, false);
        mBasicValidator.mValidationHolderList.add(mockedValidationHolder);
        mBasicValidator.trigger();
        verify(mockedValidationHolder.getEditText(), times(1)).setError(mockedValidationHolder.getErrMsg());
    }

    public void testTriggerRangeTypeWithoutError() {
        ValidationHolder mockedValidationHolder = generate(RANGE, true);
        mBasicValidator.mValidationHolderList.add(mockedValidationHolder);
        mBasicValidator.trigger();
        verify(mockedValidationHolder.getEditText(), never()).setError(anyString());
    }

    public void testTriggerRangeTypeWithError() {
        ValidationHolder mockedValidationHolder = generate(RANGE, false);
        mBasicValidator.mValidationHolderList.add(mockedValidationHolder);
        mBasicValidator.trigger();
        verify(mockedValidationHolder.getEditText(), times(1)).setError(mockedValidationHolder.getErrMsg());
    }

    public void testTriggerMixedTypesWithoutError() {
        ValidationHolder mockedValidationHolderRegexType = generate(REGEX, true);
        ValidationHolder mockedValidationHolderRangeType = generate(RANGE, true);
        mBasicValidator.mValidationHolderList.addAll(Arrays.asList(mockedValidationHolderRegexType, mockedValidationHolderRangeType));
        mBasicValidator.trigger();
        verify(mockedValidationHolderRegexType.getEditText(), never()).setError(anyString());
        verify(mockedValidationHolderRangeType.getEditText(), never()).setError(anyString());
    }

    public void testTriggerMixedTypesWithError() {
        ValidationHolder mockedValidationHolderRegexTypePass = generate(REGEX, true);
        ValidationHolder mockedValidationHolderRegexTypeFail = generate(REGEX, false);
        ValidationHolder mockedValidationHolderRangeTypePass = generate(RANGE, true);
        ValidationHolder mockedValidationHolderRangeTypeFail = generate(RANGE, false);
        mBasicValidator.mValidationHolderList.addAll(Arrays.asList(mockedValidationHolderRegexTypePass,
                mockedValidationHolderRegexTypeFail,
                mockedValidationHolderRangeTypePass,
                mockedValidationHolderRangeTypeFail));
        mBasicValidator.trigger();
        verify(mockedValidationHolderRegexTypePass.getEditText(), never()).setError(anyString());
        verify(mockedValidationHolderRangeTypePass.getEditText(), never()).setError(anyString());
        verify(mockedValidationHolderRegexTypeFail.getEditText(), times(1)).setError(mockedValidationHolderRegexTypeFail.getErrMsg());
        verify(mockedValidationHolderRangeTypeFail.getEditText(), times(1)).setError(mockedValidationHolderRangeTypeFail.getErrMsg());
    }

    public void testHaltClearErrorForAllValid() {
        ValidationHolder mockedValidationHolderRegexType = generate(REGEX, true);
        ValidationHolder mockedValidationHolderRangeType = generate(RANGE, true);
        mBasicValidator.mValidationHolderList.addAll(Arrays.asList(mockedValidationHolderRegexType, mockedValidationHolderRangeType));
        mBasicValidator.halt();
        verify(mockedValidationHolderRegexType.getEditText(), times(1)).setError(null);
        verify(mockedValidationHolderRangeType.getEditText(), times(1)).setError(null);
    }

    public void testHaltClearErrorForAllInvalid() {
        ValidationHolder mockedValidationHolderRegexType = generate(REGEX, false);
        ValidationHolder mockedValidationHolderRangeType = generate(RANGE, false);
        mBasicValidator.mValidationHolderList.addAll(Arrays.asList(mockedValidationHolderRegexType, mockedValidationHolderRangeType));
        mBasicValidator.halt();
        verify(mockedValidationHolderRegexType.getEditText(), times(1)).setError(null);
        verify(mockedValidationHolderRangeType.getEditText(), times(1)).setError(null);
    }

    public void testHaltClearErrorForAllAnyway() {
        ValidationHolder mockedValidationHolderRegexTypePass = generate(REGEX, true);
        ValidationHolder mockedValidationHolderRegexTypeFail = generate(REGEX, false);
        ValidationHolder mockedValidationHolderRangeTypePass = generate(RANGE, true);
        ValidationHolder mockedValidationHolderRangeTypeFail = generate(RANGE, false);
        mBasicValidator.mValidationHolderList.addAll(Arrays.asList(mockedValidationHolderRegexTypePass,
                mockedValidationHolderRegexTypeFail,
                mockedValidationHolderRangeTypePass,
                mockedValidationHolderRangeTypeFail));
        mBasicValidator.halt();
        verify(mockedValidationHolderRegexTypePass.getEditText(), times(1)).setError(null);
        verify(mockedValidationHolderRegexTypeFail.getEditText(), times(1)).setError(null);
        verify(mockedValidationHolderRangeTypePass.getEditText(), times(1)).setError(null);
        verify(mockedValidationHolderRangeTypeFail.getEditText(), times(1)).setError(null);
    }

}
