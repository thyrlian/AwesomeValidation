package com.basgeekball.awesomevalidation;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.model.NumericRange;
import com.basgeekball.awesomevalidation.validators.BasicValidator;
import com.basgeekball.awesomevalidation.validators.ColorationValidator;
import com.basgeekball.awesomevalidation.validators.UnderlabelValidator;
import com.google.common.collect.Range;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.regex.Pattern;

import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AwesomeValidation.class, Range.class})
public class AwesomeValidationTest extends TestCase {

    private BasicValidator mSpiedBasicValidator;
    private ColorationValidator mSpiedColorationValidator;
    private UnderlabelValidator mSpiedUnderlabelValidator;
    private AwesomeValidation mSpiedAwesomeValidationBasicStyle;
    private AwesomeValidation mSpiedAwesomeValidationColorationStyle;
    private AwesomeValidation mSpiedAwesomeValidationUnderlabelStyle;
    private Context mMockedContext;
    private int mColor = 256;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSpiedBasicValidator = spy(BasicValidator.class);
        mSpiedColorationValidator = spy(ColorationValidator.class);
        mSpiedUnderlabelValidator = spy(UnderlabelValidator.class);
        mSpiedAwesomeValidationBasicStyle = spy(new AwesomeValidation(ValidationStyle.BASIC));
        mSpiedAwesomeValidationColorationStyle = spy(new AwesomeValidation(ValidationStyle.COLORATION));
        mSpiedAwesomeValidationUnderlabelStyle = spy(new AwesomeValidation(ValidationStyle.UNDERLABEL));
        MemberModifier.field(AwesomeValidation.class, "mValidator").set(mSpiedAwesomeValidationBasicStyle, mSpiedBasicValidator);
        MemberModifier.field(AwesomeValidation.class, "mValidator").set(mSpiedAwesomeValidationColorationStyle, mSpiedColorationValidator);
        MemberModifier.field(AwesomeValidation.class, "mValidator").set(mSpiedAwesomeValidationUnderlabelStyle, mSpiedUnderlabelValidator);
        mMockedContext = mock(Context.class);
    }

    public void testAwesomeValidationConstructBasicStyle() {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        assertTrue(Whitebox.getInternalState(awesomeValidation, "mValidator") instanceof BasicValidator);
    }

    public void testAwesomeValidationConstructColorationStyle() {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.COLORATION);
        assertTrue(Whitebox.getInternalState(awesomeValidation, "mValidator") instanceof ColorationValidator);
    }

    public void testAwesomeValidationConstructUnderlabelValidatorStyle() {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        assertTrue(Whitebox.getInternalState(awesomeValidation, "mValidator") instanceof UnderlabelValidator);
    }

    public void testSetContextForUnderlabelStyle() throws Exception {
        mSpiedAwesomeValidationUnderlabelStyle.setContext(mMockedContext);
        PowerMockito.verifyPrivate(mSpiedUnderlabelValidator, times(1)).invoke("setContext", mMockedContext);
    }

    public void testSetContextForNonUnderlabelStyle() throws Exception {
        doThrow(UnsupportedOperationException.class).when(mSpiedAwesomeValidationBasicStyle).setContext(mMockedContext);
    }

    public void testSetColorForColorationStyle() throws Exception {
        mSpiedAwesomeValidationColorationStyle.setColor(mColor);
        PowerMockito.verifyPrivate(mSpiedColorationValidator, times(1)).invoke("setColor", mColor);
    }

    public void testSetColorForNonColorationStyle() throws Exception {
        doThrow(UnsupportedOperationException.class).when(mSpiedAwesomeValidationBasicStyle).setColor(mColor);
    }

    public void testAddValidation() throws Exception {
        Activity mockedActivity = mock(Activity.class, RETURNS_MOCKS);
        EditText mockedEditText = mock(EditText.class, RETURNS_MOCKS);
        Pattern mockedPattern = PowerMockito.mock(Pattern.class, RETURNS_MOCKS);
        Range mockedRange = PowerMockito.mock(Range.class, RETURNS_MOCKS);
        String mockedRegex = PowerMockito.mock(String.class);
        String mockedErrMsg = PowerMockito.mock(String.class);
        NumericRange mockedNumericRange = mock(NumericRange.class);
        int viewId = 65536;
        int errMsgId = 32768;
        when(mockedActivity.findViewById(viewId)).thenReturn(mockedEditText);
        PowerMockito.whenNew(NumericRange.class).withArguments(mockedRange).thenReturn(mockedNumericRange);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockedEditText, mockedRegex, mockedErrMsg);
        PowerMockito.verifyPrivate(mSpiedBasicValidator, times(1)).invoke("set", mockedEditText, mockedRegex, mockedErrMsg);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockedActivity, viewId, mockedRegex, errMsgId);
        PowerMockito.verifyPrivate(mSpiedBasicValidator, times(1)).invoke("set", mockedActivity, viewId, mockedRegex, errMsgId);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockedEditText, mockedPattern, mockedErrMsg);
        PowerMockito.verifyPrivate(mSpiedBasicValidator, times(1)).invoke("set", mockedEditText, mockedPattern, mockedErrMsg);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockedActivity, viewId, mockedPattern, errMsgId);
        PowerMockito.verifyPrivate(mSpiedBasicValidator, times(1)).invoke("set", mockedActivity, viewId, mockedPattern, errMsgId);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockedEditText, mockedRange, mockedErrMsg);
        PowerMockito.verifyPrivate(mSpiedBasicValidator, times(1)).invoke("set", mockedEditText, new NumericRange(mockedRange), mockedErrMsg);

        mSpiedAwesomeValidationBasicStyle.addValidation(mockedActivity, viewId, mockedRange, errMsgId);
        PowerMockito.verifyPrivate(mSpiedBasicValidator, times(1)).invoke("set", mockedActivity, viewId, new NumericRange(mockedRange), errMsgId);
    }

    public void testValidate() throws Exception {
        mSpiedAwesomeValidationBasicStyle.validate();
        PowerMockito.verifyPrivate(mSpiedBasicValidator, times(1)).invoke("trigger");
        assertEquals(Whitebox.invokeMethod(mSpiedBasicValidator, "trigger"), mSpiedAwesomeValidationBasicStyle.validate());
    }

    public void testClear() throws Exception {
        mSpiedAwesomeValidationBasicStyle.clear();
        PowerMockito.verifyPrivate(mSpiedBasicValidator, times(1)).invoke("halt");
    }

}
