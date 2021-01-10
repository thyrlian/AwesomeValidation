package com.basgeekball.awesomevalidation.validators;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.core.util.Pair;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.helper.RangeHelper;
import com.basgeekball.awesomevalidation.helper.SpanHelper;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class ColorationValidator extends Validator {

    private int mColor = Color.RED;
    private HashMap<EditText, TextWatcher> mTextWatcherForEditText = new HashMap<EditText, TextWatcher>();

    public void setColor(int color) {
        mColor = color;
    }

    private ValidationCallback mValidationCallback = new ValidationCallback() {
        @Override
        public void execute(ValidationHolder validationHolder, Matcher matcher) {
            ArrayList<Pair<Integer, Integer>> listOfMatching = new ArrayList<>();
            if (matcher != null) {
                while (matcher.find()) {
                    listOfMatching.add(Pair.create(matcher.start(), matcher.end() - 1));
                }
            }
            final EditText editText = validationHolder.getEditText();
            ArrayList<Pair<Integer, Integer>> listOfNotMatching = RangeHelper.inverse(listOfMatching, editText.getText().length());
            SpanHelper.setColor(editText, mColor, listOfNotMatching);
            editText.setError(validationHolder.getErrMsg());
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // do nothing
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    editText.removeTextChangedListener(this);
                    int cursorStart = editText.getSelectionStart();
                    int cursorEnd = editText.getSelectionEnd();
                    SpanHelper.reset(editText);
                    editText.setSelection(cursorStart, cursorEnd);
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    // do nothing
                }
            };
            editText.addTextChangedListener(textWatcher);
            mTextWatcherForEditText.put(editText, textWatcher);
        }
    };

    @Override
    public boolean trigger() {
        halt();
        return checkFields(mValidationCallback);
    }

    @Override
    public void halt() {
        for (ValidationHolder validationHolder : mValidationHolderList) {
            if (validationHolder.isSomeSortOfView()) {
                validationHolder.resetCustomError();
            } else {
                EditText editText = validationHolder.getEditText();
                editText.setError(null);
                SpanHelper.reset(editText);
            }
        }
        for (Map.Entry<EditText, TextWatcher> entry : mTextWatcherForEditText.entrySet()) {
            entry.getKey().removeTextChangedListener(entry.getValue());
        }
        if (AwesomeValidation.isAutoFocusOnFirstFailureEnabled() && mValidationHolderList.size() > 0) {
            ValidationHolder validationHolder = mValidationHolderList.get(0);
            if (!validationHolder.isSomeSortOfView()) {
                validationHolder.getEditText().requestFocus();
            }
        }
    }

}
