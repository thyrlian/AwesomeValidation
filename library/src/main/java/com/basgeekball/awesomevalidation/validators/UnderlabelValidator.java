package com.basgeekball.awesomevalidation.validators;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.exception.MissingContextException;
import com.basgeekball.awesomevalidation.exception.UnsupportedLayoutException;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;
import com.basgeekball.awesomevalidation.utility.ViewsInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class UnderlabelValidator extends Validator {

    private Context mContext;
    private ArrayList<ViewsInfo> mViewsInfos = new ArrayList<>();
    private int mColor;
    private boolean mHasFailed = false;
    private ValidationCallback mValidationCallback;
    private HashMap<EditText, TextWatcher> mTextWatcherForEditText = new HashMap<EditText, TextWatcher>();

    private void init() {
        mValidationCallback = new ValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder, Matcher matcher) {
                TextView textView = replaceView(validationHolder);
                if (AwesomeValidation.isAutoFocusOnFirstFailureEnabled() && !mHasFailed) {
                    textView.setFocusable(true);
                    textView.setFocusableInTouchMode(true);
                    textView.setClickable(true);
                    textView.requestFocus();
                    mHasFailed = true;
                }
                ViewCompat.setBackgroundTintList(validationHolder.getEditText(), ColorStateList.valueOf(mColor));
            }
        };
    }

    public void setContext(Context context) {
        mContext = context;
        setColorByResource(android.R.color.holo_red_light);
        init();
    }

    public void setColor(int colorValue) {
        mColor = colorValue;
    }

    public void setColorByResource(int colorResId) {
        if (mContext == null) {
            throw new MissingContextException("Context should be set before setting color for Underlabel style.");
        } else {
            mColor = ContextCompat.getColor(mContext, colorResId);
        }
    }

    @Override
    public boolean trigger() {
        halt();
        return checkFields(mValidationCallback);
    }

    @Override
    public void halt() {
        for (ViewsInfo viewsInfo : mViewsInfos) {
            viewsInfo.restoreViews();
        }
        for (ValidationHolder validationHolder : mValidationHolderList) {
            if (validationHolder.isSomeSortOfView()) {
                validationHolder.resetCustomError();
            }
        }
        if (AwesomeValidation.isAutoFocusOnFirstFailureEnabled() && mValidationHolderList.size() > 0) {
            ValidationHolder validationHolder = mValidationHolderList.get(0);
            if (!validationHolder.isSomeSortOfView()) {
                validationHolder.getEditText().requestFocus();
            }
        }
        mViewsInfos.clear();
        mHasFailed = false;
        for (Map.Entry<EditText, TextWatcher> entry : mTextWatcherForEditText.entrySet()) {
            entry.getKey().removeTextChangedListener(entry.getValue());
        }
    }

    private TextView replaceView(ValidationHolder validationHolder) {
        final EditText editText = validationHolder.getEditText();
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
        final ViewsInfo viewsInfo = new ViewsInfo(index, parent, newContainer, editText);
        mViewsInfos.add(viewsInfo);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editText.removeTextChangedListener(this);
                viewsInfo.restoreViews();
                editText.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // do nothing
            }
        };
        editText.addTextChangedListener(textWatcher);
        mTextWatcherForEditText.put(editText, textWatcher);
        return textView;
    }

}