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
        UnderlabelValidator spiedUnderlabelValidator = spy(UnderlabelValidator.class);
        AwesomeValidation spiedAwesomeValidation = spy(new AwesomeValidation(ValidationStyle.UNDERLABEL));
        MemberModifier.field(AwesomeValidation.class, "mValidator").set(spiedAwesomeValidation, spiedUnderlabelValidator);
        Context mockedContext = mock(Context.class);
        spiedAwesomeValidation.setContext(mockedContext);
        PowerMockito.verifyPrivate(spiedUnderlabelValidator, times(1)).invoke("setContext", mockedContext);
    }

    public void testSetContextForNonUnderlabelStyle() throws Exception {
        BasicValidator spiedBasicValidator = spy(BasicValidator.class);
        AwesomeValidation spiedAwesomeValidation = spy(new AwesomeValidation(ValidationStyle.BASIC));
        MemberModifier.field(AwesomeValidation.class, "mValidator").set(spiedAwesomeValidation, spiedBasicValidator);
        Context mockedContext = mock(Context.class);
        doThrow(UnsupportedOperationException.class).when(spiedAwesomeValidation).setContext(mockedContext);
    }

    public void testSetColorForColorationStyle() throws Exception {
        ColorationValidator spiedColorationValidator = spy(ColorationValidator.class);
        AwesomeValidation spiedAwesomeValidation = spy(new AwesomeValidation(ValidationStyle.COLORATION));
        MemberModifier.field(AwesomeValidation.class, "mValidator").set(spiedAwesomeValidation, spiedColorationValidator);
        int color = 256;
        spiedAwesomeValidation.setColor(color);
        PowerMockito.verifyPrivate(spiedColorationValidator, times(1)).invoke("setColor", color);
    }

    public void testSetColorForNonColorationStyle() throws Exception {
        BasicValidator spiedBasicValidator = spy(BasicValidator.class);
        AwesomeValidation spiedAwesomeValidation = spy(new AwesomeValidation(ValidationStyle.BASIC));
        MemberModifier.field(AwesomeValidation.class, "mValidator").set(spiedAwesomeValidation, spiedBasicValidator);
        int color = 256;
        doThrow(UnsupportedOperationException.class).when(spiedAwesomeValidation).setColor(color);
    }

    public void testAddValidation() throws Exception {
        BasicValidator spiedValidator = spy(BasicValidator.class);
        AwesomeValidation spiedAwesomeValidation = spy(new AwesomeValidation(ValidationStyle.BASIC));
        MemberModifier.field(AwesomeValidation.class, "mValidator").set(spiedAwesomeValidation, spiedValidator);

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

        spiedAwesomeValidation.addValidation(mockedEditText, mockedRegex, mockedErrMsg);
        PowerMockito.verifyPrivate(spiedValidator, times(1)).invoke("set", mockedEditText, mockedRegex, mockedErrMsg);

        spiedAwesomeValidation.addValidation(mockedActivity, viewId, mockedRegex, errMsgId);
        PowerMockito.verifyPrivate(spiedValidator, times(1)).invoke("set", mockedActivity, viewId, mockedRegex, errMsgId);

        spiedAwesomeValidation.addValidation(mockedEditText, mockedPattern, mockedErrMsg);
        PowerMockito.verifyPrivate(spiedValidator, times(1)).invoke("set", mockedEditText, mockedPattern, mockedErrMsg);

        spiedAwesomeValidation.addValidation(mockedActivity, viewId, mockedPattern, errMsgId);
        PowerMockito.verifyPrivate(spiedValidator, times(1)).invoke("set", mockedActivity, viewId, mockedPattern, errMsgId);

        spiedAwesomeValidation.addValidation(mockedEditText, mockedRange, mockedErrMsg);
        PowerMockito.verifyPrivate(spiedValidator, times(1)).invoke("set", mockedEditText, new NumericRange(mockedRange), mockedErrMsg);

        spiedAwesomeValidation.addValidation(mockedActivity, viewId, mockedRange, errMsgId);
        PowerMockito.verifyPrivate(spiedValidator, times(1)).invoke("set", mockedActivity, viewId, new NumericRange(mockedRange), errMsgId);
    }

    public void testValidate() throws Exception {
        BasicValidator spiedValidator = spy(BasicValidator.class);
        AwesomeValidation spiedAwesomeValidation = spy(new AwesomeValidation(ValidationStyle.BASIC));
        MemberModifier.field(AwesomeValidation.class, "mValidator").set(spiedAwesomeValidation, spiedValidator);
        spiedAwesomeValidation.validate();
        PowerMockito.verifyPrivate(spiedValidator, times(1)).invoke("trigger");
        assertEquals(Whitebox.invokeMethod(spiedValidator, "trigger"), spiedAwesomeValidation.validate());
    }

    public void testClear() throws Exception {
        BasicValidator spiedValidator = spy(BasicValidator.class);
        AwesomeValidation spiedAwesomeValidation = spy(new AwesomeValidation(ValidationStyle.BASIC));
        MemberModifier.field(AwesomeValidation.class, "mValidator").set(spiedAwesomeValidation, spiedValidator);
        spiedAwesomeValidation.clear();
        PowerMockito.verifyPrivate(spiedValidator, times(1)).invoke("halt");
    }

}
