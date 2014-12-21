package com.strohwitwer.awesomevalidation;

import android.widget.EditText;

import java.util.regex.Pattern;

public class ValidationHolder {

    private EditText mEditText;
    private Pattern mPattern;
    private String mErrMsg;

    public ValidationHolder(EditText editText, Pattern pattern, String errMsg) {
        mEditText = editText;
        mPattern = pattern;
        mErrMsg = errMsg;
    }

    public EditText getEditText() {
        return mEditText;
    }

    public Pattern getPattern() {
        return mPattern;
    }

    public String getErrMsg() {
        return mErrMsg;
    }

    public String getText() {
        return mEditText.getText().toString();
    }

}