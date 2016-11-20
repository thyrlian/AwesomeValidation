package com.basgeekball.awesomevalidation.validators;

import android.text.SpannableStringBuilder;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.helper.SpanHelper;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.regex.Matcher;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ColorationValidator.class, SpanHelper.class})
public class ColorationValidatorTest extends TestCase {

    private ColorationValidator mSpiedColorationValidator;
    private ValidationHolder mMockedValidationHolder;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSpiedColorationValidator = spy(ColorationValidator.class);
        mMockedValidationHolder = mock(ValidationHolder.class);
        mSpiedColorationValidator.mValidationHolderList.add(mMockedValidationHolder);
    }

    public void testValidationCallbackExecute() {
        ValidationCallback validationCallback = Whitebox.getInternalState(mSpiedColorationValidator, "mValidationCallback");
        Matcher mockedMatcher = PowerMockito.mock(Matcher.class);
        EditText mockedEditText = mock(EditText.class);
        String mockedErrMsg = PowerMockito.mock(String.class);
        SpannableStringBuilder mockedEditable = PowerMockito.mock(SpannableStringBuilder.class);
        when(mMockedValidationHolder.getEditText()).thenReturn(mockedEditText);
        when(mMockedValidationHolder.getErrMsg()).thenReturn(mockedErrMsg);
        when(mockedEditText.getText()).thenReturn(mockedEditable);
        when(mockedEditable.length()).thenReturn(PowerMockito.mock(Integer.class));
        PowerMockito.mockStatic(SpanHelper.class);
        validationCallback.execute(mMockedValidationHolder, mockedMatcher);
        verify(mockedEditText).setError(mockedErrMsg);
        PowerMockito.verifyStatic();
    }

    public void testHalt() {
        EditText mockedEditText = mock(EditText.class);
        when(mMockedValidationHolder.getEditText()).thenReturn(mockedEditText);
        PowerMockito.mockStatic(SpanHelper.class);
        mSpiedColorationValidator.halt();
        verify(mockedEditText).setError(null);
        PowerMockito.verifyStatic();
    }

}
