package com.basgeekball.awesomevalidation;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.exception.BadLayoutException;
import com.basgeekball.awesomevalidation.helper.CustomValidation;
import com.basgeekball.awesomevalidation.model.NumericRange;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.regex.Pattern;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidationHolder.class)
public class ValidationHolderTest extends TestCase {

    private EditText mMockEditText;
    private EditText mMockConfirmationEditText;
    private TextInputLayout mMockTextInputLayout;
    private TextInputLayout mMockConfirmationTextInputLayout;
    private ValidationHolder mValidationHolderRegexTypeWithEditText;
    private ValidationHolder mValidationHolderRangeTypeWithEditText;
    private ValidationHolder mValidationHolderConfirmationTypeWithEditText;
    private ValidationHolder mValidationHolderCustomValidationWithEditText;
    private ValidationHolder mValidationHolderRegexTypeWithTextInputLayout;
    private ValidationHolder mValidationHolderRangeTypeWithTextInputLayout;
    private ValidationHolder mValidationHolderConfirmationTypeWithTextInputLayout;
    private ValidationHolder mValidationHolderCustomValidationWithTextInputLayout;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockEditText = mock(EditText.class);
        mMockConfirmationEditText = mock(EditText.class);
        mMockTextInputLayout = mock(TextInputLayout.class);
        mMockConfirmationTextInputLayout = mock(TextInputLayout.class);
        Pattern mockPattern = PowerMock.createMock(Pattern.class);
        NumericRange mockNumericRange = mock(NumericRange.class);
        CustomValidation mockCustomValidation = mock(CustomValidation.class);
        String mockErrMsg = PowerMock.createMock(String.class);
        mValidationHolderRegexTypeWithEditText = new ValidationHolder(mMockEditText, mockPattern, mockErrMsg);
        mValidationHolderRangeTypeWithEditText = new ValidationHolder(mMockEditText, mockNumericRange, mockErrMsg);
        mValidationHolderConfirmationTypeWithEditText = new ValidationHolder(mMockConfirmationEditText, mMockEditText, mockErrMsg);
        mValidationHolderCustomValidationWithEditText = new ValidationHolder(mMockEditText, mockCustomValidation, mockErrMsg);
        mValidationHolderRegexTypeWithTextInputLayout = new ValidationHolder(mMockTextInputLayout, mockPattern, mockErrMsg);
        mValidationHolderRangeTypeWithTextInputLayout = new ValidationHolder(mMockTextInputLayout, mockNumericRange, mockErrMsg);
        mValidationHolderConfirmationTypeWithTextInputLayout = new ValidationHolder(mMockConfirmationTextInputLayout, mMockTextInputLayout, mockErrMsg);
        mValidationHolderCustomValidationWithTextInputLayout = new ValidationHolder(mMockTextInputLayout, mockCustomValidation, mockErrMsg);
    }

    public void testIsRegexTypeTrue() {
        assertTrue(mValidationHolderRegexTypeWithEditText.isRegexType());
        assertTrue(mValidationHolderRegexTypeWithTextInputLayout.isRegexType());
    }

    public void testIsRegexTypeFalse() {
        assertFalse(mValidationHolderRangeTypeWithEditText.isRegexType());
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isRegexType());
        assertFalse(mValidationHolderCustomValidationWithEditText.isRegexType());
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isRegexType());
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isRegexType());
        assertFalse(mValidationHolderCustomValidationWithTextInputLayout.isRegexType());
    }

    public void testIsRangeTypeTrue() {
        assertTrue(mValidationHolderRangeTypeWithEditText.isRangeType());
        assertTrue(mValidationHolderRangeTypeWithTextInputLayout.isRangeType());
    }

    public void testIsRangeTypeFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isRangeType());
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isRangeType());
        assertFalse(mValidationHolderCustomValidationWithEditText.isRangeType());
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isRangeType());
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isRangeType());
        assertFalse(mValidationHolderCustomValidationWithTextInputLayout.isRangeType());
    }

    public void testIsConfirmationTypeTrue() {
        assertTrue(mValidationHolderConfirmationTypeWithEditText.isConfirmationType());
        assertTrue(mValidationHolderConfirmationTypeWithTextInputLayout.isConfirmationType());
    }

    public void testIsConfirmationTypeFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isConfirmationType());
        assertFalse(mValidationHolderRangeTypeWithEditText.isConfirmationType());
        assertFalse(mValidationHolderCustomValidationWithEditText.isConfirmationType());
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isConfirmationType());
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isConfirmationType());
        assertFalse(mValidationHolderCustomValidationWithTextInputLayout.isConfirmationType());
    }

    public void testIsCustomTypeTrue() {
        assertTrue(mValidationHolderCustomValidationWithEditText.isCustomType());
        assertTrue(mValidationHolderCustomValidationWithTextInputLayout.isCustomType());
    }

    public void testIsCustomTypeFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isCustomType());
        assertFalse(mValidationHolderRangeTypeWithEditText.isCustomType());
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isCustomType());
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isCustomType());
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isCustomType());
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isCustomType());
    }

    public void testIsEditTextStyleTrue() {
        assertTrue(mValidationHolderRegexTypeWithEditText.isEditTextStyle());
        assertTrue(mValidationHolderRangeTypeWithEditText.isEditTextStyle());
        assertTrue(mValidationHolderConfirmationTypeWithEditText.isEditTextStyle());
        assertTrue(mValidationHolderCustomValidationWithEditText.isEditTextStyle());
    }

    public void testIsEditTextStyleFalse() {
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isEditTextStyle());
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isEditTextStyle());
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isEditTextStyle());
        assertFalse(mValidationHolderCustomValidationWithTextInputLayout.isEditTextStyle());
    }

    public void testIsTextInputLayoutStyleTrue() {
        assertTrue(mValidationHolderRegexTypeWithTextInputLayout.isTextInputLayoutStyle());
        assertTrue(mValidationHolderRangeTypeWithTextInputLayout.isTextInputLayoutStyle());
        assertTrue(mValidationHolderConfirmationTypeWithTextInputLayout.isTextInputLayoutStyle());
        assertTrue(mValidationHolderCustomValidationWithTextInputLayout.isTextInputLayoutStyle());
    }

    public void testIsTextInputLayoutStyleFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isTextInputLayoutStyle());
        assertFalse(mValidationHolderRangeTypeWithEditText.isTextInputLayoutStyle());
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isTextInputLayoutStyle());
        assertFalse(mValidationHolderCustomValidationWithEditText.isTextInputLayoutStyle());
    }

    public void testGetTextFromEditText() {
        String text = "OK";
        Editable mockEditable = mock(Editable.class);
        when(mMockEditText.getText()).thenReturn(mockEditable);
        when(mockEditable.toString()).thenReturn(text);
        assertEquals(text, mValidationHolderRegexTypeWithEditText.getText());
        assertEquals(text, mValidationHolderRangeTypeWithEditText.getText());
        assertEquals(text, mValidationHolderConfirmationTypeWithEditText.getText());
        assertEquals(text, mValidationHolderCustomValidationWithEditText.getText());
    }

    public void testGetTextFromTextInputLayout() {
        String text = "OK";
        EditText mockEditText = mock(EditText.class);
        Editable mockEditable = mock(Editable.class);
        when(mMockTextInputLayout.getEditText()).thenReturn(mockEditText);
        when(mockEditText.getText()).thenReturn(mockEditable);
        when(mockEditable.toString()).thenReturn(text);
        assertEquals(text, mValidationHolderRegexTypeWithTextInputLayout.getText());
        assertEquals(text, mValidationHolderRangeTypeWithTextInputLayout.getText());
        assertEquals(text, mValidationHolderConfirmationTypeWithTextInputLayout.getText());
        assertEquals(text, mValidationHolderCustomValidationWithTextInputLayout.getText());
    }

    @Test(expected = BadLayoutException.class)
    public void testGetTextFromTextInputLayoutThrowsException() {
        when(mMockTextInputLayout.getEditText()).thenReturn(null);
        mValidationHolderRegexTypeWithTextInputLayout.getText();
    }

    public void testGetTextReturnsNull() {
        EditText mockEditText = null;
        TextInputLayout mockTextInputLayout = null;
        Pattern mockPattern = PowerMock.createMock(Pattern.class);
        String mockErrMsg = PowerMock.createMock(String.class);
        mValidationHolderRegexTypeWithEditText = new ValidationHolder(mockEditText, mockPattern, mockErrMsg);
        mValidationHolderRegexTypeWithTextInputLayout = new ValidationHolder(mockTextInputLayout, mockPattern, mockErrMsg);
        assertNull(mValidationHolderRegexTypeWithEditText.getText());
        assertNull(mValidationHolderRegexTypeWithTextInputLayout.getText());
    }

    public void testGetConfirmationTextFromEditText() {
        String text = "OK";
        Editable mockEditable = mock(Editable.class);
        when(mMockConfirmationEditText.getText()).thenReturn(mockEditable);
        when(mockEditable.toString()).thenReturn(text);
        assertEquals(text, mValidationHolderConfirmationTypeWithEditText.getConfirmationText());
    }

    public void testGetConfirmationTextFromTextInputLayout() {
        String text = "OK";
        EditText mockEditText = mock(EditText.class);
        Editable mockEditable = mock(Editable.class);
        when(mMockConfirmationTextInputLayout.getEditText()).thenReturn(mockEditText);
        when(mockEditText.getText()).thenReturn(mockEditable);
        when(mockEditable.toString()).thenReturn(text);
        assertEquals(text, mValidationHolderConfirmationTypeWithTextInputLayout.getConfirmationText());
    }

    @Test(expected = BadLayoutException.class)
    public void testGetConfirmationTextFromTextInputLayoutThrowsException() {
        when(mMockConfirmationTextInputLayout.getEditText()).thenReturn(null);
        mValidationHolderConfirmationTypeWithTextInputLayout.getConfirmationText();
    }

    public void testGetConfirmationTextReturnsNull() {
        assertNull(mValidationHolderRegexTypeWithEditText.getConfirmationText());
        assertNull(mValidationHolderRangeTypeWithEditText.getConfirmationText());
        assertNull(mValidationHolderCustomValidationWithEditText.getConfirmationText());
        assertNull(mValidationHolderRegexTypeWithTextInputLayout.getConfirmationText());
        assertNull(mValidationHolderRangeTypeWithTextInputLayout.getConfirmationText());
        assertNull(mValidationHolderCustomValidationWithTextInputLayout.getConfirmationText());
    }

    public void testGetEditTextFromEditText() {
        assertEquals(mMockEditText, mValidationHolderRegexTypeWithEditText.getEditText());
        assertEquals(mMockEditText, mValidationHolderRangeTypeWithEditText.getEditText());
        assertEquals(mMockConfirmationEditText, mValidationHolderConfirmationTypeWithEditText.getEditText());
        assertEquals(mMockEditText, mValidationHolderCustomValidationWithEditText.getEditText());
    }

    public void testGetEditTextFromTextInputLayout() {
        EditText mockEditText = mock(EditText.class);
        EditText mockConfirmationEditText = mock(EditText.class);
        when(mMockTextInputLayout.getEditText()).thenReturn(mockEditText);
        when(mMockConfirmationTextInputLayout.getEditText()).thenReturn(mockConfirmationEditText);
        assertEquals(mockEditText, mValidationHolderRegexTypeWithTextInputLayout.getEditText());
        assertEquals(mockEditText, mValidationHolderRangeTypeWithTextInputLayout.getEditText());
        assertEquals(mockConfirmationEditText, mValidationHolderConfirmationTypeWithTextInputLayout.getEditText());
        assertEquals(mockEditText, mValidationHolderCustomValidationWithTextInputLayout.getEditText());
    }

    public void testGetTextInputLayout() {
        assertEquals(mMockTextInputLayout, mValidationHolderRegexTypeWithTextInputLayout.getTextInputLayout());
        assertEquals(mMockTextInputLayout, mValidationHolderRangeTypeWithTextInputLayout.getTextInputLayout());
        assertEquals(mMockConfirmationTextInputLayout, mValidationHolderConfirmationTypeWithTextInputLayout.getTextInputLayout());
        assertEquals(mMockTextInputLayout, mValidationHolderCustomValidationWithTextInputLayout.getTextInputLayout());
    }

    public void testGetTextInputLayoutReturnsNull() {
        assertNull(mValidationHolderRegexTypeWithEditText.getTextInputLayout());
        assertNull(mValidationHolderRangeTypeWithEditText.getTextInputLayout());
        assertNull(mValidationHolderConfirmationTypeWithEditText.getTextInputLayout());
        assertNull(mValidationHolderCustomValidationWithEditText.getTextInputLayout());
    }

}