package com.basgeekball.awesomevalidation.validators;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;

import java.util.regex.Matcher;

public class BasicValidator extends Validator {

    @Override
    public boolean trigger() {
        return checkFields(new ValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder, Matcher matcher) {
                validationHolder.getEditText().setError(validationHolder.getErrMsg());
            }
        });
    }

    @Override
    public void halt() {
        for (ValidationHolder validationHolder : mValidationHolderList) {
            validationHolder.getEditText().setError(null);
        }
    }

}