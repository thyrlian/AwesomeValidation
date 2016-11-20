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
    private ValidationHolder mMockedValidationHolder;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSpiedBasicValidator = spy(BasicValidator.class);
        mMockedValidationHolder = mock(ValidationHolder.class);
        mSpiedBasicValidator.mValidationHolderList.add(mMockedValidationHolder);
    }

    public void testValidationCallbackExecute() {
        ValidationCallback validationCallback = Whitebox.getInternalState(mSpiedBasicValidator, "mValidationCallback");
        Matcher mockedMatcher = PowerMockito.mock(Matcher.class);
        EditText mockedEditText = mock(EditText.class);
        String mockedErrMsg = PowerMockito.mock(String.class);
        when(mMockedValidationHolder.getEditText()).thenReturn(mockedEditText);
        when(mMockedValidationHolder.getErrMsg()).thenReturn(mockedErrMsg);
        doNothing().when(mockedEditText).setError(mockedErrMsg);
        validationCallback.execute(mMockedValidationHolder, mockedMatcher);
        verify(mockedEditText).setError(mockedErrMsg);
    }

    public void testHalt() {
        EditText mockedEditText = mock(EditText.class);
        when(mMockedValidationHolder.getEditText()).thenReturn(mockedEditText);
        mSpiedBasicValidator.halt();
        verify(mockedEditText).setError(null);
    }

}
