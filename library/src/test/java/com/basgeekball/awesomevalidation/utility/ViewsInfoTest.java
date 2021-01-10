package com.basgeekball.awesomevalidation.utility;

import android.content.res.ColorStateList;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@PrepareForTest({ViewCompat.class})
@RunWith(PowerMockRunner.class)
public class ViewsInfoTest extends TestCase {

    private int mIndex = 128;
    private ViewGroup mMockParent;
    private LinearLayout mMockNewContainer;
    private EditText mMockEditText;
    private ViewsInfo mViewsInfo;
    private ColorStateList mMockColorStateList;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockParent = mock(ViewGroup.class);
        mMockNewContainer = mock(LinearLayout.class);
        mMockEditText = mock(EditText.class, RETURNS_DEEP_STUBS);
        mMockColorStateList = mock(ColorStateList.class);
        PowerMockito.mockStatic(ViewCompat.class);
        when(ViewCompat.getBackgroundTintList(mMockEditText)).thenReturn(mMockColorStateList);
        mViewsInfo = new ViewsInfo(mIndex, mMockParent, mMockNewContainer, mMockEditText);
    }

    public void testRestoreViews() {
        mViewsInfo.restoreViews();
        ViewCompat.setBackgroundTintList(mMockEditText, mMockColorStateList);
        InOrder order = inOrder(mMockEditText, mMockNewContainer, mMockParent);
        order.verify(mMockEditText).requestFocus();
        order.verify(mMockNewContainer).removeView(mMockEditText);
        order.verify(mMockParent).removeView(mMockNewContainer);
        order.verify(mMockParent).addView(mMockEditText, mIndex);
    }

}
