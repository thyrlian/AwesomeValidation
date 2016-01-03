package com.basgeekball.awesomevalidation;

import android.content.Context;

import com.basgeekball.awesomevalidation.validators.BasicValidator;
import com.basgeekball.awesomevalidation.validators.ColorationValidator;
import com.basgeekball.awesomevalidation.validators.UnderlabelValidator;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AwesomeValidation.class)
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
