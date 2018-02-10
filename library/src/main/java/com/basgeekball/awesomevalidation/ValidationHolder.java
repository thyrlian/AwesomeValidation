package com.basgeekball.awesomevalidation;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.exception.BadLayoutException;
import com.basgeekball.awesomevalidation.helper.CustomValidation;
import com.basgeekball.awesomevalidation.model.NumericRange;

import java.util.regex.Pattern;

public class ValidationHolder {

    private EditText mEditText;
    private EditText mConfirmationEditText;
    private TextInputLayout mTextInputLayout;
    private TextInputLayout mConfirmationTextInputLayout;
    private Pattern mPattern;
    private NumericRange mNumericRange;
    private String mErrMsg;
    private CustomValidation mCustomValidation;

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

    public ValidationHolder(EditText confirmationEditText, EditText editText, String errMsg) {
        mConfirmationEditText = confirmationEditText;
        mEditText = editText;
        mErrMsg = errMsg;
    }

    public ValidationHolder(EditText editText, CustomValidation customValidation, String errMsg) {
        mEditText = editText;
        mCustomValidation = customValidation;
        mErrMsg = errMsg;
    }

    public ValidationHolder(TextInputLayout textInputLayout, Pattern pattern, String errMsg) {
        mTextInputLayout = textInputLayout;
        mPattern = pattern;
        mErrMsg = errMsg;
    }

    public ValidationHolder(TextInputLayout textInputLayout, NumericRange numericRange, String errMsg) {
        mTextInputLayout = textInputLayout;
        mNumericRange = numericRange;
        mErrMsg = errMsg;
    }

    public ValidationHolder(TextInputLayout confirmationTextInputLayout, TextInputLayout textInputLayout, String errMsg) {
        mConfirmationTextInputLayout = confirmationTextInputLayout;
        mTextInputLayout = textInputLayout;
        mErrMsg = errMsg;
    }

    public ValidationHolder(TextInputLayout textInputLayout, CustomValidation customValidation, String errMsg) {
        mTextInputLayout = textInputLayout;
        mCustomValidation = customValidation;
        mErrMsg = errMsg;
    }

    public boolean isRegexType() {
        return mPattern != null;
    }

    public boolean isRangeType() {
        return mNumericRange != null;
    }

    public boolean isConfirmationType() {
        return mConfirmationEditText != null || mConfirmationTextInputLayout != null;
    }

    public boolean isCustomType() {
        return mCustomValidation != null;
    }

    public boolean isEditTextView() {
        return mEditText != null;
    }

    public boolean isTextInputLayoutView() {
        return mTextInputLayout != null;
    }

    public Pattern getPattern() {
        return mPattern;
    }

    public NumericRange getNumericRange() {
        return mNumericRange;
    }

    public CustomValidation getCustomValidation() {
        return mCustomValidation;
    }

    public String getErrMsg() {
        return mErrMsg;
    }

    public String getText() {
        if (mEditText != null) {
            return mEditText.getText().toString();
        } else if (mTextInputLayout != null) {
            EditText editText = mTextInputLayout.getEditText();
            if (editText != null) {
                return editText.getText().toString();
            }
            throw new BadLayoutException("EditText must be present inside TextInputLayout.");
        } else {
            return null;
        }
    }

    public String getConfirmationText() {
        if (mConfirmationEditText != null) {
            return mConfirmationEditText.getText().toString();
        } else if (mConfirmationTextInputLayout != null) {
            EditText editText = mConfirmationTextInputLayout.getEditText();
            if (editText != null) {
                return editText.getText().toString();
            }
            throw new BadLayoutException("EditText must be present inside TextInputLayout.");
        } else {
            return null;
        }
    }

    public EditText getEditText() {
        if (isEditTextView()) {
            return isConfirmationType() ? mConfirmationEditText : mEditText;
        } else if (isTextInputLayoutView()) {
            return isConfirmationType() ? mConfirmationTextInputLayout.getEditText() : mTextInputLayout.getEditText();
        } else {
            return null;
        }
    }

    public TextInputLayout getTextInputLayout() {
        if (isTextInputLayoutView()) {
            return isConfirmationType() ? mConfirmationTextInputLayout : mTextInputLayout;
        } else {
            return null;
        }
    }

    public boolean isVisible() {
        return (isEditTextView() && getEditText().getVisibility() == View.VISIBLE) || (isTextInputLayoutView() && getTextInputLayout().getVisibility() == View.VISIBLE);
    }

}
