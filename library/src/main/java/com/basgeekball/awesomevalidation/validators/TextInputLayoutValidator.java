package com.basgeekball.awesomevalidation.validators;

import android.support.design.widget.TextInputLayout;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;

import java.util.regex.Matcher;

public class TextInputLayoutValidator extends Validator {

    private ValidationCallback mValidationCallback = new ValidationCallback() {
        @Override
        public void execute(ValidationHolder validationHolder, Matcher matcher) {
            TextInputLayout textInputLayout = validationHolder.getTextInputLayout();
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(validationHolder.getErrMsg());
        }
    };

    @Override
    public boolean trigger() {
        halt();
        return checkFields(mValidationCallback);
    }

    @Override
    public void halt() {
        for (ValidationHolder validationHolder : mValidationHolderList) {
            validationHolder.getTextInputLayout().setError(null);
        }
    }

}
