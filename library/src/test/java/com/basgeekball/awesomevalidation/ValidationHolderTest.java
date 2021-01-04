package com.basgeekball.awesomevalidation;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.exception.BadLayoutException;
import com.basgeekball.awesomevalidation.model.NumericRange;
import com.basgeekball.awesomevalidation.utility.custom.CustomErrorReset;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidation;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidationCallback;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.google.android.material.textfield.TextInputLayout;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.regex.Pattern;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidationHolder.class)
public class ValidationHolderTest extends TestCase {

    private EditText mMockEditText;
    private EditText mMockConfirmationEditText;
    private TextInputLayout mMockTextInputLayout;
    private TextInputLayout mMockConfirmationTextInputLayout;
    private View mMockView;
    private CustomErrorReset mMockCustomErrorReset;
    private ValidationHolder mValidationHolderRegexTypeWithEditText;
    private ValidationHolder mValidationHolderRangeTypeWithEditText;
    private ValidationHolder mValidationHolderConfirmationTypeWithEditText;
    private ValidationHolder mValidationHolderSimpleCustomTypeWithEditText;
    private ValidationHolder mValidationHolderRegexTypeWithTextInputLayout;
    private ValidationHolder mValidationHolderRangeTypeWithTextInputLayout;
    private ValidationHolder mValidationHolderConfirmationTypeWithTextInputLayout;
    private ValidationHolder mValidationHolderSimpleCustomTypeWithTextInputLayout;
    private ValidationHolder mValidationHolderCustomTypeWithSomeSortOfView;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockEditText = mock(EditText.class);
        mMockConfirmationEditText = mock(EditText.class);
        mMockTextInputLayout = mock(TextInputLayout.class);
        mMockConfirmationTextInputLayout = mock(TextInputLayout.class);
        mMockView = mock(View.class);
        Pattern mockPattern = PowerMock.createMock(Pattern.class);
        NumericRange mockNumericRange = mock(NumericRange.class);
        SimpleCustomValidation mockSimpleCustomValidation = mock(SimpleCustomValidation.class);
        CustomValidation mockCustomValidation = mock(CustomValidation.class);
        CustomValidationCallback mockCustomValidationCallback = mock(CustomValidationCallback.class);
        mMockCustomErrorReset = mock(CustomErrorReset.class);
        String mockErrMsg = PowerMock.createMock(String.class);
        mValidationHolderRegexTypeWithEditText = new ValidationHolder(mMockEditText, mockPattern, mockErrMsg);
        mValidationHolderRangeTypeWithEditText = new ValidationHolder(mMockEditText, mockNumericRange, mockErrMsg);
        mValidationHolderConfirmationTypeWithEditText = new ValidationHolder(mMockConfirmationEditText, mMockEditText, mockErrMsg);
        mValidationHolderSimpleCustomTypeWithEditText = new ValidationHolder(mMockEditText, mockSimpleCustomValidation, mockErrMsg);
        mValidationHolderRegexTypeWithTextInputLayout = new ValidationHolder(mMockTextInputLayout, mockPattern, mockErrMsg);
        mValidationHolderRangeTypeWithTextInputLayout = new ValidationHolder(mMockTextInputLayout, mockNumericRange, mockErrMsg);
        mValidationHolderConfirmationTypeWithTextInputLayout = new ValidationHolder(mMockConfirmationTextInputLayout, mMockTextInputLayout, mockErrMsg);
        mValidationHolderSimpleCustomTypeWithTextInputLayout = new ValidationHolder(mMockTextInputLayout, mockSimpleCustomValidation, mockErrMsg);
        mValidationHolderCustomTypeWithSomeSortOfView = new ValidationHolder(mMockView, mockCustomValidation, mockCustomValidationCallback, mMockCustomErrorReset, mockErrMsg);
    }

    public void testIsRegexTypeTrue() {
        assertTrue(mValidationHolderRegexTypeWithEditText.isRegexType());
        assertTrue(mValidationHolderRegexTypeWithTextInputLayout.isRegexType());
    }

    public void testIsRegexTypeFalse() {
        assertFalse(mValidationHolderRangeTypeWithEditText.isRegexType());
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isRegexType());
        assertFalse(mValidationHolderSimpleCustomTypeWithEditText.isRegexType());
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isRegexType());
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isRegexType());
        assertFalse(mValidationHolderSimpleCustomTypeWithTextInputLayout.isRegexType());
        assertFalse(mValidationHolderCustomTypeWithSomeSortOfView.isRegexType());
    }

    public void testIsRangeTypeTrue() {
        assertTrue(mValidationHolderRangeTypeWithEditText.isRangeType());
        assertTrue(mValidationHolderRangeTypeWithTextInputLayout.isRangeType());
    }

    public void testIsRangeTypeFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isRangeType());
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isRangeType());
        assertFalse(mValidationHolderSimpleCustomTypeWithEditText.isRangeType());
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isRangeType());
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isRangeType());
        assertFalse(mValidationHolderSimpleCustomTypeWithTextInputLayout.isRangeType());
        assertFalse(mValidationHolderCustomTypeWithSomeSortOfView.isRangeType());
    }

    public void testIsConfirmationTypeTrue() {
        assertTrue(mValidationHolderConfirmationTypeWithEditText.isConfirmationType());
        assertTrue(mValidationHolderConfirmationTypeWithTextInputLayout.isConfirmationType());
    }

    public void testIsConfirmationTypeFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isConfirmationType());
        assertFalse(mValidationHolderRangeTypeWithEditText.isConfirmationType());
        assertFalse(mValidationHolderSimpleCustomTypeWithEditText.isConfirmationType());
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isConfirmationType());
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isConfirmationType());
        assertFalse(mValidationHolderSimpleCustomTypeWithTextInputLayout.isConfirmationType());
        assertFalse(mValidationHolderCustomTypeWithSomeSortOfView.isConfirmationType());
    }

    public void testIsSimpleCustomTypeTrue() {
        assertTrue(mValidationHolderSimpleCustomTypeWithEditText.isSimpleCustomType());
        assertTrue(mValidationHolderSimpleCustomTypeWithTextInputLayout.isSimpleCustomType());
    }

    public void testIsSimpleCustomTypeFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isSimpleCustomType());
        assertFalse(mValidationHolderRangeTypeWithEditText.isSimpleCustomType());
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isSimpleCustomType());
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isSimpleCustomType());
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isSimpleCustomType());
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isSimpleCustomType());
        assertFalse(mValidationHolderCustomTypeWithSomeSortOfView.isSimpleCustomType());
    }

    public void testIsCustomTypeTrue() {
        assertTrue(mValidationHolderCustomTypeWithSomeSortOfView.isCustomType());
    }

    public void testIsCustomTypeFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isCustomType());
        assertFalse(mValidationHolderRangeTypeWithEditText.isCustomType());
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isCustomType());
        assertFalse(mValidationHolderSimpleCustomTypeWithEditText.isCustomType());
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isCustomType());
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isCustomType());
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isCustomType());
        assertFalse(mValidationHolderSimpleCustomTypeWithTextInputLayout.isCustomType());
    }

    public void testIsEditTextViewTrue() {
        assertTrue(mValidationHolderRegexTypeWithEditText.isEditTextView());
        assertTrue(mValidationHolderRangeTypeWithEditText.isEditTextView());
        assertTrue(mValidationHolderConfirmationTypeWithEditText.isEditTextView());
        assertTrue(mValidationHolderSimpleCustomTypeWithEditText.isEditTextView());
    }

    public void testIsEditTextViewFalse() {
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isEditTextView());
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isEditTextView());
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isEditTextView());
        assertFalse(mValidationHolderSimpleCustomTypeWithTextInputLayout.isEditTextView());
        assertFalse(mValidationHolderCustomTypeWithSomeSortOfView.isEditTextView());
    }

    public void testIsTextInputLayoutViewTrue() {
        assertTrue(mValidationHolderRegexTypeWithTextInputLayout.isTextInputLayoutView());
        assertTrue(mValidationHolderRangeTypeWithTextInputLayout.isTextInputLayoutView());
        assertTrue(mValidationHolderConfirmationTypeWithTextInputLayout.isTextInputLayoutView());
        assertTrue(mValidationHolderSimpleCustomTypeWithTextInputLayout.isTextInputLayoutView());
    }

    public void testIsTextInputLayoutViewFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isTextInputLayoutView());
        assertFalse(mValidationHolderRangeTypeWithEditText.isTextInputLayoutView());
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isTextInputLayoutView());
        assertFalse(mValidationHolderSimpleCustomTypeWithEditText.isTextInputLayoutView());
        assertFalse(mValidationHolderCustomTypeWithSomeSortOfView.isTextInputLayoutView());
    }

    public void testIsSomeSortOfViewTrue() {
        assertTrue(mValidationHolderCustomTypeWithSomeSortOfView.isSomeSortOfView());
    }

    public void testIsSomeSortOfViewFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isSomeSortOfView());
        assertFalse(mValidationHolderRangeTypeWithEditText.isSomeSortOfView());
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isSomeSortOfView());
        assertFalse(mValidationHolderSimpleCustomTypeWithEditText.isSomeSortOfView());
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isSomeSortOfView());
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isSomeSortOfView());
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isSomeSortOfView());
        assertFalse(mValidationHolderSimpleCustomTypeWithTextInputLayout.isSomeSortOfView());
    }

    public void testGetTextFromEditText() {
        String text = "OK";
        Editable mockEditable = mock(Editable.class);
        when(mMockEditText.getText()).thenReturn(mockEditable);
        when(mockEditable.toString()).thenReturn(text);
        assertEquals(text, mValidationHolderRegexTypeWithEditText.getText());
        assertEquals(text, mValidationHolderRangeTypeWithEditText.getText());
        assertEquals(text, mValidationHolderConfirmationTypeWithEditText.getText());
        assertEquals(text, mValidationHolderSimpleCustomTypeWithEditText.getText());
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
        assertEquals(text, mValidationHolderSimpleCustomTypeWithTextInputLayout.getText());
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
        assertNull(mValidationHolderSimpleCustomTypeWithEditText.getConfirmationText());
        assertNull(mValidationHolderRegexTypeWithTextInputLayout.getConfirmationText());
        assertNull(mValidationHolderRangeTypeWithTextInputLayout.getConfirmationText());
        assertNull(mValidationHolderSimpleCustomTypeWithTextInputLayout.getConfirmationText());
        assertNull(mValidationHolderCustomTypeWithSomeSortOfView.getConfirmationText());
    }

    public void testGetEditTextFromEditText() {
        assertEquals(mMockEditText, mValidationHolderRegexTypeWithEditText.getEditText());
        assertEquals(mMockEditText, mValidationHolderRangeTypeWithEditText.getEditText());
        assertEquals(mMockConfirmationEditText, mValidationHolderConfirmationTypeWithEditText.getEditText());
        assertEquals(mMockEditText, mValidationHolderSimpleCustomTypeWithEditText.getEditText());
    }

    public void testGetEditTextFromTextInputLayout() {
        EditText mockEditText = mock(EditText.class);
        EditText mockConfirmationEditText = mock(EditText.class);
        when(mMockTextInputLayout.getEditText()).thenReturn(mockEditText);
        when(mMockConfirmationTextInputLayout.getEditText()).thenReturn(mockConfirmationEditText);
        assertEquals(mockEditText, mValidationHolderRegexTypeWithTextInputLayout.getEditText());
        assertEquals(mockEditText, mValidationHolderRangeTypeWithTextInputLayout.getEditText());
        assertEquals(mockConfirmationEditText, mValidationHolderConfirmationTypeWithTextInputLayout.getEditText());
        assertEquals(mockEditText, mValidationHolderSimpleCustomTypeWithTextInputLayout.getEditText());
    }

    public void testGetTextInputLayout() {
        assertEquals(mMockTextInputLayout, mValidationHolderRegexTypeWithTextInputLayout.getTextInputLayout());
        assertEquals(mMockTextInputLayout, mValidationHolderRangeTypeWithTextInputLayout.getTextInputLayout());
        assertEquals(mMockConfirmationTextInputLayout, mValidationHolderConfirmationTypeWithTextInputLayout.getTextInputLayout());
        assertEquals(mMockTextInputLayout, mValidationHolderSimpleCustomTypeWithTextInputLayout.getTextInputLayout());
    }

    public void testGetTextInputLayoutReturnsNull() {
        assertNull(mValidationHolderRegexTypeWithEditText.getTextInputLayout());
        assertNull(mValidationHolderRangeTypeWithEditText.getTextInputLayout());
        assertNull(mValidationHolderConfirmationTypeWithEditText.getTextInputLayout());
        assertNull(mValidationHolderSimpleCustomTypeWithEditText.getTextInputLayout());
        assertNull(mValidationHolderCustomTypeWithSomeSortOfView.getTextInputLayout());
    }

    public void testGetView() {
        assertEquals(mMockView, mValidationHolderCustomTypeWithSomeSortOfView.getView());
    }

    public void testGetViewReturnsNull() {
        assertNull(mValidationHolderRegexTypeWithEditText.getView());
        assertNull(mValidationHolderRangeTypeWithEditText.getView());
        assertNull(mValidationHolderConfirmationTypeWithEditText.getView());
        assertNull(mValidationHolderSimpleCustomTypeWithEditText.getView());
        assertNull(mValidationHolderRegexTypeWithTextInputLayout.getView());
        assertNull(mValidationHolderRangeTypeWithTextInputLayout.getView());
        assertNull(mValidationHolderConfirmationTypeWithTextInputLayout.getView());
        assertNull(mValidationHolderSimpleCustomTypeWithTextInputLayout.getView());
    }

    public void testIsVisibleTrue() {
        when(mMockEditText.getVisibility()).thenReturn(View.VISIBLE);
        when(mMockTextInputLayout.getVisibility()).thenReturn(View.VISIBLE);
        when(mMockView.getVisibility()).thenReturn(View.VISIBLE);
        assertTrue(mValidationHolderRegexTypeWithEditText.isVisible());
        assertTrue(mValidationHolderRangeTypeWithTextInputLayout.isVisible());
        assertTrue(mValidationHolderCustomTypeWithSomeSortOfView.isVisible());
    }

    public void testIsVisibleFalse() {
        when(mMockConfirmationEditText.getVisibility()).thenReturn(View.INVISIBLE);
        when(mMockTextInputLayout.getVisibility()).thenReturn(View.GONE);
        when(mMockView.getVisibility()).thenReturn(View.INVISIBLE);
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isVisible());
        assertFalse(mValidationHolderSimpleCustomTypeWithTextInputLayout.isVisible());
        assertFalse(mValidationHolderCustomTypeWithSomeSortOfView.isVisible());
    }

    public void testResetCustomError() {
        mValidationHolderCustomTypeWithSomeSortOfView.resetCustomError();
        verify(mMockCustomErrorReset, times(1)).reset(mValidationHolderCustomTypeWithSomeSortOfView);
    }

}