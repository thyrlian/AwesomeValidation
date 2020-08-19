package com.basgeekball.awesomevalidation

import android.app.Activity
import android.content.Context
import android.support.design.widget.TextInputLayout
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.EditText
import com.basgeekball.awesomevalidation.model.NumericRange
import com.basgeekball.awesomevalidation.utility.custom.CustomErrorReset
import com.basgeekball.awesomevalidation.utility.custom.CustomValidation
import com.basgeekball.awesomevalidation.utility.custom.CustomValidationCallback
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation
import com.basgeekball.awesomevalidation.validators.BasicValidator
import com.basgeekball.awesomevalidation.validators.ColorationValidator
import com.basgeekball.awesomevalidation.validators.TextInputLayoutValidator
import com.basgeekball.awesomevalidation.validators.UnderlabelValidator
import com.google.common.collect.Range
import com.nhaarman.mockitokotlin2.*
import junit.framework.TestCase
import org.easymock.EasyMock.anyInt
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.RETURNS_MOCKS
import org.powermock.api.mockito.PowerMockito
import org.powermock.api.support.membermodification.MemberModifier
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.reflect.Whitebox
import java.util.regex.Pattern

@RunWith(PowerMockRunner::class)
@PrepareForTest(AwesomeValidation::class, Range::class, ContextCompat::class)
class AwesomeValidationTest : TestCase() {

    private lateinit var mSpiedBasicValidator: BasicValidator
    private lateinit var mSpiedColorationValidator: ColorationValidator
    private lateinit var mSpiedUnderlabelValidator: UnderlabelValidator
    private lateinit var mSpiedTextInputLayoutValidator: TextInputLayoutValidator
    private lateinit var mSpiedAwesomeValidationBasicStyle: AwesomeValidation
    private lateinit var mSpiedAwesomeValidationColorationStyle: AwesomeValidation
    private lateinit var mSpiedAwesomeValidationUnderlabelStyle: AwesomeValidation
    private lateinit var mSpiedAwesomeValidationTextInputLayoutStyle: AwesomeValidation
    private lateinit var mMockContext: Context
    private val mColor = 256
    private val mColorResId = 512
    private val mStyleResId = 768

    private val mEmptySimpleCustomValidation = SimpleCustomValidation { false }
    private val mEmptyCustomValidation = CustomValidation { false }
    private val mEmptyCustomValidationCallback = CustomValidationCallback {
        // intentionally empty, no need to test here
    }
    private val mEmptyCustomErrorReset = CustomErrorReset {
        // intentionally empty, no need to test here
    }

    override fun setUp() {
        super.setUp()

        mSpiedBasicValidator = spy()
        mSpiedColorationValidator = spy()
        mSpiedUnderlabelValidator = spy()
        mSpiedTextInputLayoutValidator = spy()
        mSpiedAwesomeValidationBasicStyle = spy(AwesomeValidation(ValidationStyle.BASIC))
        mSpiedAwesomeValidationColorationStyle = spy(AwesomeValidation(ValidationStyle.COLORATION))
        mSpiedAwesomeValidationUnderlabelStyle = spy(AwesomeValidation(ValidationStyle.UNDERLABEL))
        mSpiedAwesomeValidationTextInputLayoutStyle = spy(AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT))

