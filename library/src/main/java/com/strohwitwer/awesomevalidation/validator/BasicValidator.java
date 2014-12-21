package com.strohwitwer.awesomevalidation.validator;

import android.widget.EditText;

import com.strohwitwer.awesomevalidation.ValidationHolder;

import java.util.regex.Matcher;

public class BasicValidator extends Validator {

    @Override
    public boolean trigger() {
        boolean result = true;
        for (ValidationHolder validationHolder : mValidationHolderList) {
            EditText editText = validationHolder.getEditText();
            String text = editText.getText().toString();
            Matcher matcher = validationHolder.getPattern().matcher(text);
            if (!matcher.matches()) {
                editText.setError(validationHolder.getErrMsg());
                result = false;
            }
        }
        return result;
    }

}