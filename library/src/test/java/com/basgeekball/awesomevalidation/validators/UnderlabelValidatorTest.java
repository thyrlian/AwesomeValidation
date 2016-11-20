package com.basgeekball.awesomevalidation.validators;

import android.widget.EditText;

import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.utility.ViewsInfo;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class UnderlabelValidatorTest extends TestCase {

    private UnderlabelValidator mSpiedUnderlabelValidator;
    private ValidationHolder mMockedValidationHolder;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSpiedUnderlabelValidator = PowerMockito.spy(new UnderlabelValidator());
        mMockedValidationHolder = mock(ValidationHolder.class);
        mSpiedUnderlabelValidator.mValidationHolderList.add(mMockedValidationHolder);
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

}