        MemberModifier.field(AwesomeValidation::class.java, "mValidator").set(mSpiedAwesomeValidationBasicStyle, mSpiedBasicValidator)
        MemberModifier.field(AwesomeValidation::class.java, "mValidator").set(mSpiedAwesomeValidationColorationStyle, mSpiedColorationValidator)
        MemberModifier.field(AwesomeValidation::class.java, "mValidator").set(mSpiedAwesomeValidationUnderlabelStyle, mSpiedUnderlabelValidator)
        MemberModifier.field(AwesomeValidation::class.java, "mValidator").set(mSpiedAwesomeValidationTextInputLayoutStyle, mSpiedTextInputLayoutValidator)
        mMockContext = mock()
    }

    fun testAwesomeValidationConstructBasicStyle() {
        assertTrue(Whitebox.getInternalState<AwesomeValidation>(mSpiedAwesomeValidationBasicStyle, "mValidator") is BasicValidator)
    }

    fun testAwesomeValidationConstructColorationStyle() {
        assertTrue(Whitebox.getInternalState<AwesomeValidation>(mSpiedAwesomeValidationColorationStyle, "mValidator") is ColorationValidator)
    }

    fun testAwesomeValidationConstructUnderlabelValidatorStyle() {
        assertTrue(Whitebox.getInternalState<AwesomeValidation>(mSpiedAwesomeValidationUnderlabelStyle, "mValidator") is UnderlabelValidator)
    }

    fun testAwesomeValidationConstructTextInputLayoutValidatorStyle() {
        assertTrue(Whitebox.getInternalState<AwesomeValidation>(mSpiedAwesomeValidationTextInputLayoutStyle, "mValidator") is TextInputLayoutValidator)
    }

    @Test(expected = UnsupportedOperationException::class)
    @Throws(Exception::class)
    fun testCheckIsColorationValidatorThrowsExceptionWithBasicStyle() {
        Whitebox.invokeMethod<AwesomeValidation>(mSpiedAwesomeValidationBasicStyle, "checkIsColorationValidator")
    }

    @Test(expected = UnsupportedOperationException::class)
    @Throws(Exception::class)
    fun testCheckIsColorationValidatorThrowsExceptionWithUnderlabelStyle() {
        Whitebox.invokeMethod<AwesomeValidation>(mSpiedAwesomeValidationUnderlabelStyle, "checkIsColorationValidator")
    }

    @Test(expected = UnsupportedOperationException::class)
    @Throws(Exception::class)
    fun testCheckIsColorationValidatorThrowsExceptionWithTextInputLayoutStyle() {
        Whitebox.invokeMethod<AwesomeValidation>(mSpiedAwesomeValidationTextInputLayoutStyle, "checkIsColorationValidator")
    }

    @Throws(Exception::class)
    fun testCheckIsColorationValidatorWithoutException() {
        Whitebox.invokeMethod<AwesomeValidation>(mSpiedAwesomeValidationColorationStyle, "checkIsColorationValidator")
    }

    @Test(expected = UnsupportedOperationException::class)
    @Throws(Exception::class)
    fun testCheckIsUnderlabelValidatorThrowsExceptionWithBasicStyle() {
        Whitebox.invokeMethod<AwesomeValidation>(mSpiedAwesomeValidationBasicStyle, "checkIsUnderlabelValidator")
    }

    @Test(expected = UnsupportedOperationException::class)
    @Throws(Exception::class)
    fun testCheckIsUnderlabelValidatorThrowsExceptionWithColorationStyle() {
        Whitebox.invokeMethod<AwesomeValidation>(mSpiedAwesomeValidationColorationStyle, "checkIsUnderlabelValidator")
    }

    @Test(expected = UnsupportedOperationException::class)
    @Throws(Exception::class)
    fun testCheckIsUnderlabelValidatorThrowsExceptionWithTextInputLayoutStyle() {
        Whitebox.invokeMethod<AwesomeValidation>(mSpiedAwesomeValidationTextInputLayoutStyle, "checkIsUnderlabelValidator")
    }

    @Throws(Exception::class)
    fun testCheckIsUnderlabelValidatorWithoutException() {
        Whitebox.invokeMethod<AwesomeValidation>(mSpiedAwesomeValidationUnderlabelStyle, "checkIsUnderlabelValidator")
    }

    @Test(expected = UnsupportedOperationException::class)
    @Throws(Exception::class)
    fun testCheckIsTextInputLayoutValidatorThrowsExceptionWithBasicStyle() {
        Whitebox.invokeMethod<AwesomeValidation>(mSpiedAwesomeValidationBasicStyle, "checkIsTextInputLayoutValidator")
    }

    @Test(expected = UnsupportedOperationException::class)
    @Throws(Exception::class)
    fun testCheckIsTextInputLayoutValidatorThrowsExceptionWithColorationStyle() {
        Whitebox.invokeMethod<AwesomeValidation>(mSpiedAwesomeValidationColorationStyle, "checkIsTextInputLayoutValidator")
    }

    @Test(expected = UnsupportedOperationException::class)
    @Throws(Exception::class)
    fun testCheckIsTextInputLayoutValidatorThrowsExceptionWithUnderlabelStyle() {
        Whitebox.invokeMethod<AwesomeValidation>(mSpiedAwesomeValidationUnderlabelStyle, "checkIsTextInputLayoutValidator")
    }

    @Throws(Exception::class)
    fun testCheckIsTextInputLayoutValidatorWithoutException() {
        Whitebox.invokeMethod<AwesomeValidation>(mSpiedAwesomeValidationTextInputLayoutStyle, "checkIsTextInputLayoutValidator")
    }

    @Test(expected = UnsupportedOperationException::class)
    @Throws(Exception::class)
    fun testCheckIsNotTextInputLayoutValidatorThrowsExceptionWithTextInputLayoutStyle() {
        Whitebox.invokeMethod<AwesomeValidation>(mSpiedAwesomeValidationTextInputLayoutStyle, "checkIsNotTextInputLayoutValidator")
    }

    @Throws(Exception::class)
    fun testCheckIsNotTextInputLayoutValidatorWithoutExceptionWithBasicStyle() {
        Whitebox.invokeMethod<AwesomeValidation>(mSpiedAwesomeValidationBasicStyle, "checkIsNotTextInputLayoutValidator")
    }

    @Throws(Exception::class)
    fun testCheckIsNotTextInputLayoutValidatorWithoutExceptionWithColorationStyle() {
        Whitebox.invokeMethod<AwesomeValidation>(mSpiedAwesomeValidationColorationStyle, "checkIsNotTextInputLayoutValidator")
    }

    @Throws(Exception::class)
    fun testCheckIsNotTextInputLayoutValidatorWithoutExceptionWithUnderlabelStyle() {
        Whitebox.invokeMethod<AwesomeValidation>(mSpiedAwesomeValidationUnderlabelStyle, "checkIsNotTextInputLayoutValidator")
    }

    @Throws(Exception::class)
    fun testSetContextForUnderlabelStyle() {
        PowerMockito.mockStatic(ContextCompat::class.java)
        PowerMockito.`when`(ContextCompat.getColor(eq(mMockContext), eq(anyInt()))).thenReturn(mColor)
        mSpiedAwesomeValidationUnderlabelStyle.setContext(mMockContext)
        verify(mSpiedUnderlabelValidator, times(1)).setContext(mMockContext)
    }

    @Test(expected = UnsupportedOperationException::class)
    @Throws(Exception::class)
    fun testSetContextForNonUnderlabelStyle() {
        mSpiedAwesomeValidationBasicStyle.setContext(mMockContext)
    }

    @Throws(Exception::class)
    fun testSetColorForColorationStyle() {
        mSpiedAwesomeValidationColorationStyle.setColor(mColor)
        verify(mSpiedColorationValidator, times(1)).setColor(mColor)
    }

    @Test(expected = UnsupportedOperationException::class)
    @Throws(Exception::class)
    fun testSetColorForNonColorationStyle() {
        mSpiedAwesomeValidationBasicStyle.setColor(mColor)
    }

    @Throws(Exception::class)
    fun testSetUnderlabelColorForUnderlabelStyle() {
        mSpiedAwesomeValidationUnderlabelStyle.setUnderlabelColor(mColor)
        verify(mSpiedUnderlabelValidator, times(1)).setColor(mColor)
    }

    @Test(expected = UnsupportedOperationException::class)
    @Throws(Exception::class)
    fun testSetUnderlabelColorForNonUnderlabelStyle() {
        mSpiedAwesomeValidationBasicStyle.setUnderlabelColor(mColor)
    }

    @Throws(Exception::class)
    fun testSetUnderlabelColorByResourceForUnderlabelStyle() {
        PowerMockito.mockStatic(ContextCompat::class.java)
        PowerMockito.`when`(ContextCompat.getColor(eq(mMockContext), eq(mColorResId))).thenReturn(mColor)
        mSpiedAwesomeValidationUnderlabelStyle.setContext(mMockContext)
        mSpiedAwesomeValidationUnderlabelStyle.setUnderlabelColorByResource(mColorResId)
        verify(mSpiedUnderlabelValidator, times(1)).setColorByResource(mColorResId)
    }

    @Test(expected = UnsupportedOperationException::class)
    @Throws(Exception::class)
    fun testSetUnderlabelColorByResourceForNonUnderlabelStyle() {
        mSpiedAwesomeValidationBasicStyle.setUnderlabelColorByResource(mColorResId)
    }

    @Throws(Exception::class)
    fun testSetTextInputLayoutErrorTextAppearanceForTextInputLayoutStyle() {
        mSpiedAwesomeValidationTextInputLayoutStyle.setTextInputLayoutErrorTextAppearance(mStyleResId)
        verify(mSpiedTextInputLayoutValidator, times(1)).setErrorTextAppearance(mStyleResId)
    }

    @Test(expected = UnsupportedOperationException::class)
    @Throws(Exception::class)
    fun testSetTextInputLayoutErrorTextAppearanceForNonTextInputLayoutStyle() {
        mSpiedAwesomeValidationBasicStyle.setTextInputLayoutErrorTextAppearance(mStyleResId)
    }

    @Throws(Exception::class)
    fun testAddValidation() {
        val viewId = 65535
        val confirmationViewId = 65536
        val errMsgId = 32768

        val mockView = PowerMockito.mock<View>(View::class.java, RETURNS_MOCKS)
        val mockEditText = PowerMockito.mock<EditText>(EditText::class.java, RETURNS_MOCKS)
        val mockConfirmationEditText = PowerMockito.mock<EditText>(EditText::class.java, RETURNS_MOCKS)
        val mockTextInputLayout = PowerMockito.mock<TextInputLayout>(TextInputLayout::class.java, RETURNS_MOCKS)
        val mockConfirmationTextInputLayout = PowerMockito.mock<TextInputLayout>(TextInputLayout::class.java, RETURNS_MOCKS)
        val mockPattern = PowerMockito.mock<Pattern>(Pattern::class.java, RETURNS_MOCKS)
        val mockRange = PowerMockito.mock<Range<*>>(Range::class.java, RETURNS_MOCKS)
        val mockRegex = PowerMockito.mock(String::class.java)
        val mockErrMsg = PowerMockito.mock(String::class.java)
        val mockNumericRange = mock<NumericRange>()

        val mockActivity = PowerMockito.mock<Activity>(Activity::class.java, RETURNS_MOCKS).apply {
            whenever(findViewById<View>(viewId)).thenReturn(mockEditText)
            whenever(findViewById<View>(confirmationViewId)).thenReturn(mockConfirmationEditText)
        }

        PowerMockito.whenNew(NumericRange::class.java).withArguments(mockRange).thenReturn(mockNumericRange)

        mSpiedAwesomeValidationBasicStyle.addValidation(mockEditText, mockRegex, mockErrMsg)
        verify(mSpiedBasicValidator, times(1)).set(mockEditText, mockRegex, mockErrMsg)

        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mockTextInputLayout, mockRegex, mockErrMsg)
        verify(mSpiedTextInputLayoutValidator, times(1)).set(mockTextInputLayout, mockRegex, mockErrMsg)

        mSpiedAwesomeValidationBasicStyle.addValidation(mockActivity, viewId, mockRegex, errMsgId)
        verify(mSpiedBasicValidator, times(1)).set(mockActivity, viewId, mockRegex, errMsgId)

        mSpiedAwesomeValidationBasicStyle.addValidation(mockEditText, mockPattern, mockErrMsg)
        verify(mSpiedBasicValidator, times(1)).set(mockEditText, mockPattern, mockErrMsg)

        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mockTextInputLayout, mockPattern, mockErrMsg)
        verify(mSpiedTextInputLayoutValidator, times(1)).set(mockTextInputLayout, mockPattern, mockErrMsg)

        mSpiedAwesomeValidationBasicStyle.addValidation(mockActivity, viewId, mockPattern, errMsgId)
        verify(mSpiedBasicValidator, times(1)).set(mockActivity, viewId, mockPattern, errMsgId)

        mSpiedAwesomeValidationBasicStyle.addValidation(mockEditText, mockRange, mockErrMsg)
        verify(mSpiedBasicValidator, times(1)).set(mockEditText, NumericRange(mockRange), mockErrMsg)

        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mockTextInputLayout, mockRange, mockErrMsg)
        verify(mSpiedTextInputLayoutValidator, times(1)).set(mockTextInputLayout, NumericRange(mockRange), mockErrMsg)

        mSpiedAwesomeValidationBasicStyle.addValidation(mockActivity, viewId, mockRange, errMsgId)
        verify(mSpiedBasicValidator, times(1)).set(mockActivity, viewId, NumericRange(mockRange), errMsgId)

        mSpiedAwesomeValidationBasicStyle.addValidation(mockConfirmationEditText, mockEditText, mockErrMsg)
        verify(mSpiedBasicValidator, times(1)).set(mockConfirmationEditText, mockEditText, mockErrMsg)

        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mockConfirmationTextInputLayout, mockTextInputLayout, mockErrMsg)
        verify(mSpiedTextInputLayoutValidator, times(1)).set(mockConfirmationTextInputLayout, mockTextInputLayout, mockErrMsg)

        mSpiedAwesomeValidationBasicStyle.addValidation(mockActivity, confirmationViewId, viewId, errMsgId)
        verify(mSpiedBasicValidator, times(1)).set(mockActivity, confirmationViewId, viewId, errMsgId)

        mSpiedAwesomeValidationBasicStyle.addValidation(mockEditText, mEmptySimpleCustomValidation, mockErrMsg)
        verify(mSpiedBasicValidator, times(1)).set(mockEditText, mEmptySimpleCustomValidation, mockErrMsg)

        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mockTextInputLayout, mEmptySimpleCustomValidation, mockErrMsg)
        verify(mSpiedTextInputLayoutValidator, times(1)).set(mockTextInputLayout, mEmptySimpleCustomValidation, mockErrMsg)

        mSpiedAwesomeValidationBasicStyle.addValidation(mockActivity, viewId, mEmptySimpleCustomValidation, errMsgId)
        verify(mSpiedBasicValidator, times(1)).set(mockActivity, viewId, mEmptySimpleCustomValidation, errMsgId)

        mSpiedAwesomeValidationBasicStyle.addValidation(mockView, mEmptyCustomValidation, mEmptyCustomValidationCallback, mEmptyCustomErrorReset, mockErrMsg)
        verify(mSpiedBasicValidator, times(1)).set(mockView, mEmptyCustomValidation, mEmptyCustomValidationCallback, mEmptyCustomErrorReset, mockErrMsg)

        mSpiedAwesomeValidationBasicStyle.addValidation(mockActivity, viewId, mEmptyCustomValidation, mEmptyCustomValidationCallback, mEmptyCustomErrorReset, errMsgId)
        verify(mSpiedBasicValidator, times(1)).set(mockActivity, viewId, mEmptyCustomValidation, mEmptyCustomValidationCallback, mEmptyCustomErrorReset, errMsgId)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testAddValidationForTextInputLayoutValidatorWithEditTextAndRegexThrowsException() {
        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mock<EditText>(), PowerMockito.mock(String::class.java), PowerMockito.mock(String::class.java))
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testAddValidationForTextInputLayoutValidatorWithEditTextAndPatternThrowsException() {
        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mock<EditText>(), PowerMockito.mock(Pattern::class.java), PowerMockito.mock(String::class.java))
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testAddValidationForTextInputLayoutValidatorWithEditTextAndRangeThrowsException() {
        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mock<EditText>(), PowerMockito.mock(Range::class.java), PowerMockito.mock(String::class.java))
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testAddValidationForTextInputLayoutValidatorWithEditTextAndConfirmationThrowsException() {
        mSpiedAwesomeValidationTextInputLayoutStyle.addValidation(mock<EditText>(), mock<EditText>(), PowerMockito.mock(String::class.java))
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testAddValidationForNonTextInputLayoutValidatorWithTextInputLayoutAndRegexThrowsException() {
        mSpiedAwesomeValidationBasicStyle.addValidation(mock<TextInputLayout>(), PowerMockito.mock(String::class.java), PowerMockito.mock(String::class.java))
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testAddValidationForNonTextInputLayoutValidatorWithTextInputLayoutAndPatternThrowsException() {
        mSpiedAwesomeValidationColorationStyle.addValidation(mock<TextInputLayout>(), PowerMockito.mock(Pattern::class.java), PowerMockito.mock(String::class.java))
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testAddValidationForNonTextInputLayoutValidatorWithTextInputLayoutAndRangeThrowsException() {
        mSpiedAwesomeValidationUnderlabelStyle.addValidation(mock<TextInputLayout>(), PowerMockito.mock(Range::class.java), PowerMockito.mock(String::class.java))
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testAddValidationForNonTextInputLayoutValidatorWithTextInputLayoutAndConfirmationThrowsException() {
        mSpiedAwesomeValidationBasicStyle.addValidation(mock<TextInputLayout>(), mock<TextInputLayout>(), PowerMockito.mock(String::class.java))
    }

    fun testValidate() {
        mSpiedAwesomeValidationBasicStyle.validate()
        verify(mSpiedBasicValidator, times(1)).trigger()
        assertEquals(mSpiedBasicValidator.trigger(), mSpiedAwesomeValidationBasicStyle.validate())
    }

    @Throws(Exception::class)
    fun testClear() {
        mSpiedAwesomeValidationBasicStyle.clear()
        verify(mSpiedBasicValidator, times(1)).halt()
    }
}