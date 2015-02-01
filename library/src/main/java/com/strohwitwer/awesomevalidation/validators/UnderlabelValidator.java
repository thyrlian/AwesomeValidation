package com.strohwitwer.awesomevalidation.validators;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.strohwitwer.awesomevalidation.ValidationHolder;
import com.strohwitwer.awesomevalidation.utility.ValidationCallback;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class UnderlabelValidator extends Validator {

    private Context mContext;
    private ArrayList<TextView> mTextViews = new ArrayList<>();

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public boolean trigger() {
        halt();
        return checkFields(new ValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder, Matcher matcher) {
                EditText editText = validationHolder.getEditText();
                ViewGroup parent = (ViewGroup) editText.getParent();
                int index = parent.indexOfChild(editText);
                TextView textView = new TextView(mContext);
                textView.setText(validationHolder.getErrMsg());
                textView.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_light));
                mTextViews.add(textView);
                parent.addView(textView, index + 1);
            }
        });
    }

    @Override
    public void halt() {
        for (TextView textView : mTextViews) {
            ViewGroup parent = (ViewGroup) textView.getParent();
            parent.removeView(textView);
        }
        mTextViews.clear();
    }

}