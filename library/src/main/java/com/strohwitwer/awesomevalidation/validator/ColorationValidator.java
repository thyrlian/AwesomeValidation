package com.strohwitwer.awesomevalidation.validator;

import android.graphics.Color;
import android.widget.EditText;

import com.strohwitwer.awesomevalidation.ValidationHolder;
import com.strohwitwer.awesomevalidation.helper.RangeHelper;
import com.strohwitwer.awesomevalidation.helper.SpanHelper;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ColorationValidator extends Validator {

    private int mColor = Color.RED;

    public void setColor(int color) {
        mColor = color;
    }

    @Override
    public boolean trigger() {
        boolean result = true;
        for (ValidationHolder validationHolder : mValidationHolderList) {
            EditText editText = validationHolder.getEditText();
            String text = editText.getText().toString();
            Matcher matcher = validationHolder.getPattern().matcher(text);
            if (!matcher.matches()) {
                ArrayList<int[]> listOfMatching = new ArrayList<int[]>();
                while (matcher.find()) {
                    listOfMatching.add(new int[]{matcher.start(), matcher.end() - 1});
                }
                ArrayList<int[]> listOfNotMatching = RangeHelper.inverse(listOfMatching, text.length());
                SpanHelper.setColor(editText, mColor, listOfNotMatching);
                editText.setError(validationHolder.getErrMsg());
                result = false;
            }
        }
        return result;
    }

}