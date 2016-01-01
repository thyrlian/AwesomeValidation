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

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpanHelper.class)
public class SpanHelperTest extends TestCase {

    public void testSetColor() throws Exception {
        EditText mockedEditText = mock(EditText.class, RETURNS_DEEP_STUBS);
        String mockedString = PowerMockito.mock(String.class);
        when(mockedEditText.getText().toString()).thenReturn(mockedString);
        int color = 256;
        ArrayList<int[]> ranges = new ArrayList<>();
        ranges.addAll(Arrays.asList(new int[]{1, 3}, new int[]{6, 9}, new int[]{48, 50}));
        SpannableString mockedSpannableString = mock(SpannableString.class);
        PowerMockito.whenNew(SpannableString.class).withArguments(mockedString).thenReturn(mockedSpannableString);
        BackgroundColorSpan mockedBackgroundColorSpan = mock(BackgroundColorSpan.class);
        PowerMockito.whenNew(BackgroundColorSpan.class).withArguments(color).thenReturn(mockedBackgroundColorSpan);
        SpanHelper.setColor(mockedEditText, color, ranges);
//        verify(mockedSpannableString).setSpan(mockedBackgroundColorSpan, 1, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        verify(mockedSpannableString).setSpan(mockedBackgroundColorSpan, 6, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        verify(mockedSpannableString).setSpan(mockedBackgroundColorSpan, 48, 51, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        verify(mockedEditText).setText(mockedSpannableString);
    }

    public void testReset() {
        EditText mockedEditText = mock(EditText.class, RETURNS_MOCKS);
        Editable mockedEditable = mock(Editable.class);
        when(mockedEditText.getText()).thenReturn(mockedEditable);
        CharacterStyle mockedSpan1 = mock(CharacterStyle.class);
        Object mockedSpan2 = mock(Object.class);
        CharacterStyle mockedSpan3 = mock(CharacterStyle.class);
        Object[] mockedSpans = new Object[]{mockedSpan1, mockedSpan2, mockedSpan3};
        when(mockedEditable.getSpans(0, mockedEditText.length(), Object.class)).thenReturn(mockedSpans);
        SpanHelper.reset(mockedEditText);
        verify(mockedEditable, times(1)).removeSpan(mockedSpan1);
        verify(mockedEditable, never()).removeSpan(mockedSpan2);
        verify(mockedEditable, times(1)).removeSpan(mockedSpan3);
    }

}
