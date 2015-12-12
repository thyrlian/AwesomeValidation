package com.basgeekball.awesomevalidation;

import com.google.common.collect.Range;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.model.NumericRange;
import com.basgeekball.awesomevalidation.validators.BasicValidator;
import com.basgeekball.awesomevalidation.validators.ColorationValidator;
import com.basgeekball.awesomevalidation.validators.UnderlabelValidator;
import com.basgeekball.awesomevalidation.validators.Validator;

import java.util.regex.Pattern;

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

    public void addValidation(View rootView, int viewId, String regex, int errMsgId) {
        mValidator.set(rootView, viewId, regex, errMsgId);
    }

    public void addValidation(EditText editText, Pattern pattern, String errMsg) {
        mValidator.set(editText, pattern, errMsg);
    }

    public void addValidation(Activity activity, int viewId, Pattern pattern, int errMsgId) {
        mValidator.set(activity, viewId, pattern, errMsgId);
    }

    public void addValidation(View rootView, int viewId, Pattern pattern, int errMsgId) {
        mValidator.set(rootView, viewId, pattern, errMsgId);
    }

    public void addValidation(EditText editText, Range range, String errMsg) {
        mValidator.set(editText, new NumericRange(range), errMsg);
    }

    public void addValidation(Activity activity, int viewId, Range range, int errMsgId) {
        mValidator.set(activity, viewId, new NumericRange(range), errMsgId);
    }

    public void addValidation(View rootView, int viewId, Range range, int errMsgId) {
        mValidator.set(rootView, viewId, new NumericRange(range), errMsgId);
    }

    public boolean validate() {
        return mValidator.trigger();
    }

    public void clear() {
        mValidator.halt();
    }

}