package com.basgeekball.awesomevalidation.utility;

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import junit.framework.TestCase;

import org.mockito.InOrder;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

public class ViewsInfoTest extends TestCase {

    private int mIndex = 128;
    private ViewGroup mMockedParent;
    private LinearLayout mMockedNewContainer;
    private EditText mMockedEditText;
    private ViewsInfo mViewsInfo;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockedParent = mock(ViewGroup.class);
        mMockedNewContainer = mock(LinearLayout.class);
        mMockedEditText = mock(EditText.class, RETURNS_DEEP_STUBS);
        mViewsInfo = new ViewsInfo(mIndex, mMockedParent, mMockedNewContainer, mMockedEditText);
    }

    public void testRestoreViews() {
        mViewsInfo.restoreViews();
        InOrder order = inOrder(mMockedNewContainer, mMockedParent);
        order.verify(mMockedNewContainer).removeView(mMockedEditText);
        order.verify(mMockedParent).removeView(mMockedNewContainer);
        order.verify(mMockedParent).addView(mMockedEditText, mIndex);
    }

}
