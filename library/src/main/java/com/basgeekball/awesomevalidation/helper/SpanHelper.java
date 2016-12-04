package com.basgeekball.awesomevalidation.helper;

import android.text.Editable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.widget.EditText;

import java.util.ArrayList;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

public class SpanHelper {

    private SpanHelper() {
        throw new UnsupportedOperationException();
    }

    public static void setColor(EditText editText, int color, ArrayList<int[]> ranges) {
        String text = editText.getText().toString();
        SpannableString spanText = new SpannableString(text);
        for (int[] range : ranges) {
            spanText.setSpan(new BackgroundColorSpan(color), range[0], range[1] + 1, SPAN_INCLUSIVE_INCLUSIVE);
        }
        editText.setText(spanText);
    }

    public static void reset(EditText editText) {
        Editable editable = editText.getText();
        if (editable != null) {
            editable.clearSpans();
        }
    }

}