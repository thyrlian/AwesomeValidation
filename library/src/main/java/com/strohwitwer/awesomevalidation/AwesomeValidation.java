package com.strohwitwer.awesomevalidation;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

import com.strohwitwer.awesomevalidation.validator.BasicValidator;
import com.strohwitwer.awesomevalidation.validator.ColorationValidator;
import com.strohwitwer.awesomevalidation.validator.CroutonValidator;
import com.strohwitwer.awesomevalidation.validator.Validator;

public class AwesomeValidation {

    private Validator mValidator = null;

    public AwesomeValidation(ValidationStyle style) {
        switch (style) {
            case BASIC:
                if (mValidator == null || !(mValidator instanceof BasicValidator)) {
                    mValidator = new BasicValidator();
                }
                return;
            case COLORATION:
                if (mValidator == null || !(mValidator instanceof ColorationValidator)) {
                    mValidator = new ColorationValidator();
                }
                return;
            case CROUTON:
                if (mValidator == null || !(mValidator instanceof CroutonValidator)) {
                    mValidator = new CroutonValidator();
                }
                return;
        }
    }

    public void setColor(int color) {
        if (mValidator instanceof ColorationValidator) {
            ((ColorationValidator) mValidator).setColor(color);
        } else {
            throw new UnsupportedOperationException("Only ColorationValidator supports setting color.");
        }
    }

    public void setContext(Context context) {
        if (mValidator instanceof CroutonValidator) {
            ((CroutonValidator) mValidator).setContext(context);
        } else {
            throw new UnsupportedOperationException("Only CroutonValidator supports setting context.");
        }
    }

    public void setActivity(Activity activity) {
        if (mValidator instanceof CroutonValidator) {
            ((CroutonValidator) mValidator).setActivity(activity);
        } else {
            throw new UnsupportedOperationException("Only CroutonValidator supports setting activity.");
        }
    }

    public void tearDown() {
        if (mValidator instanceof CroutonValidator) {
            ((CroutonValidator) mValidator).destroy();
        }
    }

    public void addValidation(EditText editText, String regex, String errMsg) {
        mValidator.set(editText, regex, errMsg);
    }

    public void addValidation(Activity activity, int viewId, String regex, int errMsgId) {
        mValidator.set(activity, viewId, regex, errMsgId);
    }

    public boolean validate() {
        return mValidator.trigger();
    }

}