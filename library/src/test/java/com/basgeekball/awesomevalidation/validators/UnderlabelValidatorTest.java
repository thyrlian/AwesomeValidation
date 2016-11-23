package com.basgeekball.awesomevalidation.validators;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.utility.ValidationCallback;
import com.basgeekball.awesomevalidation.utility.ViewsInfo;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UnderlabelValidator.class, AnimationUtils.class})
public class UnderlabelValidatorTest extends TestCase {

    private UnderlabelValidator mSpiedUnderlabelValidator;
    private ValidationHolder mMockedValidationHolder;
    private Context mMockedContext;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSpiedUnderlabelValidator = PowerMockito.spy(new UnderlabelValidator());
        mMockedValidationHolder = mock(ValidationHolder.class, RETURNS_DEEP_STUBS);
        mSpiedUnderlabelValidator.mValidationHolderList.add(mMockedValidationHolder);
        mMockedContext = mock(Context.class);
        mSpiedUnderlabelValidator.setContext(mMockedContext);
    }

    public void testValidationCallbackExecute() throws Exception {
        ValidationCallback validationCallback = Whitebox.getInternalState(mSpiedUnderlabelValidator, "mValidationCallback");
        Whitebox.setInternalState(mSpiedUnderlabelValidator, "mHasFailed", false);
        TextView mockedTextView = mock(TextView.class);
        Matcher mockedMatcher = PowerMockito.mock(Matcher.class);
        Drawable mockedDrawable = mock(Drawable.class);
        when(mMockedValidationHolder.getEditText().getBackground()).thenReturn(mockedDrawable);
        doNothing().when(mockedDrawable).setColorFilter(anyInt(), any(PorterDuff.Mode.class));
        PowerMockito.doReturn(mockedTextView).when(mSpiedUnderlabelValidator, "replaceView", mMockedValidationHolder);
        validationCallback.execute(mMockedValidationHolder, mockedMatcher);
    }

    public void testHalt() throws IllegalAccessException {
        ViewsInfo mockedViewsInfo = mock(ViewsInfo.class);
        ArrayList<ViewsInfo> viewsInfos = new ArrayList<>();
        viewsInfos.add(mockedViewsInfo);
        EditText mockedEditText = mock(EditText.class);
        MemberModifier.field(UnderlabelValidator.class, "mViewsInfos").set(mSpiedUnderlabelValidator, viewsInfos);
        doNothing().when(mockedViewsInfo).restoreViews();
        when(mMockedValidationHolder.getEditText()).thenReturn(mockedEditText);
        when(mockedEditText.requestFocus()).thenReturn(true);
        mSpiedUnderlabelValidator.halt();
        assertTrue(viewsInfos.isEmpty());
    }

    public void testReplaceView() throws Exception {
        EditText mockedEditText = mock(EditText.class);
        ViewGroup mockedViewGroup = mock(ViewGroup.class);
        LinearLayout mockedNewContainer = mock(LinearLayout.class);
        int mockedInt = PowerMockito.mock(Integer.class);
        TextView mockedTextView = mock(TextView.class);
        PowerMockito.mockStatic(AnimationUtils.class);
        when(mMockedValidationHolder.getEditText()).thenReturn(mockedEditText);
        when(mMockedValidationHolder.getErrMsg()).thenReturn(PowerMockito.mock(String.class));
        when(mockedEditText.getParent()).thenReturn(mockedViewGroup);
        when(mockedViewGroup.indexOfChild(mockedEditText)).thenReturn(mockedInt);
        when(mockedEditText.getPaddingLeft()).thenReturn(PowerMockito.mock(Integer.class));
        when(mockedEditText.getPaddingRight()).thenReturn(PowerMockito.mock(Integer.class));
        PowerMockito.whenNew(LinearLayout.class).withArguments(mMockedContext).thenReturn(mockedNewContainer);
        PowerMockito.whenNew(TextView.class).withArguments(mMockedContext).thenReturn(mockedTextView);
        PowerMockito.when(AnimationUtils.loadAnimation(any(Context.class), anyInt())).thenReturn(mock(Animation.class));
        assertEquals(mockedTextView, Whitebox.invokeMethod(mSpiedUnderlabelValidator, "replaceView", mMockedValidationHolder));
        verify(mockedViewGroup).addView(mockedNewContainer, mockedInt);
    }

}
