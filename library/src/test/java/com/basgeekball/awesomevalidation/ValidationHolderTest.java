package com.basgeekball.awesomevalidation;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.model.NumericRange;

import junit.framework.TestCase;

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

    private EditText mMockedEditText;
    private EditText mMockedConfirmationEditText;
    private TextInputLayout mMockedTextInputLayout;
    private TextInputLayout mMockedConfirmationTextInputLayout;
    private ValidationHolder mValidationHolderRegexTypeWithEditText;
    private ValidationHolder mValidationHolderRangeTypeWithEditText;
    private ValidationHolder mValidationHolderConfirmationTypeWithEditText;
    private ValidationHolder mValidationHolderRegexTypeWithTextInputLayout;
    private ValidationHolder mValidationHolderRangeTypeWithTextInputLayout;
    private ValidationHolder mValidationHolderConfirmationTypeWithTextInputLayout;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockedEditText = mock(EditText.class);
        mMockedConfirmationEditText = mock(EditText.class);
        mMockedTextInputLayout = mock(TextInputLayout.class);
        mMockedConfirmationTextInputLayout = mock(TextInputLayout.class);
        Pattern mockedPattern = PowerMock.createMock(Pattern.class);
        NumericRange mockedNumericRange = mock(NumericRange.class);
        String mockedErrMsg = PowerMock.createMock(String.class);
        mValidationHolderRegexTypeWithEditText = new ValidationHolder(mMockedEditText, mockedPattern, mockedErrMsg);
        mValidationHolderRangeTypeWithEditText = new ValidationHolder(mMockedEditText, mockedNumericRange, mockedErrMsg);
        mValidationHolderConfirmationTypeWithEditText = new ValidationHolder(mMockedConfirmationEditText, mMockedEditText, mockedErrMsg);
        mValidationHolderRegexTypeWithTextInputLayout = new ValidationHolder(mMockedTextInputLayout, mockedPattern, mockedErrMsg);
        mValidationHolderRangeTypeWithTextInputLayout = new ValidationHolder(mMockedTextInputLayout, mockedNumericRange, mockedErrMsg);
        mValidationHolderConfirmationTypeWithTextInputLayout = new ValidationHolder(mMockedConfirmationTextInputLayout, mMockedTextInputLayout, mockedErrMsg);
    }

    public void testIsRegexTypeTrue() {
        assertTrue(mValidationHolderRegexTypeWithEditText.isRegexType());
        assertTrue(mValidationHolderRegexTypeWithTextInputLayout.isRegexType());
    }

    public void testIsRegexTypeFalse() {
        assertFalse(mValidationHolderRangeTypeWithEditText.isRegexType());
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isRegexType());
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isRegexType());
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isRegexType());
    }

    public void testIsRangeTypeTrue() {
        assertTrue(mValidationHolderRangeTypeWithEditText.isRangeType());
        assertTrue(mValidationHolderRangeTypeWithTextInputLayout.isRangeType());
    }

    public void testIsRangeTypeFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isRangeType());
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isRangeType());
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isRangeType());
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isRangeType());
    }

    public void testIsConfirmationTypeTrue() {
        assertTrue(mValidationHolderConfirmationTypeWithEditText.isConfirmationType());
        assertTrue(mValidationHolderConfirmationTypeWithTextInputLayout.isConfirmationType());
    }

    public void testIsConfirmationTypeFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isConfirmationType());
        assertFalse(mValidationHolderRangeTypeWithEditText.isConfirmationType());
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isConfirmationType());
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isConfirmationType());
    }

    public void testIsEditTextStyleTrue() {
        assertTrue(mValidationHolderRegexTypeWithEditText.isEditTextStyle());
        assertTrue(mValidationHolderRangeTypeWithEditText.isEditTextStyle());
        assertTrue(mValidationHolderConfirmationTypeWithEditText.isEditTextStyle());
    }

    public void testIsEditTextStyleFalse() {
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isEditTextStyle());
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isEditTextStyle());
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isEditTextStyle());
    }

    public void testIsTextInputLayoutStyleTrue() {
        assertTrue(mValidationHolderRegexTypeWithTextInputLayout.isTextInputLayoutStyle());
        assertTrue(mValidationHolderRangeTypeWithTextInputLayout.isTextInputLayoutStyle());
        assertTrue(mValidationHolderConfirmationTypeWithTextInputLayout.isTextInputLayoutStyle());
    }

    public void testIsTextInputLayoutStyleFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isTextInputLayoutStyle());
        assertFalse(mValidationHolderRangeTypeWithEditText.isTextInputLayoutStyle());
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isTextInputLayoutStyle());
    }

    public void testGetTextFromEditText() {
        String text = "OK";
        Editable mockedEditable = mock(Editable.class);
        when(mMockedEditText.getText()).thenReturn(mockedEditable);
        when(mockedEditable.toString()).thenReturn(text);
        assertEquals(text, mValidationHolderRegexTypeWithEditText.getText());
        assertEquals(text, mValidationHolderRangeTypeWithEditText.getText());
        assertEquals(text, mValidationHolderConfirmationTypeWithEditText.getText());
    }

    public void testGetTextFromTextInputLayout() {
        String text = "OK";
        EditText mockedEditText = mock(EditText.class);
        Editable mockedEditable = mock(Editable.class);
        when(mMockedTextInputLayout.getEditText()).thenReturn(mockedEditText);
        when(mockedEditText.getText()).thenReturn(mockedEditable);
        when(mockedEditable.toString()).thenReturn(text);
        assertEquals(text, mValidationHolderRegexTypeWithTextInputLayout.getText());
        assertEquals(text, mValidationHolderRangeTypeWithTextInputLayout.getText());
        assertEquals(text, mValidationHolderConfirmationTypeWithTextInputLayout.getText());
    }

    public void testGetConfirmationTextFromEditText() {
        String text = "OK";
        Editable mockedEditable = mock(Editable.class);
        when(mMockedConfirmationEditText.getText()).thenReturn(mockedEditable);
        when(mockedEditable.toString()).thenReturn(text);
        assertEquals(text, mValidationHolderConfirmationTypeWithEditText.getConfirmationText());
    }

    public void testGetConfirmationTextFromTextInputLayout() {
        String text = "OK";
        EditText mockedEditText = mock(EditText.class);
        Editable mockedEditable = mock(Editable.class);
        when(mMockedConfirmationTextInputLayout.getEditText()).thenReturn(mockedEditText);
        when(mockedEditText.getText()).thenReturn(mockedEditable);
        when(mockedEditable.toString()).thenReturn(text);
        assertEquals(text, mValidationHolderConfirmationTypeWithTextInputLayout.getConfirmationText());
    }

    public void testGetConfirmationTextReturnsNull() {
        assertNull(mValidationHolderRegexTypeWithEditText.getConfirmationText());
        assertNull(mValidationHolderRangeTypeWithEditText.getConfirmationText());
        assertNull(mValidationHolderRegexTypeWithTextInputLayout.getConfirmationText());
        assertNull(mValidationHolderRangeTypeWithTextInputLayout.getConfirmationText());
    }

    public void testGetEditTextFromEditText() {
        assertEquals(mMockedEditText, mValidationHolderRegexTypeWithEditText.getEditText());
        assertEquals(mMockedEditText, mValidationHolderRangeTypeWithEditText.getEditText());
        assertEquals(mMockedConfirmationEditText, mValidationHolderConfirmationTypeWithEditText.getEditText());
    }

    public void testGetEditTextFromTextInputLayout() {
        EditText mockedEditText = mock(EditText.class);
        EditText mockedConfirmationEditText = mock(EditText.class);
        when(mMockedTextInputLayout.getEditText()).thenReturn(mockedEditText);
        when(mMockedConfirmationTextInputLayout.getEditText()).thenReturn(mockedConfirmationEditText);
        assertEquals(mockedEditText, mValidationHolderRegexTypeWithTextInputLayout.getEditText());
        assertEquals(mockedEditText, mValidationHolderRangeTypeWithTextInputLayout.getEditText());
        assertEquals(mockedConfirmationEditText, mValidationHolderConfirmationTypeWithTextInputLayout.getEditText());
    }

    public void testGetTextInputLayout() {
        assertEquals(mMockedTextInputLayout, mValidationHolderRegexTypeWithTextInputLayout.getTextInputLayout());
        assertEquals(mMockedTextInputLayout, mValidationHolderRangeTypeWithTextInputLayout.getTextInputLayout());
        assertEquals(mMockedConfirmationTextInputLayout, mValidationHolderConfirmationTypeWithTextInputLayout.getTextInputLayout());
    }

    public void testGetTextInputLayoutReturnsNull() {
        assertNull(mValidationHolderRegexTypeWithEditText.getTextInputLayout());
        assertNull(mValidationHolderRangeTypeWithEditText.getTextInputLayout());
        assertNull(mValidationHolderConfirmationTypeWithEditText.getTextInputLayout());
    }

}
