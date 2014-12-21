package com.strohwitwer.awesomevalidation.validator;

import com.strohwitwer.awesomevalidation.ValidationHolder;
import com.strohwitwer.awesomevalidation.utils.ValidationCallback;

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

}