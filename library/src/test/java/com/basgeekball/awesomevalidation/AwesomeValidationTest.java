package com.basgeekball.awesomevalidation;

import com.basgeekball.awesomevalidation.validators.BasicValidator;
import com.basgeekball.awesomevalidation.validators.ColorationValidator;
import com.basgeekball.awesomevalidation.validators.UnderlabelValidator;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

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

}
