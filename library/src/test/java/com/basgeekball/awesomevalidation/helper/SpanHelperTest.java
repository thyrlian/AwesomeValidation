package com.basgeekball.awesomevalidation.helper;

import android.text.Editable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.widget.EditText;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpanHelper.class)
public class SpanHelperTest extends TestCase {

    private EditText mMockEditText;
    private Editable mMockEditable;
    private SpannableString mMockSpannableString;
    private BackgroundColorSpan mMockBackgroundColorSpan;
    private int mColor = 256;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockEditText = mock(EditText.class, RETURNS_MOCKS);
        mMockEditable = mock(Editable.class);
        mMockSpannableString = mock(SpannableString.class);
        mMockBackgroundColorSpan = mock(BackgroundColorSpan.class);
        when(mMockEditText.getText()).thenReturn(mMockEditable);
        when(mMockEditText.getText().toString()).thenReturn(PowerMockito.mock(String.class));
        PowerMockito.whenNew(SpannableString.class).withArguments(PowerMockito.mock(String.class)).thenReturn(mMockSpannableString);
        PowerMockito.whenNew(BackgroundColorSpan.class).withArguments(mColor).thenReturn(mMockBackgroundColorSpan);
    }

    public void testSetColor() throws Exception {
        ArrayList<int[]> ranges = new ArrayList<>();
        ranges.addAll(Arrays.asList(new int[]{1, 3}, new int[]{6, 9}, new int[]{48, 50}));
        SpanHelper.setColor(mMockEditText, mColor, ranges);
//        verify(mMockSpannableString, times(1)).setSpan(mMockBackgroundColorSpan, 1, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        verify(mMockSpannableString, times(1)).setSpan(mMockBackgroundColorSpan, 6, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        verify(mMockSpannableString, times(1)).setSpan(mMockBackgroundColorSpan, 48, 51, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        verify(mMockEditText).setText(mMockSpannableString);
    }

    public void testReset() {
        CharacterStyle mockSpan1 = mock(CharacterStyle.class);
        Object mockSpan2 = mock(Object.class);
        CharacterStyle mockSpan3 = mock(CharacterStyle.class);
        Object[] mockSpans = new Object[]{mockSpan1, mockSpan2, mockSpan3};
        when(mMockEditable.getSpans(0, mMockEditText.length(), Object.class)).thenReturn(mockSpans);
        SpanHelper.reset(mMockEditText);
        verify(mMockEditable, times(1)).removeSpan(mockSpan1);
        verify(mMockEditable, never()).removeSpan(mockSpan2);
        verify(mMockEditable, times(1)).removeSpan(mockSpan3);
    }

}
