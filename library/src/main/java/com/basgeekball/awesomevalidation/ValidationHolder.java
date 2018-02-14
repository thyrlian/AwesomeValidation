package com.basgeekball.awesomevalidation;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.exception.BadLayoutException;
import com.basgeekball.awesomevalidation.model.NumericRange;
import com.basgeekball.awesomevalidation.utility.custom.CustomErrorReset;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidation;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidationCallback;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;

import java.util.regex.Pattern;

public class ValidationHolder {

    private EditText mEditText;
    private EditText mConfirmationEditText;
    private TextInputLayout mTextInputLayout;
    private TextInputLayout mConfirmationTextInputLayout;
    private View mView;
    private Pattern mPattern;
    private NumericRange mNumericRange;
    private String mErrMsg;
    private SimpleCustomValidation mSimpleCustomValidation;
    private CustomValidation mCustomValidation;
    private CustomValidationCallback mCustomValidationCallback;
    private CustomErrorReset mCustomErrorReset;

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

    public ValidationHolder(EditText editText, SimpleCustomValidation simpleCustomValidation, String errMsg) {
        mEditText = editText;
        mSimpleCustomValidation = simpleCustomValidation;
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

    public ValidationHolder(TextInputLayout textInputLayout, SimpleCustomValidation simpleCustomValidation, String errMsg) {
        mTextInputLayout = textInputLayout;
        mSimpleCustomValidation = simpleCustomValidation;
        mErrMsg = errMsg;
    }

    public ValidationHolder(View view, CustomValidation customValidation, CustomValidationCallback customValidationCallback, CustomErrorReset customErrorReset, String errMsg) {
        mView = view;
        mCustomValidation = customValidation;
        mCustomValidationCallback = customValidationCallback;
        mCustomErrorReset = customErrorReset;
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

    public boolean isSimpleCustomType() {
        return mSimpleCustomValidation != null;
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

    public boolean isSomeSortOfView() {
        return mView != null;
    }

    public Pattern getPattern() {
        return mPattern;
    }

    public NumericRange getNumericRange() {
        return mNumericRange;
    }

    public SimpleCustomValidation getSimpleCustomValidation() {
        return mSimpleCustomValidation;
    }

    public CustomValidation getCustomValidation() {
        return mCustomValidation;
    }

    public CustomValidationCallback getCustomValidationCallback() {
        return mCustomValidationCallback;
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

    public View getView() {
        if (isSomeSortOfView()) {
            return mView;
        } else {
            return null;
        }
    }

    public boolean isVisible() {
        return isEditTextView() && getEditText().getVisibility() == View.VISIBLE
                || isTextInputLayoutView() && getTextInputLayout().getVisibility() == View.VISIBLE
                || isSomeSortOfView() && getView().getVisibility() == View.VISIBLE;
    }

    public void resetCustomError() {
        mCustomErrorReset.reset(this);
    }

}
