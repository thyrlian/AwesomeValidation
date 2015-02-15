package com.strohwitwer.awesomevalidation;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

import com.strohwitwer.awesomevalidation.model.NumericRange;
import com.strohwitwer.awesomevalidation.validators.BasicValidator;
import com.strohwitwer.awesomevalidation.validators.ColorationValidator;
import com.strohwitwer.awesomevalidation.validators.UnderlabelValidator;
import com.strohwitwer.awesomevalidation.validators.Validator;

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
            case UNDERLABEL:
                if (mValidator == null || !(mValidator instanceof UnderlabelValidator)) {
                    mValidator = new UnderlabelValidator();
                }
                return;
        }
    }

    public void setContext(Context context) {
        if (mValidator instanceof UnderlabelValidator) {
            ((UnderlabelValidator) mValidator).setContext(context);
        } else {
            throw new UnsupportedOperationException("Only UnderlabelValidator supports setting context.");
        }
    }

    public void setColor(int color) {
        if (mValidator instanceof ColorationValidator) {
            ((ColorationValidator) mValidator).setColor(color);
        } else {
            throw new UnsupportedOperationException("Only ColorationValidator supports setting color.");
        }
    }

    public void addValidation(EditText editText, String regex, String errMsg) {
        mValidator.set(editText, regex, errMsg);
    }

    public void addValidation(Activity activity, int viewId, String regex, int errMsgId) {
        mValidator.set(activity, viewId, regex, errMsgId);
    }

    public void addValidation(EditText editText, int min, int max, boolean minInclusive, boolean maxInclusive, String errMsg) {
        mValidator.set(editText, new NumericRange(min, max, minInclusive, maxInclusive), errMsg);
    }

    public void addValidation(EditText editText, int min, int max, boolean inclusive, String errMsg) {
        mValidator.set(editText, new NumericRange(min, max, inclusive), errMsg);
    }

    public void addValidation(EditText editText, int min, int max, String errMsg) {
        mValidator.set(editText, new NumericRange(min, max), errMsg);
    }

    public void addValidation(Activity activity, int viewId, int min, int max, boolean minInclusive, boolean maxInclusive, int errMsgId) {
        mValidator.set(activity, viewId, new NumericRange(min, max, minInclusive, maxInclusive), errMsgId);
    }

    public void addValidation(Activity activity, int viewId, int min, int max, boolean inclusive, int errMsgId) {
        mValidator.set(activity, viewId, new NumericRange(min, max, inclusive), errMsgId);
    }

    public void addValidation(Activity activity, int viewId, int min, int max, int errMsgId) {
        mValidator.set(activity, viewId, new NumericRange(min, max), errMsgId);
    }

    public boolean validate() {
        return mValidator.trigger();
    }

    public void clear() {
        mValidator.halt();
    }

}