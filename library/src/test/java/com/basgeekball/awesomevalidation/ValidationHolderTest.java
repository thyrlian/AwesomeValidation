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
    private ValidationHolder mValidationHolderRegexType;
    private ValidationHolder mValidationHolderRangeType;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockedEditText = mock(EditText.class);
        Pattern mockedPattern = PowerMock.createMock(Pattern.class);
        NumericRange mockedNumericRange = mock(NumericRange.class);
        String mockedErrMsg = PowerMock.createMock(String.class);
        mValidationHolderRegexType = new ValidationHolder(mMockedEditText, mockedPattern, mockedErrMsg);
        mValidationHolderRangeType = new ValidationHolder(mMockedEditText, mockedNumericRange, mockedErrMsg);
    }

    public void testIsRegexTypeTrue() {
        assertTrue(mValidationHolderRegexType.isRegexType());
    }

    public void testIsRegexTypeFalse() {
        assertFalse(mValidationHolderRangeType.isRegexType());
    }

    public void testIsRangeTypeTrue() {
        assertTrue(mValidationHolderRangeType.isRangeType());
    }

    public void testIsRangeTypeFalse() {
        assertFalse(mValidationHolderRegexType.isRangeType());
    }

    public void testGetText() {
        String text = "OK";
        Editable mockedEditable = mock(Editable.class);
        when(mMockedEditText.getText()).thenReturn(mockedEditable);
        when(mockedEditable.toString()).thenReturn(text);
        assertEquals(text, mValidationHolderRegexType.getText());
        assertEquals(text, mValidationHolderRangeType.getText());
    }

}
