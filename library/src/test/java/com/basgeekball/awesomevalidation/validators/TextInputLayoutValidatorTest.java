package com.basgeekball.awesomevalidation.validators;

import android.support.design.widget.TextInputLayout;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;

import junit.framework.TestCase;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.util.regex.Matcher;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TextInputLayoutValidatorTest extends TestCase {

    private TextInputLayoutValidator mSpiedTextInputLayoutValidator;
    private ValidationHolder mMockedValidationHolder;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSpiedTextInputLayoutValidator = spy(TextInputLayoutValidator.class);
        mMockedValidationHolder = mock(ValidationHolder.class);
        mSpiedTextInputLayoutValidator.mValidationHolderList.add(mMockedValidationHolder);
    }

    public void testValidationCallbackExecute() {
        ValidationCallback validationCallback = Whitebox.getInternalState(mSpiedTextInputLayoutValidator, "mValidationCallback");
        Matcher mockedMatcher = PowerMockito.mock(Matcher.class);
        TextInputLayout mockedTextInputLayout = mock(TextInputLayout.class);
        String mockedErrMsg = PowerMockito.mock(String.class);
        when(mMockedValidationHolder.getTextInputLayout()).thenReturn(mockedTextInputLayout);
        when(mMockedValidationHolder.getErrMsg()).thenReturn(mockedErrMsg);
        validationCallback.execute(mMockedValidationHolder, mockedMatcher);
        verify(mockedTextInputLayout).setErrorEnabled(true);
        verify(mockedTextInputLayout).setError(mockedErrMsg);
    }

    public void testHalt() {
        TextInputLayout mockedTextInputLayout = mock(TextInputLayout.class);
        when(mMockedValidationHolder.getTextInputLayout()).thenReturn(mockedTextInputLayout);
        mSpiedTextInputLayoutValidator.halt();
        verify(mockedTextInputLayout).setError(null);
    }

}
