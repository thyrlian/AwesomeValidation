package com.basgeekball.awesomevalidation.utility;

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ViewsInfo {

    private int mIndex;
    private ViewGroup mParent;
    private LinearLayout mNewContainer;
    private EditText mEditText;
    private boolean mIsOriginal = false;

    public ViewsInfo(int index, ViewGroup parent, LinearLayout newContainer, EditText editText) {
        mIndex = index;
        mParent = parent;
        mNewContainer = newContainer;
        mEditText = editText;
    }

    public void restoreViews() {
        if (!mIsOriginal) {
            mEditText.getBackground().clearColorFilter();
            mEditText.requestFocus();
            mNewContainer.removeView(mEditText);
            mParent.removeView(mNewContainer);
            mParent.addView(mEditText, mIndex);
            mIsOriginal = true;
        }
    }

    public int getIndex() {
        return mIndex;
    }

    public ViewGroup getParent() {
        return mParent;
    }

    public LinearLayout getNewContainer() {
        return mNewContainer;
    }

    public EditText getEditText() {
        return mEditText;
    }

}