package com.basgeekball.awesomevalidation.validators;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.model.NumericRange;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Validator {

    protected ArrayList<ValidationHolder> mValidationHolderList;
    private boolean mHasFailed = false;

    Validator() {
        mValidationHolderList = new ArrayList<>();
    }

    public void set(EditText editText, String regex, String errMsg) {
        Pattern pattern = Pattern.compile(regex);
        ValidationHolder validationHolder = new ValidationHolder(editText, pattern, errMsg);
        mValidationHolderList.add(validationHolder);
    }

    public void set(TextInputLayout textInputLayout, String regex, String errMsg) {
        Pattern pattern = Pattern.compile(regex);
        ValidationHolder validationHolder = new ValidationHolder(textInputLayout, pattern, errMsg);
        mValidationHolderList.add(validationHolder);
    }

    public void set(Activity activity, int viewId, String regex, int errMsgId) {
        View view = activity.findViewById(viewId);
        String errMsg = activity.getResources().getString(errMsgId);
        if (view instanceof EditText) {
            set((EditText) view, regex, errMsg);
        } else if (view instanceof TextInputLayout) {
            set((TextInputLayout) view, regex, errMsg);
        }
    }

    public void set(EditText editText, Pattern pattern, String errMsg) {
        ValidationHolder validationHolder = new ValidationHolder(editText, pattern, errMsg);
        mValidationHolderList.add(validationHolder);
    }

    public void set(TextInputLayout textInputLayout, Pattern pattern, String errMsg) {
        ValidationHolder validationHolder = new ValidationHolder(textInputLayout, pattern, errMsg);
        mValidationHolderList.add(validationHolder);
    }

    public void set(Activity activity, int viewId, Pattern pattern, int errMsgId) {
        View view = activity.findViewById(viewId);
        String errMsg = activity.getResources().getString(errMsgId);
        if (view instanceof EditText) {
            set((EditText) view, pattern, errMsg);
        } else if (view instanceof TextInputLayout) {
            set((TextInputLayout) view, pattern, errMsg);
        }
    }

    public void set(EditText editText, NumericRange numericRange, String errMsg) {
        ValidationHolder validationHolder = new ValidationHolder(editText, numericRange, errMsg);
        mValidationHolderList.add(validationHolder);
    }

    public void set(TextInputLayout textInputLayout, NumericRange numericRange, String errMsg) {
        ValidationHolder validationHolder = new ValidationHolder(textInputLayout, numericRange, errMsg);
        mValidationHolderList.add(validationHolder);
    }

    public void set(Activity activity, int viewId, NumericRange numericRange, int errMsgId) {
        View view = activity.findViewById(viewId);
        String errMsg = activity.getResources().getString(errMsgId);
        if (view instanceof EditText) {
            set((EditText) view, numericRange, errMsg);
        } else if (view instanceof TextInputLayout) {
            set((TextInputLayout) view, numericRange, errMsg);
        }
    }

    public void set(EditText confirmationEditText, EditText editText, String errMsg) {
        ValidationHolder validationHolder = new ValidationHolder(confirmationEditText, editText, errMsg);
        mValidationHolderList.add(validationHolder);
    }

    public void set(TextInputLayout confirmationTextInputLayout, TextInputLayout textInputLayout, String errMsg) {
        ValidationHolder validationHolder = new ValidationHolder(confirmationTextInputLayout, textInputLayout, errMsg);
        mValidationHolderList.add(validationHolder);
    }

    public void set(Activity activity, int confirmationViewId, int viewId, int errMsgId) {
        View confirmationView = activity.findViewById(confirmationViewId);
        View view = activity.findViewById(viewId);
        String errMsg = activity.getResources().getString(errMsgId);
        if (view instanceof EditText) {
            set((EditText) confirmationView, (EditText) view, errMsg);
        } else if (view instanceof TextInputLayout) {
            set((TextInputLayout) confirmationView, (TextInputLayout) view, errMsg);
        }
    }

    protected boolean checkFields(ValidationCallback callback) {
        boolean result = true;
        mHasFailed = false;
        for (ValidationHolder validationHolder : mValidationHolderList) {
            if (validationHolder.isRegexType()) {
                result = checkRegexTypeField(validationHolder, callback) && result;
            } else if (validationHolder.isRangeType()) {
                result = checkRangeTypeField(validationHolder, callback) && result;
            } else if (validationHolder.isConfirmationType()) {
                result = checkConfirmationTypeField(validationHolder, callback) && result;
            }
        }
        return result;
    }

    private boolean checkRegexTypeField(ValidationHolder validationHolder, ValidationCallback callback) {
        Matcher matcher = validationHolder.getPattern().matcher(validationHolder.getText());
        if (!matcher.matches()) {
            executeCallback(callback, validationHolder, matcher);
            return false;
        }
        return true;
    }

    private boolean checkRangeTypeField(ValidationHolder validationHolder, ValidationCallback callback) {
        boolean valid;
        try {
            valid = validationHolder.getNumericRange().isValid(validationHolder.getText());
        } catch (NumberFormatException e) {
            valid = false;
        }
        if (!valid) {
            Matcher matcher = Pattern.compile("±*").matcher(validationHolder.getText());
            executeCallback(callback, validationHolder, matcher);
            return false;
        }
        return true;
    }

    private boolean checkConfirmationTypeField(ValidationHolder validationHolder, ValidationCallback callback) {
        boolean valid = validationHolder.getText().equals(validationHolder.getConfirmationText());
        if (!valid) {
            executeCallback(callback, validationHolder, null);
            return false;
        }
        return true;
    }

    private void executeCallback(ValidationCallback callback, ValidationHolder validationHolder, Matcher matcher) {
        callback.execute(validationHolder, matcher);
        requestFocus(validationHolder);
    }

    private void requestFocus(ValidationHolder validationHolder) {
        if (!mHasFailed) {
            EditText editText = validationHolder.getEditText();
            editText.requestFocus();
            editText.setSelection(editText.getText().length());
            mHasFailed = true;
        }
    }

    public abstract boolean trigger();

    public abstract void halt();

}
