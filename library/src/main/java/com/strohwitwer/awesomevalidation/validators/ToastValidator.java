package com.strohwitwer.awesomevalidation.validators;

import android.widget.Toast;

import com.strohwitwer.awesomevalidation.ValidationHolder;
import com.strohwitwer.awesomevalidation.utility.ValidationCallback;

import java.util.regex.Matcher;

public class ToastValidator extends Validator {

    @Override
    public boolean trigger() {
        return checkFields(new ValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder, Matcher matcher) {
//                validationHolder.getEditText().setError(validationHolder.getErrMsg());

                Toast.makeText(validationHolder.getEditText().getContext(), validationHolder.getErrMsg(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void halt() {
        for (ValidationHolder validationHolder : mValidationHolderList) {
//            validationHolder.getEditText().setError(null);
        }
    }

}