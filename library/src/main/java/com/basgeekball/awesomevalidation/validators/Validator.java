package com.basgeekball.awesomevalidation.validators;

import android.app.Activity;
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
    private boolean mValidationResult = true;
    private ValidationCallback mCallback;

    public Validator() {
        mValidationHolderList = new ArrayList<ValidationHolder>();
    }

    public void set(EditText editText, String regex, String errMsg) {
        Pattern pattern = Pattern.compile(regex);
        ValidationHolder validationHolder = new ValidationHolder(editText, pattern, errMsg);
        mValidationHolderList.add(validationHolder);
    }

    public void set(Activity activity, int viewId, String regex, int errMsgId) {
        EditText editText = (EditText) activity.findViewById(viewId);
        String errMsg = activity.getResources().getString(errMsgId);
        set(editText, regex, errMsg);
    }

    public void set(EditText editText, Pattern pattern, String errMsg) {
        ValidationHolder validationHolder = new ValidationHolder(editText, pattern, errMsg);
        mValidationHolderList.add(validationHolder);
    }

    public void set(Activity activity, int viewId, Pattern pattern, int errMsgId) {
        EditText editText = (EditText) activity.findViewById(viewId);
        String errMsg = activity.getResources().getString(errMsgId);
        set(editText, pattern, errMsg);
    }


    public void set(Activity activity, int viewId, int confirmationViewId, int errMsgId) {
        EditText editText = (EditText) activity.findViewById(viewId);
        EditText editTextConfirmation = (EditText) activity.findViewById(confirmationViewId);
        String errMsg = activity.getResources().getString(errMsgId);
        ValidationHolder validationHolder = new ValidationHolder(editText, editTextConfirmation,
                errMsg);
        mValidationHolderList.add(validationHolder);
    }

    public void set(EditText editText, NumericRange numericRange, String errMsg) {
        ValidationHolder validationHolder = new ValidationHolder(editText, numericRange, errMsg);
        mValidationHolderList.add(validationHolder);
    }

    public void set(Activity activity, int viewId, NumericRange numericRange, int errMsgId) {
        EditText editText = (EditText) activity.findViewById(viewId);
        String errMsg = activity.getResources().getString(errMsgId);
        set(editText, numericRange, errMsg);
    }


    protected boolean checkFields(ValidationCallback callback) {
        mHasFailed = false;
        mValidationResult = true;
        mCallback = callback;
        for (ValidationHolder validationHolder : mValidationHolderList) {
            if (validationHolder.isRegexType()) {
                checkRegexTypeField(validationHolder);
            } else if (validationHolder.isRangeType()) {
                checkRangeTypeField(validationHolder);
            } else if (validationHolder.isConfirmationType()) {
                checkConfirmationTypeField(validationHolder);
            }
        }
        return mValidationResult;
    }

    private void checkRegexTypeField(ValidationHolder validationHolder) {
        Matcher matcher = validationHolder.getPattern().matcher(validationHolder.getText());
        if (matcher != null && !matcher.matches()) {
            executeCallBack(matcher, validationHolder);
        }
    }

    private void checkRangeTypeField(ValidationHolder validationHolder) {
        Matcher matcher;
        boolean valid;
        try {
            valid = validationHolder.getNumericRange().isValid(validationHolder.getText());
        } catch (NumberFormatException e) {
            valid = false;
        }
        if (!valid) {
            matcher = Pattern.compile("±*").matcher(validationHolder.getText());
            executeCallBack(matcher, validationHolder);
        }
    }

    private void checkConfirmationTypeField(ValidationHolder validationHolder) {
        boolean valid = validationHolder.getText().equals(validationHolder.getConfirmationText());
        if (!valid) {
            executeCallBack(null, validationHolder);
        }
    }

    private void executeCallBack(Matcher matcher, ValidationHolder validationHolder) {
        mCallback.execute(validationHolder, matcher);
        mValidationResult = false;
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
