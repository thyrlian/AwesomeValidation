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

import androidx.core.content.ContextCompat;

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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UnderlabelValidator.class, AnimationUtils.class, ContextCompat.class})
public class UnderlabelValidatorTest extends TestCase {

    private UnderlabelValidator mSpiedUnderlabelValidator;
    private ValidationHolder mMockValidationHolder;
    private Context mMockContext;
    private int mColor = 256;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSpiedUnderlabelValidator = PowerMockito.spy(new UnderlabelValidator());
        mMockValidationHolder = mock(ValidationHolder.class, RETURNS_DEEP_STUBS);
        mSpiedUnderlabelValidator.mValidationHolderList.add(mMockValidationHolder);
        mMockContext = mock(Context.class);
        PowerMockito.mockStatic(ContextCompat.class);
        PowerMockito.when(ContextCompat.getColor(eq(mMockContext), anyInt())).thenReturn(mColor);
        mSpiedUnderlabelValidator.setContext(mMockContext);
    }

    public void testValidationCallbackExecute() throws Exception {
        ValidationCallback validationCallback = Whitebox.getInternalState(mSpiedUnderlabelValidator, "mValidationCallback");
        Whitebox.setInternalState(mSpiedUnderlabelValidator, "mHasFailed", false);
        TextView mockTextView = mock(TextView.class);
        Matcher mockMatcher = PowerMockito.mock(Matcher.class);
        Drawable mockDrawable = mock(Drawable.class);
        when(mMockValidationHolder.getEditText().getBackground()).thenReturn(mockDrawable);
        doNothing().when(mockDrawable).setColorFilter(anyInt(), any(PorterDuff.Mode.class));
        PowerMockito.doReturn(mockTextView).when(mSpiedUnderlabelValidator, "replaceView", mMockValidationHolder);
        validationCallback.execute(mMockValidationHolder, mockMatcher);
    }

    public void testHalt() throws IllegalAccessException {
        ViewsInfo mockViewsInfo = mock(ViewsInfo.class);
        ArrayList<ViewsInfo> viewsInfos = new ArrayList<>();
        viewsInfos.add(mockViewsInfo);
        EditText mockEditText = mock(EditText.class);
        MemberModifier.field(UnderlabelValidator.class, "mViewsInfos").set(mSpiedUnderlabelValidator, viewsInfos);
        doNothing().when(mockViewsInfo).restoreViews();
        when(mMockValidationHolder.getEditText()).thenReturn(mockEditText);
        when(mockEditText.requestFocus()).thenReturn(true);
        mSpiedUnderlabelValidator.halt();
        assertTrue(viewsInfos.isEmpty());
    }

    public void testHaltWithSomeSortOfView() {
        ValidationHolder mockValidationHolder = mock(ValidationHolder.class);
        when(mockValidationHolder.isSomeSortOfView()).thenReturn(true);
        mSpiedUnderlabelValidator.mValidationHolderList.clear();
        mSpiedUnderlabelValidator.mValidationHolderList.add(mockValidationHolder);
        mSpiedUnderlabelValidator.halt();
        verify(mockValidationHolder).resetCustomError();
    }

    public void testReplaceView() throws Exception {
        EditText mockEditText = mock(EditText.class);
        ViewGroup mockViewGroup = mock(ViewGroup.class);
        LinearLayout mockNewContainer = mock(LinearLayout.class);
        int mockInt = PowerMockito.mock(Integer.class);
        TextView mockTextView = mock(TextView.class);
        PowerMockito.mockStatic(AnimationUtils.class);
        when(mMockValidationHolder.getEditText()).thenReturn(mockEditText);
        when(mMockValidationHolder.getErrMsg()).thenReturn(PowerMockito.mock(String.class));
        when(mockEditText.getParent()).thenReturn(mockViewGroup);
        when(mockViewGroup.indexOfChild(mockEditText)).thenReturn(mockInt);
        when(mockEditText.getPaddingLeft()).thenReturn(PowerMockito.mock(Integer.class));
        when(mockEditText.getPaddingRight()).thenReturn(PowerMockito.mock(Integer.class));
        PowerMockito.whenNew(LinearLayout.class).withArguments(mMockContext).thenReturn(mockNewContainer);
        PowerMockito.whenNew(TextView.class).withArguments(mMockContext).thenReturn(mockTextView);
        PowerMockito.when(AnimationUtils.loadAnimation(any(Context.class), anyInt())).thenReturn(mock(Animation.class));
        assertEquals(mockTextView, Whitebox.invokeMethod(mSpiedUnderlabelValidator, "replaceView", mMockValidationHolder));
        verify(mockViewGroup).addView(mockNewContainer, mockInt);
    }

}
