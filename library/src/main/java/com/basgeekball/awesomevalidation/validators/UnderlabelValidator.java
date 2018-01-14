package com.basgeekball.awesomevalidation.validators;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.exception.UnsupportedLayoutException;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;
import com.basgeekball.awesomevalidation.utility.ViewsInfo;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class UnderlabelValidator extends Validator {

    private Context mContext;
    private ArrayList<ViewsInfo> mViewsInfos = new ArrayList<>();
    private int mColor;
    private boolean mHasFailed = false;
    private ValidationCallback mValidationCallback;

    private void init() {
        mValidationCallback = new ValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder, Matcher matcher) {
                TextView textView = replaceView(validationHolder);
                if (!mHasFailed) {
                    textView.setFocusable(true);
                    textView.setFocusableInTouchMode(true);
                    textView.setClickable(true);
                    textView.requestFocus();
                    mHasFailed = true;
                }
                validationHolder.getEditText().getBackground().setColorFilter(mColor, PorterDuff.Mode.SRC_IN);
            }
        };
    }

    public void setContext(Context context) {
        mContext = context;
        init();
    }

    @Override
    public boolean trigger() {
        halt();
        mColor = ContextCompat.getColor(mContext, android.R.color.holo_red_light);
        return checkFields(mValidationCallback);
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

    private TextView replaceView(ValidationHolder validationHolder) {
        EditText editText = validationHolder.getEditText();
        ViewGroup parent = (ViewGroup) editText.getParent();
        if (parent instanceof ConstraintLayout) {
            throw new UnsupportedLayoutException("UnderlabelValidator doesn't support ConstraintLayout, please use TextInputLayoutValidator or other any other validator.");
        }
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
        return textView;
    }

}