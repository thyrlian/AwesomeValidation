package com.strohwitwer.awesomevalidation.validator;

import android.graphics.Color;
import android.widget.EditText;

import com.strohwitwer.awesomevalidation.ValidationHolder;
import com.strohwitwer.awesomevalidation.helper.RangeHelper;
import com.strohwitwer.awesomevalidation.helper.SpanHelper;
import com.strohwitwer.awesomevalidation.utils.ValidationCallback;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ColorationValidator extends Validator {

    private int mColor = Color.RED;

    public void setColor(int color) {
        mColor = color;
    }

    @Override
    public boolean trigger() {
        return checkFields(new ValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder, Matcher matcher) {
                ArrayList<int[]> listOfMatching = new ArrayList<int[]>();
                while (matcher.find()) {
                    listOfMatching.add(new int[]{matcher.start(), matcher.end() - 1});
                }
                EditText editText = validationHolder.getEditText();
                ArrayList<int[]> listOfNotMatching = RangeHelper.inverse(listOfMatching, validationHolder.getText().length());
                SpanHelper.setColor(editText, mColor, listOfNotMatching);
                editText.setError(validationHolder.getErrMsg());
            }
        });
    }

}