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
    private Context mMockContext;
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
        mMockContext = mock(Context.class);
    }

    public void testAwesomeValidationConstructBasicStyle() {
        assertTrue(Whitebox.getInternalState(mSpiedAwesomeValidationBasicStyle, "mValidator") instanceof BasicValidator);
    }

    public void testAwesomeValidationConstructColorationStyle() {
        assertTrue(Whitebox.getInternalState(mSpiedAwesomeValidationColorationStyle, "mValidator") instanceof ColorationValidator);
    }

    public void testAwesomeValidationConstructUnderlabelValidatorStyle() {
        assertTrue(Whitebox.getInternalState(mSpiedAwesomeValidationUnderlabelStyle, "mValidator") instanceof UnderlabelValidator);
    }

    public void testAwesomeValidationConstructTextInputLayoutValidatorStyle() {
        assertTrue(Whitebox.getInternalState(mSpiedAwesomeValidationTextInputLayoutStyle, "mValidator") instanceof TextInputLayoutValidator);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCheckIsColorationValidatorThrowsException() throws Exception {
        Whitebox.invokeMethod(mSpiedAwesomeValidationBasicStyle, "checkIsColorationValidator");
    }

    public void testCheckIsColorationValidatorWithoutException() throws Exception {
        Whitebox.invokeMethod(mSpiedAwesomeValidationColorationStyle, "checkIsColorationValidator");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCheckIsUnderlabelValidatorThrowsException() throws Exception {
        Whitebox.invokeMethod(mSpiedAwesomeValidationTextInputLayoutStyle, "checkIsUnderlabelValidator");
    }

    public void testCheckIsUnderlabelValidatorWithoutException() throws Exception {
        Whitebox.invokeMethod(mSpiedAwesomeValidationUnderlabelStyle, "checkIsUnderlabelValidator");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCheckIsTextInputLayoutValidatorThrowsException() throws Exception {
        Whitebox.invokeMethod(mSpiedAwesomeValidationUnderlabelStyle, "checkIsTextInputLayoutValidator");
    }

    public void testCheckIsTextInputLayoutValidatorWithoutException() throws Exception {
        Whitebox.invokeMethod(mSpiedAwesomeValidationTextInputLayoutStyle, "checkIsTextInputLayoutValidator");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCheckIsNotTextInputLayoutValidatorThrowsException() throws Exception {
        Whitebox.invokeMethod(mSpiedAwesomeValidationTextInputLayoutStyle, "checkIsNotTextInputLayoutValidator");
    }

    public void testCheckIsNotTextInputLayoutValidatorWithoutException() throws Exception {
        Whitebox.invokeMethod(mSpiedAwesomeValidationColorationStyle, "checkIsNotTextInputLayoutValidator");
    }

    public void testSetContextForUnderlabelStyle() throws Exception {
        mSpiedAwesomeValidationUnderlabelStyle.setContext(mMockContext);
        verify(mSpiedUnderlabelValidator, times(1)).setContext(mMockContext);
    }

    public void testSetContextForNonUnderlabelStyle() throws Exception {
        doThrow(UnsupportedOperationException.class).when(mSpiedAwesomeValidationBasicStyle).setContext(mMockContext);
    }

    public void testSetColorForColorationStyle() throws Exception {
        mSpiedAwesomeValidationColorationStyle.setColor(mColor);
        verify(mSpiedColorationValidator, times(1)).setColor(mColor);
    }

    public void testSetColorForNonColorationStyle() throws Exception {
        doThrow(UnsupportedOperationException.class).when(mSpiedAwesomeValidationBasicStyle).setColor(mColor);
    }

    public void testAddValidation() throws Exception {
        Activity mockActivity = mock(Activity.class, RETURNS_MOCKS);
        EditText mockEditText = mock(EditText.class, RETURNS_MOCKS);
        EditText mockConfirmationEditText = mock(EditText.class, RETURNS_MOCKS);
        TextInputLayout mockTextInputLayout = mock(TextInputLayout.class, RETURNS_MOCKS);
        TextInputLayout mockConfirmationTextInputLayout = mock(TextInputLayout.class, RETURNS_MOCKS);
        Pattern mockPattern = PowerMockito.mock(Pattern.class, RETURNS_MOCKS);
        Range mockRange = PowerMockito.mock(Range.class, RETURNS_MOCKS);
        String mockRegex = PowerMockito.mock(String.class);
        String mockErrMsg = PowerMockito.mock(String.class);
        NumericRange mockNumericRange = mock(NumericRange.class);
        int viewId = 65535;
        int confirmationViewId = 65536;
        int errMsgId = 32768;
        when(mockActivity.findViewById(viewId)).thenReturn(mockEditText);
        when(mockActivity.findViewById(confirmationViewId)).thenReturn(mockConfirmationEditText);
        PowerMockito.whenNew(NumericRange.class).withArguments(mockRange).thenReturn(mockNumericRange);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockEditText, mockRegex, mockErrMsg);
        verify(mSpiedBasicValidator, times(1)).set(mockEditText, mockRegex, mockErrMsg);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockActivity, viewId, mockRegex, errMsgId);
        verify(mSpiedBasicValidator, times(1)).set(mockActivity, viewId, mockRegex, errMsgId);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockEditText, mockPattern, mockErrMsg);
        verify(mSpiedBasicValidator, times(1)).set(mockEditText, mockPattern, mockErrMsg);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockActivity, viewId, mockPattern, errMsgId);
        verify(mSpiedBasicValidator, times(1)).set(mockActivity, viewId, mockPattern, errMsgId);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockEditText, mockRange, mockErrMsg);
        verify(mSpiedBasicValidator, times(1)).set(mockEditText, new NumericRange(mockRange), mockErrMsg);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockActivity, viewId, mockRange, errMsgId);
        verify(mSpiedBasicValidator, times(1)).set(mockActivity, viewId, new NumericRange(mockRange), errMsgId);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockConfirmationEditText, mockEditText, mockErrMsg);
        verify(mSpiedBasicValidator, times(1)).set(mockConfirmationEditText, mockEditText, mockErrMsg);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockActivity, confirmationViewId, viewId, errMsgId);
        verify(mSpiedBasicValidator, times(1)).set(mockActivity, confirmationViewId, viewId, errMsgId);

        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mockTextInputLayout, mockRegex, mockErrMsg);
        verify(mSpiedTextInputLayoutValidator, times(1)).set(mockTextInputLayout, mockRegex, mockErrMsg);

        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mockTextInputLayout, mockPattern, mockErrMsg);
        verify(mSpiedTextInputLayoutValidator, times(1)).set(mockTextInputLayout, mockPattern, mockErrMsg);

        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mockTextInputLayout, mockRange, mockErrMsg);
        verify(mSpiedTextInputLayoutValidator, times(1)).set(mockTextInputLayout, new NumericRange(mockRange), mockErrMsg);

        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mockConfirmationTextInputLayout, mockTextInputLayout, mockErrMsg);
        verify(mSpiedTextInputLayoutValidator, times(1)).set(mockConfirmationTextInputLayout, mockTextInputLayout, mockErrMsg);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddValidationForTextInputLayoutValidatorWithEditTextAndRegexThrowsException() {
        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mock(EditText.class), PowerMockito.mock(String.class), PowerMockito.mock(String.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddValidationForTextInputLayoutValidatorWithEditTextAndPatternThrowsException() {
        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mock(EditText.class), PowerMockito.mock(Pattern.class), PowerMockito.mock(String.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddValidationForTextInputLayoutValidatorWithEditTextAndRangeThrowsException() {
        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mock(EditText.class), PowerMockito.mock(Range.class), PowerMockito.mock(String.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddValidationForTextInputLayoutValidatorWithEditTextAndConfirmationThrowsException() {
        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mock(EditText.class), mock(EditText.class), PowerMockito.mock(String.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddValidationForNonTextInputLayoutValidatorWithTextInputLayoutAndRegexThrowsException() {
        mSpiedAwesomeValidationBasicStyle.addValidation(mock(TextInputLayout.class), PowerMockito.mock(String.class), PowerMockito.mock(String.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddValidationForNonTextInputLayoutValidatorWithTextInputLayoutAndPatternThrowsException() {
        mSpiedAwesomeValidationColorationStyle.addValidation(mock(TextInputLayout.class), PowerMockito.mock(Pattern.class), PowerMockito.mock(String.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddValidationForNonTextInputLayoutValidatorWithTextInputLayoutAndRangeThrowsException() {
        mSpiedAwesomeValidationUnderlabelStyle.addValidation(mock(TextInputLayout.class), PowerMockito.mock(Range.class), PowerMockito.mock(String.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddValidationForNonTextInputLayoutValidatorWithTextInputLayoutAndConfirmationThrowsException() {
        mSpiedAwesomeValidationBasicStyle.addValidation(mock(TextInputLayout.class), mock(TextInputLayout.class), PowerMockito.mock(String.class));
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
