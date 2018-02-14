package com.basgeekball.awesomevalidation.validators;

import android.widget.EditText;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.regex.Matcher;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class BasicValidatorTest extends TestCase {

    private BasicValidator mSpiedBasicValidator;
    private ValidationHolder mMockValidationHolder;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSpiedBasicValidator = spy(BasicValidator.class);
        mMockValidationHolder = mock(ValidationHolder.class);
        mSpiedBasicValidator.mValidationHolderList.add(mMockValidationHolder);
    }

    public void testValidationCallbackExecute() {
        ValidationCallback validationCallback = Whitebox.getInternalState(mSpiedBasicValidator, "mValidationCallback");
        Matcher mockMatcher = PowerMockito.mock(Matcher.class);
        EditText mockEditText = mock(EditText.class);
        String mockErrMsg = PowerMockito.mock(String.class);
        when(mMockValidationHolder.getEditText()).thenReturn(mockEditText);
        when(mMockValidationHolder.getErrMsg()).thenReturn(mockErrMsg);
        doNothing().when(mockEditText).setError(mockErrMsg);
        validationCallback.execute(mMockValidationHolder, mockMatcher);
        verify(mockEditText).setError(mockErrMsg);
    }

    public void testHalt() {
        EditText mockEditText = mock(EditText.class);
        when(mMockValidationHolder.getEditText()).thenReturn(mockEditText);
        mSpiedBasicValidator.halt();
        verify(mockEditText).setError(null);
    }

    public void testHaltWithSomeSortOfView() {
        ValidationHolder mockValidationHolder = mock(ValidationHolder.class);
        when(mockValidationHolder.isSomeSortOfView()).thenReturn(true);
        mSpiedBasicValidator.mValidationHolderList.clear();
        mSpiedBasicValidator.mValidationHolderList.add(mockValidationHolder);
        mSpiedBasicValidator.halt();
        verify(mockValidationHolder).resetCustomError();
    }

}
