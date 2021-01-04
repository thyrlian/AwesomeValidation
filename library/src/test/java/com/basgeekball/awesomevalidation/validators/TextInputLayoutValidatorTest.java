package com.basgeekball.awesomevalidation.validators;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;
import com.google.android.material.textfield.TextInputLayout;

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
    private ValidationHolder mMockValidationHolder;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSpiedTextInputLayoutValidator = spy(TextInputLayoutValidator.class);
        mMockValidationHolder = mock(ValidationHolder.class);
        mSpiedTextInputLayoutValidator.mValidationHolderList.add(mMockValidationHolder);
    }

    public void testValidationCallbackExecute() {
        ValidationCallback validationCallback = Whitebox.getInternalState(mSpiedTextInputLayoutValidator, "mValidationCallback");
        Matcher mockMatcher = PowerMockito.mock(Matcher.class);
        TextInputLayout mockTextInputLayout = mock(TextInputLayout.class);
        String mockErrMsg = PowerMockito.mock(String.class);
        when(mMockValidationHolder.getTextInputLayout()).thenReturn(mockTextInputLayout);
        when(mMockValidationHolder.getErrMsg()).thenReturn(mockErrMsg);
        validationCallback.execute(mMockValidationHolder, mockMatcher);
        verify(mockTextInputLayout).setErrorEnabled(true);
        verify(mockTextInputLayout).setError(mockErrMsg);
    }

    public void testHalt() {
        TextInputLayout mockTextInputLayout = mock(TextInputLayout.class);
        when(mMockValidationHolder.getTextInputLayout()).thenReturn(mockTextInputLayout);
        mSpiedTextInputLayoutValidator.halt();
        verify(mockTextInputLayout).setErrorEnabled(false);
        verify(mockTextInputLayout).setError(null);
    }

    public void testHaltWithSomeSortOfView() {
        ValidationHolder mockValidationHolder = mock(ValidationHolder.class);
        when(mockValidationHolder.isSomeSortOfView()).thenReturn(true);
        mSpiedTextInputLayoutValidator.mValidationHolderList.clear();
        mSpiedTextInputLayoutValidator.mValidationHolderList.add(mockValidationHolder);
        mSpiedTextInputLayoutValidator.halt();
        verify(mockValidationHolder).resetCustomError();
    }

}
