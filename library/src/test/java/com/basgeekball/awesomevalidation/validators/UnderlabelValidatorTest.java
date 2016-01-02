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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UnderlabelValidator.class, MockValidationHolderHelper.class})
public class UnderlabelValidatorTest extends TestCase {

    private UnderlabelValidator mSpiedUnderlabelValidator;
    private Context mMockedContext;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSpiedUnderlabelValidator = PowerMockito.spy(new UnderlabelValidator());
        mMockedContext = mock(Context.class, RETURNS_MOCKS);
        mSpiedUnderlabelValidator.setContext(mMockedContext);
    }

    public void testReplaceView() throws Exception {
        ArrayList<ViewsInfo> mockedViewsInfos = mock(ArrayList.class);
        MemberModifier.field(UnderlabelValidator.class, "mContext").set(mSpiedUnderlabelValidator, mMockedContext);
        MemberModifier.field(UnderlabelValidator.class, "mViewsInfos").set(mSpiedUnderlabelValidator, mockedViewsInfos);
        ValidationHolder mockedValidationHolder = generate(REGEX, false);
        EditText mockedEditText = mock(EditText.class);
        ViewGroup mockedParent = mock(ViewGroup.class);
        LinearLayout mockedContainer = mock(LinearLayout.class);
        int index = 128;
        ViewsInfo mockedViewsInfo = mock(ViewsInfo.class);
        when(mockedValidationHolder.getEditText()).thenReturn(mockedEditText);
        when(mockedEditText.getParent()).thenReturn(mockedParent);
        when(mockedParent.indexOfChild(mockedEditText)).thenReturn(index);
        PowerMockito.whenNew(LinearLayout.class).withArguments(mMockedContext).thenReturn(mockedContainer);
        PowerMockito.whenNew(ViewsInfo.class).withArguments(index, mockedParent, mockedContainer, mockedEditText).thenReturn(mockedViewsInfo);
        Whitebox.invokeMethod(mSpiedUnderlabelValidator, "replaceView", mockedValidationHolder);
        verify(mockedViewsInfos, times(1)).add(mockedViewsInfo);

        TextView mockedTextView = mock(TextView.class);
        PowerMockito.whenNew(TextView.class).withArguments(mMockedContext).thenReturn(mockedTextView);
        assertEquals(mockedTextView, Whitebox.invokeMethod(mSpiedUnderlabelValidator, "replaceView", mockedValidationHolder));
    }

    public void testTriggerOneWithoutError() throws Exception {
        ValidationHolder mockedValidationHolder = generate(REGEX, true);
        mSpiedUnderlabelValidator.mValidationHolderList.add(mockedValidationHolder);
        mSpiedUnderlabelValidator.trigger();
        PowerMockito.verifyPrivate(mSpiedUnderlabelValidator, never()).invoke("replaceView", mockedValidationHolder);
    }

    public void testTriggerManyWithoutError() throws Exception {
        ValidationHolder mockedValidationHolderRegexType = generate(REGEX, true);
        ValidationHolder mockedValidationHolderRangeType = generate(RANGE, true);
        mSpiedUnderlabelValidator.mValidationHolderList.addAll(Arrays.asList(mockedValidationHolderRegexType, mockedValidationHolderRangeType));
        mSpiedUnderlabelValidator.trigger();
        PowerMockito.verifyPrivate(mSpiedUnderlabelValidator, never()).invoke("replaceView", mockedValidationHolderRegexType);
        PowerMockito.verifyPrivate(mSpiedUnderlabelValidator, never()).invoke("replaceView", mockedValidationHolderRangeType);
    }

}
