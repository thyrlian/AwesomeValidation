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
    private ViewGroup mMockParent;
    private LinearLayout mMockNewContainer;
    private EditText mMockEditText;
    private ViewsInfo mViewsInfo;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockParent = mock(ViewGroup.class);
        mMockNewContainer = mock(LinearLayout.class);
        mMockEditText = mock(EditText.class, RETURNS_DEEP_STUBS);
        mViewsInfo = new ViewsInfo(mIndex, mMockParent, mMockNewContainer, mMockEditText);
    }

    public void testRestoreViews() {
        mViewsInfo.restoreViews();
        InOrder order = inOrder(mMockNewContainer, mMockParent);
        order.verify(mMockNewContainer).removeView(mMockEditText);
        order.verify(mMockParent).removeView(mMockNewContainer);
        order.verify(mMockParent).addView(mMockEditText, mIndex);
    }

}
