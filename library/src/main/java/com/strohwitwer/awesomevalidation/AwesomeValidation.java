package com.strohwitwer.awesomevalidation;

import android.app.Activity;
import android.widget.EditText;

import com.strohwitwer.awesomevalidation.validator.ColorationValidator;
import com.strohwitwer.awesomevalidation.validator.Validator;

public class AwesomeValidation {

    private Validator mValidator = null;

    public AwesomeValidation(ValidationStyle style) {
        switch (style) {
            case COLORATION:
                if (mValidator == null || !(mValidator instanceof ColorationValidator)) {
                    mValidator = new ColorationValidator();
                }
                return;
        }
    }

    public void addValidation(EditText editText, String regex, String errMsg) {
        mValidator.set(editText, regex, errMsg);
    }

    public void addValidation(Activity activity, int viewId, String regex, int errMsgId) {
        mValidator.set(activity, viewId, regex, errMsgId);
    }

    public void validate() {
        mValidator.trigger();
    }

}