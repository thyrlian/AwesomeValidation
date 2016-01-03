package com.basgeekball.awesomevalidation.validators;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.utility.ViewsInfo;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.Arrays;

import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.RANGE;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.ValidationHolderType.REGEX;
import static com.basgeekball.awesomevalidation.validators.MockValidationHolderHelper.generate;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UnderlabelValidator.class, MockValidationHolderHelper.class})
public class UnderlabelValidatorTest extends TestCase {

    private UnderlabelValidator mSpiedUnderlabelValidator;
    private Context mMockedContext;
    private ValidationHolder mMockedValidationHolderRegexTypePass;
    private ValidationHolder mMockedValidationHolderRegexTypeFail;
    private ValidationHolder mMockedValidationHolderRangeTypePass;
    private ValidationHolder mMockedValidationHolderRangeTypeFail;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSpiedUnderlabelValidator = PowerMockito.spy(new UnderlabelValidator());
        mMockedContext = mock(Context.class, RETURNS_MOCKS);
        mSpiedUnderlabelValidator.setContext(mMockedContext);
        mMockedValidationHolderRegexTypePass = generate(REGEX, true);
        mMockedValidationHolderRegexTypeFail = generate(REGEX, false);
        mMockedValidationHolderRangeTypePass = generate(RANGE, true);
        mMockedValidationHolderRangeTypeFail = generate(RANGE, false);
    }

    public void testReplaceView() throws Exception {
        ArrayList<ViewsInfo> mockedViewsInfos = mock(ArrayList.class);
        MemberModifier.field(UnderlabelValidator.class, "mContext").set(mSpiedUnderlabelValidator, mMockedContext);
        MemberModifier.field(UnderlabelValidator.class, "mViewsInfos").set(mSpiedUnderlabelValidator, mockedViewsInfos);
        int index = 128;
        EditText mockedEditText = mock(EditText.class);
        ViewGroup mockedParent = mock(ViewGroup.class);
        LinearLayout mockedContainer = mock(LinearLayout.class);
        ViewsInfo mockedViewsInfo = mock(ViewsInfo.class);
        when(mMockedValidationHolderRegexTypeFail.getEditText()).thenReturn(mockedEditText);
        when(mockedEditText.getParent()).thenReturn(mockedParent);
        when(mockedParent.indexOfChild(mockedEditText)).thenReturn(index);
        PowerMockito.whenNew(LinearLayout.class).withArguments(mMockedContext).thenReturn(mockedContainer);
        PowerMockito.whenNew(ViewsInfo.class).withArguments(index, mockedParent, mockedContainer, mockedEditText).thenReturn(mockedViewsInfo);
        Whitebox.invokeMethod(mSpiedUnderlabelValidator, "replaceView", mMockedValidationHolderRegexTypeFail);
        verify(mockedViewsInfos, times(1)).add(mockedViewsInfo);
        TextView mockedTextView = mock(TextView.class);
        PowerMockito.whenNew(TextView.class).withArguments(mMockedContext).thenReturn(mockedTextView);
        assertEquals(mockedTextView, Whitebox.invokeMethod(mSpiedUnderlabelValidator, "replaceView", mMockedValidationHolderRegexTypeFail));
    }

    public void testTriggerOneWithoutError() throws Exception {
        mSpiedUnderlabelValidator.mValidationHolderList.add(mMockedValidationHolderRegexTypePass);
        assertTrue(mSpiedUnderlabelValidator.trigger());
        PowerMockito.verifyPrivate(mSpiedUnderlabelValidator, never()).invoke("replaceView", mMockedValidationHolderRegexTypePass);
    }

    public void testTriggerManyWithoutError() throws Exception {
        mSpiedUnderlabelValidator.mValidationHolderList.addAll(Arrays.asList(mMockedValidationHolderRegexTypePass, mMockedValidationHolderRangeTypePass));
        assertTrue(mSpiedUnderlabelValidator.trigger());
        PowerMockito.verifyPrivate(mSpiedUnderlabelValidator, never()).invoke("replaceView", mMockedValidationHolderRegexTypePass);
        PowerMockito.verifyPrivate(mSpiedUnderlabelValidator, never()).invoke("replaceView", mMockedValidationHolderRangeTypePass);
    }

    public void testTriggerOneWithError() throws Exception {
        when(mMockedValidationHolderRegexTypeFail.getEditText().getParent()).thenReturn(mock(ViewGroup.class));
        mSpiedUnderlabelValidator.mValidationHolderList.add(mMockedValidationHolderRegexTypeFail);
        assertFalse(mSpiedUnderlabelValidator.trigger());
        PowerMockito.verifyPrivate(mSpiedUnderlabelValidator, times(1)).invoke("replaceView", mMockedValidationHolderRegexTypeFail);
    }

    public void testTriggerManyWithError() throws Exception {
        when(mMockedValidationHolderRegexTypePass.getEditText().getParent()).thenReturn(mock(ViewGroup.class));
        when(mMockedValidationHolderRegexTypeFail.getEditText().getParent()).thenReturn(mock(ViewGroup.class));
        when(mMockedValidationHolderRangeTypePass.getEditText().getParent()).thenReturn(mock(ViewGroup.class));
        when(mMockedValidationHolderRangeTypeFail.getEditText().getParent()).thenReturn(mock(ViewGroup.class));
        mSpiedUnderlabelValidator.mValidationHolderList.addAll(Arrays.asList(mMockedValidationHolderRegexTypePass,
                mMockedValidationHolderRegexTypeFail,
                mMockedValidationHolderRangeTypePass,
                mMockedValidationHolderRangeTypeFail));
        assertFalse(mSpiedUnderlabelValidator.trigger());
        PowerMockito.verifyPrivate(mSpiedUnderlabelValidator, never()).invoke("replaceView", mMockedValidationHolderRegexTypePass);
        PowerMockito.verifyPrivate(mSpiedUnderlabelValidator, times(1)).invoke("replaceView", mMockedValidationHolderRegexTypeFail);
        PowerMockito.verifyPrivate(mSpiedUnderlabelValidator, never()).invoke("replaceView", mMockedValidationHolderRangeTypePass);
        PowerMockito.verifyPrivate(mSpiedUnderlabelValidator, times(1)).invoke("replaceView", mMockedValidationHolderRangeTypeFail);
    }

    public void testHaltRestoreViewsForAllValid() throws IllegalAccessException {
        ArrayList<ViewsInfo> spiedViewsInfos = spy(ArrayList.class);
        ViewsInfo mockedViewsInfo1 = mock(ViewsInfo.class);
        ViewsInfo mockedViewsInfo2 = mock(ViewsInfo.class);
        spiedViewsInfos.addAll(Arrays.asList(mockedViewsInfo1, mockedViewsInfo2));
        MemberModifier.field(UnderlabelValidator.class, "mViewsInfos").set(mSpiedUnderlabelValidator, spiedViewsInfos);
        mSpiedUnderlabelValidator.mValidationHolderList.addAll(Arrays.asList(mMockedValidationHolderRegexTypePass, mMockedValidationHolderRangeTypePass));
        mSpiedUnderlabelValidator.halt();
        for (ViewsInfo viewsInfo : spiedViewsInfos) {
            verify(viewsInfo, times(1)).restoreViews();
        }
    }

    public void testHaltRestoreViewsForAllInvalid() throws IllegalAccessException {
        ArrayList<ViewsInfo> spiedViewsInfos = spy(ArrayList.class);
        ViewsInfo mockedViewsInfo1 = mock(ViewsInfo.class);
        ViewsInfo mockedViewsInfo2 = mock(ViewsInfo.class);
        spiedViewsInfos.addAll(Arrays.asList(mockedViewsInfo1, mockedViewsInfo2));
        MemberModifier.field(UnderlabelValidator.class, "mViewsInfos").set(mSpiedUnderlabelValidator, spiedViewsInfos);
        mSpiedUnderlabelValidator.mValidationHolderList.addAll(Arrays.asList(mMockedValidationHolderRegexTypeFail, mMockedValidationHolderRangeTypeFail));
        mSpiedUnderlabelValidator.halt();
        for (ViewsInfo viewsInfo : spiedViewsInfos) {
            verify(viewsInfo, times(1)).restoreViews();
        }
    }

    public void testHaltRestoreViewsForAllAnyway() throws IllegalAccessException {
        ArrayList<ViewsInfo> spiedViewsInfos = spy(ArrayList.class);
        ViewsInfo mockedViewsInfo1 = mock(ViewsInfo.class);
        ViewsInfo mockedViewsInfo2 = mock(ViewsInfo.class);
        ViewsInfo mockedViewsInfo3 = mock(ViewsInfo.class);
        ViewsInfo mockedViewsInfo4 = mock(ViewsInfo.class);
        spiedViewsInfos.addAll(Arrays.asList(mockedViewsInfo1, mockedViewsInfo2, mockedViewsInfo3, mockedViewsInfo4));
        MemberModifier.field(UnderlabelValidator.class, "mViewsInfos").set(mSpiedUnderlabelValidator, spiedViewsInfos);
        mSpiedUnderlabelValidator.mValidationHolderList.addAll(Arrays.asList(mMockedValidationHolderRegexTypePass,
                mMockedValidationHolderRegexTypeFail,
                mMockedValidationHolderRangeTypePass,
                mMockedValidationHolderRangeTypeFail));
        mSpiedUnderlabelValidator.halt();
        for (ViewsInfo viewsInfo : spiedViewsInfos) {
            verify(viewsInfo, times(1)).restoreViews();
        }
    }

}
