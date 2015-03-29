package com.basgeekball.awesomevalidation.validators;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;
import com.basgeekball.awesomevalidation.utility.ViewsInfo;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class UnderlabelValidator extends Validator {

    private Context mContext;
    private ArrayList<ViewsInfo> mViewsInfos = new ArrayList<>();
    private int mColor;
    private boolean mHasFailed = false;

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public boolean trigger() {
        halt();
        mColor = mContext.getResources().getColor(android.R.color.holo_red_light);
        return checkFields(new ValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder, Matcher matcher) {
                EditText editText = validationHolder.getEditText();
                ViewGroup parent = (ViewGroup) editText.getParent();
                int index = parent.indexOfChild(editText);
                LinearLayout newContainer = new LinearLayout(mContext);
                newContainer.setLayoutParams(editText.getLayoutParams());
                newContainer.setOrientation(LinearLayout.VERTICAL);
                TextView textView = new TextView(mContext);
                textView.setText(validationHolder.getErrMsg());
                textView.setTextColor(mColor);
                textView.setPadding(editText.getPaddingLeft(), 0, editText.getPaddingRight(), 0);
                textView.startAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));
                parent.removeView(editText);
                newContainer.addView(editText);
                newContainer.addView(textView);
                parent.addView(newContainer, index);
                mViewsInfos.add(new ViewsInfo(index, parent, newContainer, editText));
                if (!mHasFailed) {
                    textView.setFocusable(true);
                    textView.setFocusableInTouchMode(true);
                    textView.setClickable(true);
                    textView.requestFocus();
                    mHasFailed = true;
                }
                editText.getBackground().setColorFilter(mColor, PorterDuff.Mode.SRC_IN);
            }
        });
    }

    @Override
    public void halt() {
        for (ViewsInfo viewsInfo : mViewsInfos) {
            viewsInfo.restoreViews();
        }
        if (mValidationHolderList.size() > 0) {
            mValidationHolderList.get(0).getEditText().requestFocus();
        }
        mViewsInfos.clear();
        mHasFailed = false;
    }

}