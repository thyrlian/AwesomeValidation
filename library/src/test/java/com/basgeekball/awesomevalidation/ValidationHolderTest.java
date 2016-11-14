package com.basgeekball.awesomevalidation;

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
    private ValidationHolder mValidationHolderRegexType;
    private ValidationHolder mValidationHolderRangeType;
    private ValidationHolder mValidationHolderConfirmationType;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockedEditText = mock(EditText.class);
        mMockedConfirmationEditText = mock(EditText.class);
        Pattern mockedPattern = PowerMock.createMock(Pattern.class);
        NumericRange mockedNumericRange = mock(NumericRange.class);
        String mockedErrMsg = PowerMock.createMock(String.class);
        mValidationHolderRegexType = new ValidationHolder(mMockedEditText, mockedPattern, mockedErrMsg);
        mValidationHolderRangeType = new ValidationHolder(mMockedEditText, mockedNumericRange, mockedErrMsg);
        mValidationHolderConfirmationType = new ValidationHolder(mMockedEditText, mMockedConfirmationEditText, mockedErrMsg);
    }

    public void testIsRegexTypeTrue() {
        assertTrue(mValidationHolderRegexType.isRegexType());
    }

    public void testIsRegexTypeFalse() {
        assertFalse(mValidationHolderRangeType.isRegexType());
        assertFalse(mValidationHolderConfirmationType.isRegexType());
    }

    public void testIsRangeTypeTrue() {
        assertTrue(mValidationHolderRangeType.isRangeType());
    }

    public void testIsRangeTypeFalse() {
        assertFalse(mValidationHolderRegexType.isRangeType());
        assertFalse(mValidationHolderConfirmationType.isRangeType());
    }

    public void testIsConfirmationTypeTrue() {
        assertTrue(mValidationHolderConfirmationType.isConfirmationType());
    }

    public void testIsConfirmationTypeFalse() {
        assertFalse(mValidationHolderRangeType.isConfirmationType());
        assertFalse(mValidationHolderRegexType.isConfirmationType());

    }

    public void testGetText() {
        String text = "OK";
        Editable mockedEditable = mock(Editable.class);
        when(mMockedEditText.getText()).thenReturn(mockedEditable);
        when(mockedEditable.toString()).thenReturn(text);
        assertEquals(text, mValidationHolderRegexType.getText());
        assertEquals(text, mValidationHolderRangeType.getText());
        assertEquals(text, mValidationHolderConfirmationType.getText());
    }

    public void testGetConfirmationText() {
        String text = "OK";
        Editable mockedEditable = mock(Editable.class);
        when(mMockedConfirmationEditText.getText()).thenReturn(mockedEditable);
        when(mockedEditable.toString()).thenReturn(text);
        assertEquals(text, mValidationHolderConfirmationType.getConfirmationText());
    }

}
