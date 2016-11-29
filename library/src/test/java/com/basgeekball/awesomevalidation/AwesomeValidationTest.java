package com.basgeekball.awesomevalidation;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.model.NumericRange;
import com.basgeekball.awesomevalidation.validators.BasicValidator;
import com.basgeekball.awesomevalidation.validators.ColorationValidator;
import com.basgeekball.awesomevalidation.validators.TextInputLayoutValidator;
import com.basgeekball.awesomevalidation.validators.UnderlabelValidator;
import com.google.common.collect.Range;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.regex.Pattern;

import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AwesomeValidation.class, Range.class})
public class AwesomeValidationTest extends TestCase {

    private BasicValidator mSpiedBasicValidator;
    private ColorationValidator mSpiedColorationValidator;
    private UnderlabelValidator mSpiedUnderlabelValidator;
    private TextInputLayoutValidator mSpiedTextInputLayoutValidator;
    private AwesomeValidation mSpiedAwesomeValidationBasicStyle;
    private AwesomeValidation mSpiedAwesomeValidationColorationStyle;
    private AwesomeValidation mSpiedAwesomeValidationUnderlabelStyle;
    private AwesomeValidation mSpiedAwesomeValidationTextInputLayoutStyle;
    private Context mMockedContext;
    private int mColor = 256;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSpiedBasicValidator = spy(BasicValidator.class);
        mSpiedColorationValidator = spy(ColorationValidator.class);
        mSpiedUnderlabelValidator = spy(UnderlabelValidator.class);
        mSpiedTextInputLayoutValidator = spy(TextInputLayoutValidator.class);
        mSpiedAwesomeValidationBasicStyle = spy(new AwesomeValidation(ValidationStyle.BASIC));
        mSpiedAwesomeValidationColorationStyle = spy(new AwesomeValidation(ValidationStyle.COLORATION));
        mSpiedAwesomeValidationUnderlabelStyle = spy(new AwesomeValidation(ValidationStyle.UNDERLABEL));
        mSpiedAwesomeValidationTextInputLayoutStyle = spy(new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT));
        MemberModifier.field(AwesomeValidation.class, "mValidator").set(mSpiedAwesomeValidationBasicStyle, mSpiedBasicValidator);
        MemberModifier.field(AwesomeValidation.class, "mValidator").set(mSpiedAwesomeValidationColorationStyle, mSpiedColorationValidator);
        MemberModifier.field(AwesomeValidation.class, "mValidator").set(mSpiedAwesomeValidationUnderlabelStyle, mSpiedUnderlabelValidator);
        MemberModifier.field(AwesomeValidation.class, "mValidator").set(mSpiedAwesomeValidationTextInputLayoutStyle, mSpiedTextInputLayoutValidator);
        mMockedContext = mock(Context.class);
    }

    public void testAwesomeValidationConstructBasicStyle() {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        assertTrue(Whitebox.getInternalState(awesomeValidation, "mValidator") instanceof BasicValidator);
    }

    public void testAwesomeValidationConstructColorationStyle() {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.COLORATION);
        assertTrue(Whitebox.getInternalState(awesomeValidation, "mValidator") instanceof ColorationValidator);
    }

    public void testAwesomeValidationConstructUnderlabelValidatorStyle() {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        assertTrue(Whitebox.getInternalState(awesomeValidation, "mValidator") instanceof UnderlabelValidator);
    }

    public void testAwesomeValidationConstructTextInputLayoutValidatorStyle() {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        assertTrue(Whitebox.getInternalState(awesomeValidation, "mValidator") instanceof TextInputLayoutValidator);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCheckIsColorationValidatorThrowsException() throws Exception {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        Whitebox.invokeMethod(awesomeValidation, "checkIsColorationValidator");
    }

    public void testCheckIsColorationValidatorWithoutException() throws Exception {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.COLORATION);
        Whitebox.invokeMethod(awesomeValidation, "checkIsColorationValidator");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCheckIsUnderlabelValidatorThrowsException() throws Exception {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        Whitebox.invokeMethod(awesomeValidation, "checkIsUnderlabelValidator");
    }

    public void testCheckIsUnderlabelValidatorWithoutException() throws Exception {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        Whitebox.invokeMethod(awesomeValidation, "checkIsUnderlabelValidator");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCheckIsTextInputLayoutValidatorThrowsException() throws Exception {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        Whitebox.invokeMethod(awesomeValidation, "checkIsTextInputLayoutValidator");
    }

    public void testCheckIsTextInputLayoutValidatorWithoutException() throws Exception {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        Whitebox.invokeMethod(awesomeValidation, "checkIsTextInputLayoutValidator");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCheckIsNotTextInputLayoutValidatorThrowsException() throws Exception {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        Whitebox.invokeMethod(awesomeValidation, "checkIsNotTextInputLayoutValidator");
    }

    public void testCheckIsNotTextInputLayoutValidatorWithoutException() throws Exception {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.COLORATION);
        Whitebox.invokeMethod(awesomeValidation, "checkIsNotTextInputLayoutValidator");
    }

    public void testSetContextForUnderlabelStyle() throws Exception {
        mSpiedAwesomeValidationUnderlabelStyle.setContext(mMockedContext);
        verify(mSpiedUnderlabelValidator, times(1)).setContext(mMockedContext);
    }

    public void testSetContextForNonUnderlabelStyle() throws Exception {
        doThrow(UnsupportedOperationException.class).when(mSpiedAwesomeValidationBasicStyle).setContext(mMockedContext);
    }

    public void testSetColorForColorationStyle() throws Exception {
        mSpiedAwesomeValidationColorationStyle.setColor(mColor);
        verify(mSpiedColorationValidator, times(1)).setColor(mColor);
    }

    public void testSetColorForNonColorationStyle() throws Exception {
        doThrow(UnsupportedOperationException.class).when(mSpiedAwesomeValidationBasicStyle).setColor(mColor);
    }

    public void testAddValidation() throws Exception {
        Activity mockedActivity = mock(Activity.class, RETURNS_MOCKS);
        EditText mockedEditText = mock(EditText.class, RETURNS_MOCKS);
        EditText mockedConfirmationEditText = mock(EditText.class, RETURNS_MOCKS);
        TextInputLayout mockedTextInputLayout = mock(TextInputLayout.class, RETURNS_MOCKS);
        TextInputLayout mockedConfirmationTextInputLayout = mock(TextInputLayout.class, RETURNS_MOCKS);
        Pattern mockedPattern = PowerMockito.mock(Pattern.class, RETURNS_MOCKS);
        Range mockedRange = PowerMockito.mock(Range.class, RETURNS_MOCKS);
        String mockedRegex = PowerMockito.mock(String.class);
        String mockedErrMsg = PowerMockito.mock(String.class);
        NumericRange mockedNumericRange = mock(NumericRange.class);
        int viewId = 65535;
        int confirmationViewId = 65536;
        int errMsgId = 32768;
        when(mockedActivity.findViewById(viewId)).thenReturn(mockedEditText);
        when(mockedActivity.findViewById(confirmationViewId)).thenReturn(mockedConfirmationEditText);
        PowerMockito.whenNew(NumericRange.class).withArguments(mockedRange).thenReturn(mockedNumericRange);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockedEditText, mockedRegex, mockedErrMsg);
        verify(mSpiedBasicValidator, times(1)).set(mockedEditText, mockedRegex, mockedErrMsg);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockedActivity, viewId, mockedRegex, errMsgId);
        verify(mSpiedBasicValidator, times(1)).set(mockedActivity, viewId, mockedRegex, errMsgId);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockedEditText, mockedPattern, mockedErrMsg);
        verify(mSpiedBasicValidator, times(1)).set(mockedEditText, mockedPattern, mockedErrMsg);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockedActivity, viewId, mockedPattern, errMsgId);
        verify(mSpiedBasicValidator, times(1)).set(mockedActivity, viewId, mockedPattern, errMsgId);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockedEditText, mockedRange, mockedErrMsg);
        verify(mSpiedBasicValidator, times(1)).set(mockedEditText, new NumericRange(mockedRange), mockedErrMsg);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockedActivity, viewId, mockedRange, errMsgId);
        verify(mSpiedBasicValidator, times(1)).set(mockedActivity, viewId, new NumericRange(mockedRange), errMsgId);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockedConfirmationEditText, mockedEditText, mockedErrMsg);
        verify(mSpiedBasicValidator, times(1)).set(mockedConfirmationEditText, mockedEditText, mockedErrMsg);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockedActivity, confirmationViewId, viewId, errMsgId);
        verify(mSpiedBasicValidator, times(1)).set(mockedActivity, confirmationViewId, viewId, errMsgId);

        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mockedTextInputLayout, mockedRegex, mockedErrMsg);
        verify(mSpiedTextInputLayoutValidator, times(1)).set(mockedTextInputLayout, mockedRegex, mockedErrMsg);

        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mockedTextInputLayout, mockedPattern, mockedErrMsg);
        verify(mSpiedTextInputLayoutValidator, times(1)).set(mockedTextInputLayout, mockedPattern, mockedErrMsg);

        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mockedTextInputLayout, mockedRange, mockedErrMsg);
        verify(mSpiedTextInputLayoutValidator, times(1)).set(mockedTextInputLayout, new NumericRange(mockedRange), mockedErrMsg);

        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mockedConfirmationTextInputLayout, mockedTextInputLayout, mockedErrMsg);
        verify(mSpiedTextInputLayoutValidator, times(1)).set(mockedConfirmationTextInputLayout, mockedTextInputLayout, mockedErrMsg);
    }

    public void testValidate() {
        mSpiedAwesomeValidationBasicStyle.validate();
        verify(mSpiedBasicValidator, times(1)).trigger();
        assertEquals(mSpiedBasicValidator.trigger(), mSpiedAwesomeValidationBasicStyle.validate());
    }

    public void testClear() throws Exception {
        mSpiedAwesomeValidationBasicStyle.clear();
        verify(mSpiedBasicValidator, times(1)).halt();
    }

}
