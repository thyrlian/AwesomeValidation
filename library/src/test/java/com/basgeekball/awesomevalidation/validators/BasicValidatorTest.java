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
    private ValidationHolder mMockedValidationHolderRegexTypePass;
    private ValidationHolder mMockedValidationHolderRegexTypeFail;
    private ValidationHolder mMockedValidationHolderRangeTypePass;
    private ValidationHolder mMockedValidationHolderRangeTypeFail;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mBasicValidator = new BasicValidator();
        mMockedValidationHolderRegexTypePass = generate(REGEX, true);
        mMockedValidationHolderRegexTypeFail = generate(REGEX, false);
        mMockedValidationHolderRangeTypePass = generate(RANGE, true);
        mMockedValidationHolderRangeTypeFail = generate(RANGE, false);
    }

    public void testTriggerRegexTypeWithoutError() {
        mBasicValidator.mValidationHolderList.add(mMockedValidationHolderRegexTypePass);
        assertTrue(mBasicValidator.trigger());
        verify(mMockedValidationHolderRegexTypePass.getEditText(), never()).setError(anyString());
    }

    public void testTriggerRegexTypeWithError() {
        mBasicValidator.mValidationHolderList.add(mMockedValidationHolderRegexTypeFail);
        assertFalse(mBasicValidator.trigger());
        verify(mMockedValidationHolderRegexTypeFail.getEditText(), times(1)).setError(mMockedValidationHolderRegexTypeFail.getErrMsg());
    }

    public void testTriggerRangeTypeWithoutError() {
        mBasicValidator.mValidationHolderList.add(mMockedValidationHolderRangeTypePass);
        assertTrue(mBasicValidator.trigger());
        verify(mMockedValidationHolderRangeTypePass.getEditText(), never()).setError(anyString());
    }

    public void testTriggerRangeTypeWithError() {
        mBasicValidator.mValidationHolderList.add(mMockedValidationHolderRangeTypeFail);
        assertFalse(mBasicValidator.trigger());
        verify(mMockedValidationHolderRangeTypeFail.getEditText(), times(1)).setError(mMockedValidationHolderRangeTypeFail.getErrMsg());
    }

    public void testTriggerMixedTypesWithoutError() {
        mBasicValidator.mValidationHolderList.addAll(Arrays.asList(mMockedValidationHolderRegexTypePass, mMockedValidationHolderRangeTypePass));
        assertTrue(mBasicValidator.trigger());
        verify(mMockedValidationHolderRegexTypePass.getEditText(), never()).setError(anyString());
        verify(mMockedValidationHolderRangeTypePass.getEditText(), never()).setError(anyString());
    }

    public void testTriggerMixedTypesWithError() {
        mBasicValidator.mValidationHolderList.addAll(Arrays.asList(mMockedValidationHolderRegexTypePass,
                mMockedValidationHolderRegexTypeFail,
                mMockedValidationHolderRangeTypePass,
                mMockedValidationHolderRangeTypeFail));
        assertFalse(mBasicValidator.trigger());
        verify(mMockedValidationHolderRegexTypePass.getEditText(), never()).setError(anyString());
        verify(mMockedValidationHolderRegexTypeFail.getEditText(), times(1)).setError(mMockedValidationHolderRegexTypeFail.getErrMsg());
        verify(mMockedValidationHolderRangeTypePass.getEditText(), never()).setError(anyString());
        verify(mMockedValidationHolderRangeTypeFail.getEditText(), times(1)).setError(mMockedValidationHolderRangeTypeFail.getErrMsg());
    }

    public void testHaltClearErrorForAllValid() {
        mBasicValidator.mValidationHolderList.addAll(Arrays.asList(mMockedValidationHolderRegexTypePass, mMockedValidationHolderRangeTypePass));
        mBasicValidator.halt();
        verify(mMockedValidationHolderRegexTypePass.getEditText(), times(1)).setError(null);
        verify(mMockedValidationHolderRangeTypePass.getEditText(), times(1)).setError(null);
    }

    public void testHaltClearErrorForAllInvalid() {
        mBasicValidator.mValidationHolderList.addAll(Arrays.asList(mMockedValidationHolderRegexTypeFail, mMockedValidationHolderRangeTypeFail));
        mBasicValidator.halt();
        verify(mMockedValidationHolderRegexTypeFail.getEditText(), times(1)).setError(null);
        verify(mMockedValidationHolderRangeTypeFail.getEditText(), times(1)).setError(null);
    }

    public void testHaltClearErrorForAllAnyway() {
        mBasicValidator.mValidationHolderList.addAll(Arrays.asList(mMockedValidationHolderRegexTypePass,
                mMockedValidationHolderRegexTypeFail,
                mMockedValidationHolderRangeTypePass,
                mMockedValidationHolderRangeTypeFail));
        mBasicValidator.halt();
        verify(mMockedValidationHolderRegexTypePass.getEditText(), times(1)).setError(null);
        verify(mMockedValidationHolderRegexTypeFail.getEditText(), times(1)).setError(null);
        verify(mMockedValidationHolderRangeTypePass.getEditText(), times(1)).setError(null);
        verify(mMockedValidationHolderRangeTypeFail.getEditText(), times(1)).setError(null);
    }

}
