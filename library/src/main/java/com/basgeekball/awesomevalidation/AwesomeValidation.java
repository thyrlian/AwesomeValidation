package com.basgeekball.awesomevalidation;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.model.NumericRange;
import com.basgeekball.awesomevalidation.validators.BasicValidator;
import com.basgeekball.awesomevalidation.validators.ColorationValidator;
import com.basgeekball.awesomevalidation.validators.TextInputLayoutValidator;
import com.basgeekball.awesomevalidation.validators.UnderlabelValidator;
import com.basgeekball.awesomevalidation.validators.Validator;
import com.google.common.collect.Range;

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
            case TEXT_INPUT_LAYOUT:
                if (mValidator == null || !(mValidator instanceof TextInputLayoutValidator)) {
                    mValidator = new TextInputLayoutValidator();
                }
                return;
            default:
        }
    }

    private void checkIsColorationValidator() {
        if (!(mValidator instanceof ColorationValidator)) {
            throw new UnsupportedOperationException("Only supported by ColorationValidator.");
        }
    }

    private void checkIsUnderlabelValidator() {
        if (!(mValidator instanceof UnderlabelValidator)) {
            throw new UnsupportedOperationException("Only supported by UnderlabelValidator.");
        }
    }

    private void checkIsTextInputLayoutValidator() {
        if (!(mValidator instanceof TextInputLayoutValidator)) {
            throw new UnsupportedOperationException("Only supported by TextInputLayoutValidator.");
        }
    }

    private void checkIsNotTextInputLayoutValidator() {
        if (mValidator instanceof TextInputLayoutValidator) {
            throw new UnsupportedOperationException("Not supported by TextInputLayoutValidator.");
        }
    }

    public void setColor(int color) {
        checkIsColorationValidator();
        ((ColorationValidator) mValidator).setColor(color);
    }

    public void setContext(Context context) {
        checkIsUnderlabelValidator();
        ((UnderlabelValidator) mValidator).setContext(context);
    }

    public void addValidation(EditText editText, String regex, String errMsg) {
        checkIsNotTextInputLayoutValidator();
        mValidator.set(editText, regex, errMsg);
    }

    public void addValidation(TextInputLayout textInputLayout, String regex, String errMsg) {
        checkIsTextInputLayoutValidator();
        mValidator.set(textInputLayout, regex, errMsg);
    }

    public void addValidation(Activity activity, int viewId, String regex, int errMsgId) {
        mValidator.set(activity, viewId, regex, errMsgId);
    }

    public void addValidation(EditText editText, Pattern pattern, String errMsg) {
        checkIsNotTextInputLayoutValidator();
        mValidator.set(editText, pattern, errMsg);
    }

    public void addValidation(TextInputLayout textInputLayout, Pattern pattern, String errMsg) {
        checkIsTextInputLayoutValidator();
        mValidator.set(textInputLayout, pattern, errMsg);
    }

    public void addValidation(Activity activity, int viewId, Pattern pattern, int errMsgId) {
        mValidator.set(activity, viewId, pattern, errMsgId);
    }

    public void addValidation(EditText editText, Range range, String errMsg) {
        checkIsNotTextInputLayoutValidator();
        mValidator.set(editText, new NumericRange(range), errMsg);
    }

    public void addValidation(TextInputLayout textInputLayout, Range range, String errMsg) {
        checkIsTextInputLayoutValidator();
        mValidator.set(textInputLayout, new NumericRange(range), errMsg);
    }

    public void addValidation(Activity activity, int viewId, Range range, int errMsgId) {
        mValidator.set(activity, viewId, new NumericRange(range), errMsgId);
    }

    public void addValidation(EditText confirmationEditText, EditText editText, String errMsg) {
        checkIsNotTextInputLayoutValidator();
        mValidator.set(confirmationEditText, editText, errMsg);
    }

    public void addValidation(TextInputLayout confirmationTextInputLayout, TextInputLayout textInputLayout, String errMsg) {
        checkIsTextInputLayoutValidator();
        mValidator.set(confirmationTextInputLayout, textInputLayout, errMsg);
    }

    public void addValidation(Activity activity, int confirmationViewId, int viewId, int errMsgId) {
        mValidator.set(activity, confirmationViewId, viewId, errMsgId);
    }

    public boolean validate() {
        return mValidator.trigger();
    }

    public void clear() {
        mValidator.halt();
    }

}
