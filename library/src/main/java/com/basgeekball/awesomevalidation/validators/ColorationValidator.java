package com.basgeekball.awesomevalidation.validators;

import android.graphics.Color;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.helper.RangeHelper;
import com.basgeekball.awesomevalidation.helper.SpanHelper;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ColorationValidator extends Validator {

    private int mColor = Color.RED;

    public void setColor(int color) {
        mColor = color;
    }

    private ValidationCallback mValidationCallback = new ValidationCallback() {
        @Override
        public void execute(ValidationHolder validationHolder, Matcher matcher) {
            ArrayList<int[]> listOfMatching = new ArrayList<>();
            if (matcher != null) {
                while (matcher.find()) {
                    listOfMatching.add(new int[]{matcher.start(), matcher.end() - 1});
                }
            }
            EditText editText = validationHolder.getEditText();
            ArrayList<int[]> listOfNotMatching = RangeHelper.inverse(listOfMatching, editText.getText().length());
            SpanHelper.setColor(editText, mColor, listOfNotMatching);
            editText.setError(validationHolder.getErrMsg());
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
            EditText editText = validationHolder.getEditText();
            editText.setError(null);
            SpanHelper.reset(editText);
        }
    }

}
