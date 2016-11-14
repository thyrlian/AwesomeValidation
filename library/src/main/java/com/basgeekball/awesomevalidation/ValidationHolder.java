package com.basgeekball.awesomevalidation;

import android.widget.EditText;

import com.basgeekball.awesomevalidation.model.NumericRange;

import java.util.regex.Pattern;

public class ValidationHolder {

    private EditText mEditText;
    private EditText mConfirmationEditText;
    private Pattern mPattern;
    private NumericRange mNumericRange;
    private String mErrMsg;

    public ValidationHolder(EditText editText, Pattern pattern, String errMsg) {
        mEditText = editText;
        mPattern = pattern;
        mErrMsg = errMsg;
    }

    public ValidationHolder(EditText editText, NumericRange numericRange, String errMsg) {
        mEditText = editText;
        mNumericRange = numericRange;
        mErrMsg = errMsg;
    }

    public ValidationHolder(EditText editText, EditText confirmationEditText, String errMsg) {
        mEditText = editText;
        mConfirmationEditText = confirmationEditText;
        mErrMsg = errMsg;
    }

    public boolean isRegexType() {
        return mPattern != null;
    }

    public boolean isRangeType() {
        return mNumericRange != null;
    }

    public boolean isConfirmationType() {
        return mConfirmationEditText != null;
    }

    public EditText getEditText() {
        return isConfirmationType() ? mConfirmationEditText : mEditText;
    }

    public Pattern getPattern() {
        return mPattern;
    }

    public NumericRange getNumericRange() {
        return mNumericRange;
    }

    public String getErrMsg() {
        return mErrMsg;
    }

    public String getText() {
        return mEditText.getText().toString();
    }

    public String getConfirmationText() {
        return mConfirmationEditText.getText().toString();
    }
}
