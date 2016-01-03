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

    private EditText mMockedEditText;
    private Editable mMockedEditable;
    private SpannableString mMockedSpannableString;
    private BackgroundColorSpan mMockedBackgroundColorSpan;
    private int mColor = 256;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockedEditText = mock(EditText.class, RETURNS_MOCKS);
        mMockedEditable = mock(Editable.class);
        mMockedSpannableString = mock(SpannableString.class);
        mMockedBackgroundColorSpan = mock(BackgroundColorSpan.class);
        when(mMockedEditText.getText()).thenReturn(mMockedEditable);
        when(mMockedEditText.getText().toString()).thenReturn(PowerMockito.mock(String.class));
        PowerMockito.whenNew(SpannableString.class).withArguments(PowerMockito.mock(String.class)).thenReturn(mMockedSpannableString);
        PowerMockito.whenNew(BackgroundColorSpan.class).withArguments(mColor).thenReturn(mMockedBackgroundColorSpan);
    }

    public void testSetColor() throws Exception {
        ArrayList<int[]> ranges = new ArrayList<>();
        ranges.addAll(Arrays.asList(new int[]{1, 3}, new int[]{6, 9}, new int[]{48, 50}));
        SpanHelper.setColor(mMockedEditText, mColor, ranges);
//        verify(mMockedSpannableString, times(1)).setSpan(mMockedBackgroundColorSpan, 1, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        verify(mMockedSpannableString, times(1)).setSpan(mMockedBackgroundColorSpan, 6, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        verify(mMockedSpannableString, times(1)).setSpan(mMockedBackgroundColorSpan, 48, 51, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        verify(mMockedEditText).setText(mMockedSpannableString);
    }

    public void testReset() {
        CharacterStyle mockedSpan1 = mock(CharacterStyle.class);
        Object mockedSpan2 = mock(Object.class);
        CharacterStyle mockedSpan3 = mock(CharacterStyle.class);
        Object[] mockedSpans = new Object[]{mockedSpan1, mockedSpan2, mockedSpan3};
        when(mMockedEditable.getSpans(0, mMockedEditText.length(), Object.class)).thenReturn(mockedSpans);
        SpanHelper.reset(mMockedEditText);
        verify(mMockedEditable, times(1)).removeSpan(mockedSpan1);
        verify(mMockedEditable, never()).removeSpan(mockedSpan2);
        verify(mMockedEditable, times(1)).removeSpan(mockedSpan3);
    }

}
