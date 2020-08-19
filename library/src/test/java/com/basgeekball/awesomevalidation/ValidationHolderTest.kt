package com.basgeekball.awesomevalidation

import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.view.View
import android.widget.EditText
import com.basgeekball.awesomevalidation.exception.BadLayoutException
import com.basgeekball.awesomevalidation.model.NumericRange
import com.basgeekball.awesomevalidation.utility.custom.CustomErrorReset
import com.basgeekball.awesomevalidation.utility.custom.CustomValidation
import com.basgeekball.awesomevalidation.utility.custom.CustomValidationCallback
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation
import com.nhaarman.mockitokotlin2.mock
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.powermock.api.easymock.PowerMock
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.util.regex.Pattern

@RunWith(PowerMockRunner::class)
@PrepareForTest(ValidationHolder::class)
class ValidationHolderTest : TestCase() {

    private lateinit var mMockEditText: EditText
    private lateinit var mMockConfirmationEditText: EditText
    private lateinit var mMockTextInputLayout: TextInputLayout
    private lateinit var mMockConfirmationTextInputLayout: TextInputLayout
    private lateinit var mMockView: View
    private lateinit var mMockCustomErrorReset: CustomErrorReset
    private lateinit var mValidationHolderRegexTypeWithEditText: ValidationHolder
    private lateinit var mValidationHolderRangeTypeWithEditText: ValidationHolder
    private lateinit var mValidationHolderConfirmationTypeWithEditText: ValidationHolder
    private lateinit var mValidationHolderSimpleCustomTypeWithEditText: ValidationHolder
    private lateinit var mValidationHolderRegexTypeWithTextInputLayout: ValidationHolder
    private lateinit var mValidationHolderRangeTypeWithTextInputLayout: ValidationHolder
    private lateinit var mValidationHolderConfirmationTypeWithTextInputLayout: ValidationHolder
    private lateinit var mValidationHolderSimpleCustomTypeWithTextInputLayout: ValidationHolder
    private lateinit var mValidationHolderCustomTypeWithSomeSortOfView: ValidationHolder

    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        mMockEditText = mock()
        mMockConfirmationEditText = mock()
        mMockTextInputLayout = mock()
        mMockConfirmationTextInputLayout = mock()
        mMockView = mock()
        val mockPattern = PowerMock.createMock(Pattern::class.java)
        val mockNumericRange = mock<NumericRange>()
        val mockSimpleCustomValidation = mock<SimpleCustomValidation>()
        val mockCustomValidation = mock<CustomValidation>()
        val mockCustomValidationCallback = mock<CustomValidationCallback>()
        mMockCustomErrorReset = mock()
        val mockErrMsg = PowerMock.createMock(String::class.java)
        mValidationHolderRegexTypeWithEditText = ValidationHolder(mMockEditText, mockPattern, mockErrMsg)
        mValidationHolderRangeTypeWithEditText = ValidationHolder(mMockEditText, mockNumericRange, mockErrMsg)
        mValidationHolderConfirmationTypeWithEditText = ValidationHolder(mMockConfirmationEditText, mMockEditText, mockErrMsg)
        mValidationHolderSimpleCustomTypeWithEditText = ValidationHolder(mMockEditText, mockSimpleCustomValidation, mockErrMsg)
        mValidationHolderRegexTypeWithTextInputLayout = ValidationHolder(mMockTextInputLayout, mockPattern, mockErrMsg)
        mValidationHolderRangeTypeWithTextInputLayout = ValidationHolder(mMockTextInputLayout, mockNumericRange, mockErrMsg)
        mValidationHolderConfirmationTypeWithTextInputLayout = ValidationHolder(mMockConfirmationTextInputLayout, mMockTextInputLayout, mockErrMsg)
        mValidationHolderSimpleCustomTypeWithTextInputLayout = ValidationHolder(mMockTextInputLayout, mockSimpleCustomValidation, mockErrMsg)
        mValidationHolderCustomTypeWithSomeSortOfView = ValidationHolder(mMockView, mockCustomValidation, mockCustomValidationCallback, mMockCustomErrorReset, mockErrMsg)
    }

    fun testIsRegexTypeTrue() {
        assertTrue(mValidationHolderRegexTypeWithEditText.isRegexType)
        assertTrue(mValidationHolderRegexTypeWithTextInputLayout.isRegexType)
    }

    fun testIsRegexTypeFalse() {
        assertFalse(mValidationHolderRangeTypeWithEditText.isRegexType)
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isRegexType)
        assertFalse(mValidationHolderSimpleCustomTypeWithEditText.isRegexType)
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isRegexType)
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isRegexType)
        assertFalse(mValidationHolderSimpleCustomTypeWithTextInputLayout.isRegexType)
        assertFalse(mValidationHolderCustomTypeWithSomeSortOfView.isRegexType)
    }

    fun testIsRangeTypeTrue() {
        assertTrue(mValidationHolderRangeTypeWithEditText.isRangeType)
        assertTrue(mValidationHolderRangeTypeWithTextInputLayout.isRangeType)
    }

    fun testIsRangeTypeFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isRangeType)
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isRangeType)
        assertFalse(mValidationHolderSimpleCustomTypeWithEditText.isRangeType)
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isRangeType)
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isRangeType)
        assertFalse(mValidationHolderSimpleCustomTypeWithTextInputLayout.isRangeType)
        assertFalse(mValidationHolderCustomTypeWithSomeSortOfView.isRangeType)
    }

    fun testIsConfirmationTypeTrue() {
        assertTrue(mValidationHolderConfirmationTypeWithEditText.isConfirmationType)
        assertTrue(mValidationHolderConfirmationTypeWithTextInputLayout.isConfirmationType)
    }

    fun testIsConfirmationTypeFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isConfirmationType)
        assertFalse(mValidationHolderRangeTypeWithEditText.isConfirmationType)
        assertFalse(mValidationHolderSimpleCustomTypeWithEditText.isConfirmationType)
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isConfirmationType)
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isConfirmationType)
        assertFalse(mValidationHolderSimpleCustomTypeWithTextInputLayout.isConfirmationType)
        assertFalse(mValidationHolderCustomTypeWithSomeSortOfView.isConfirmationType)
    }

    fun testIsSimpleCustomTypeTrue() {
        assertTrue(mValidationHolderSimpleCustomTypeWithEditText.isSimpleCustomType)
        assertTrue(mValidationHolderSimpleCustomTypeWithTextInputLayout.isSimpleCustomType)
    }

    fun testIsSimpleCustomTypeFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isSimpleCustomType)
        assertFalse(mValidationHolderRangeTypeWithEditText.isSimpleCustomType)
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isSimpleCustomType)
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isSimpleCustomType)
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isSimpleCustomType)
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isSimpleCustomType)
        assertFalse(mValidationHolderCustomTypeWithSomeSortOfView.isSimpleCustomType)
    }

    fun testIsCustomTypeTrue() {
        assertTrue(mValidationHolderCustomTypeWithSomeSortOfView.isCustomType)
    }

    fun testIsCustomTypeFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isCustomType)
        assertFalse(mValidationHolderRangeTypeWithEditText.isCustomType)
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isCustomType)
        assertFalse(mValidationHolderSimpleCustomTypeWithEditText.isCustomType)
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isCustomType)
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isCustomType)
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isCustomType)
        assertFalse(mValidationHolderSimpleCustomTypeWithTextInputLayout.isCustomType)
    }

    fun testIsEditTextViewTrue() {
        assertTrue(mValidationHolderRegexTypeWithEditText.isEditTextView)
        assertTrue(mValidationHolderRangeTypeWithEditText.isEditTextView)
        assertTrue(mValidationHolderConfirmationTypeWithEditText.isEditTextView)
        assertTrue(mValidationHolderSimpleCustomTypeWithEditText.isEditTextView)
    }

    fun testIsEditTextViewFalse() {
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isEditTextView)
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isEditTextView)
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isEditTextView)
        assertFalse(mValidationHolderSimpleCustomTypeWithTextInputLayout.isEditTextView)
        assertFalse(mValidationHolderCustomTypeWithSomeSortOfView.isEditTextView)
    }

    fun testIsTextInputLayoutViewTrue() {
        assertTrue(mValidationHolderRegexTypeWithTextInputLayout.isTextInputLayoutView)
        assertTrue(mValidationHolderRangeTypeWithTextInputLayout.isTextInputLayoutView)
        assertTrue(mValidationHolderConfirmationTypeWithTextInputLayout.isTextInputLayoutView)
        assertTrue(mValidationHolderSimpleCustomTypeWithTextInputLayout.isTextInputLayoutView)
    }

    fun testIsTextInputLayoutViewFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isTextInputLayoutView)
        assertFalse(mValidationHolderRangeTypeWithEditText.isTextInputLayoutView)
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isTextInputLayoutView)
        assertFalse(mValidationHolderSimpleCustomTypeWithEditText.isTextInputLayoutView)
        assertFalse(mValidationHolderCustomTypeWithSomeSortOfView.isTextInputLayoutView)
    }

    fun testIsSomeSortOfViewTrue() {
        assertTrue(mValidationHolderCustomTypeWithSomeSortOfView.isSomeSortOfView)
    }

    fun testIsSomeSortOfViewFalse() {
        assertFalse(mValidationHolderRegexTypeWithEditText.isSomeSortOfView)
        assertFalse(mValidationHolderRangeTypeWithEditText.isSomeSortOfView)
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isSomeSortOfView)
        assertFalse(mValidationHolderSimpleCustomTypeWithEditText.isSomeSortOfView)
        assertFalse(mValidationHolderRegexTypeWithTextInputLayout.isSomeSortOfView)
        assertFalse(mValidationHolderRangeTypeWithTextInputLayout.isSomeSortOfView)
        assertFalse(mValidationHolderConfirmationTypeWithTextInputLayout.isSomeSortOfView)
        assertFalse(mValidationHolderSimpleCustomTypeWithTextInputLayout.isSomeSortOfView)
    }

    fun testGetTextFromEditText() {
        val text = "OK"
        val mockEditable = mock<Editable>()
        `when`(mMockEditText.text).thenReturn(mockEditable)
        `when`(mockEditable.toString()).thenReturn(text)
        assertEquals(text, mValidationHolderRegexTypeWithEditText.text)
        assertEquals(text, mValidationHolderRangeTypeWithEditText.text)
        assertEquals(text, mValidationHolderConfirmationTypeWithEditText.text)
        assertEquals(text, mValidationHolderSimpleCustomTypeWithEditText.text)
    }

    fun testGetTextFromTextInputLayout() {
        val text = "OK"
        val mockEditText = mock<EditText>()
        val mockEditable = mock<Editable>()
        `when`(mMockTextInputLayout.editText).thenReturn(mockEditText)
        `when`(mockEditText.text).thenReturn(mockEditable)
        `when`(mockEditable.toString()).thenReturn(text)
        assertEquals(text, mValidationHolderRegexTypeWithTextInputLayout.text)
        assertEquals(text, mValidationHolderRangeTypeWithTextInputLayout.text)
        assertEquals(text, mValidationHolderConfirmationTypeWithTextInputLayout.text)
        assertEquals(text, mValidationHolderSimpleCustomTypeWithTextInputLayout.text)
    }

    @Test(expected = BadLayoutException::class)
    fun testGetTextFromTextInputLayoutThrowsException() {
        `when`(mMockTextInputLayout.editText).thenReturn(null)
        mValidationHolderRegexTypeWithTextInputLayout.text
    }

    fun testGetTextReturnsNull() {
        val mockEditText: EditText? = null
        val mockTextInputLayout: TextInputLayout? = null
        val mockPattern = PowerMock.createMock(Pattern::class.java)
        val mockErrMsg = PowerMock.createMock(String::class.java)
        mValidationHolderRegexTypeWithEditText = ValidationHolder(mockEditText, mockPattern, mockErrMsg)
        mValidationHolderRegexTypeWithTextInputLayout = ValidationHolder(mockTextInputLayout, mockPattern, mockErrMsg)
        assertNull(mValidationHolderRegexTypeWithEditText.text)
        assertNull(mValidationHolderRegexTypeWithTextInputLayout.text)
    }

    fun testGetConfirmationTextFromEditText() {
        val text = "OK"
        val mockEditable = mock<Editable>()
        `when`(mMockConfirmationEditText.text).thenReturn(mockEditable)
        `when`(mockEditable.toString()).thenReturn(text)
        assertEquals(text, mValidationHolderConfirmationTypeWithEditText.confirmationText)
    }

    fun testGetConfirmationTextFromTextInputLayout() {
        val text = "OK"
        val mockEditText = mock<EditText>()
        val mockEditable = mock<Editable>()
        `when`<EditText>(mMockConfirmationTextInputLayout.editText).thenReturn(mockEditText)
        `when`(mockEditText.text).thenReturn(mockEditable)
        `when`(mockEditable.toString()).thenReturn(text)
        assertEquals(text, mValidationHolderConfirmationTypeWithTextInputLayout.confirmationText)
    }

    @Test(expected = BadLayoutException::class)
    fun testGetConfirmationTextFromTextInputLayoutThrowsException() {
        `when`<EditText>(mMockConfirmationTextInputLayout.editText).thenReturn(null)
        mValidationHolderConfirmationTypeWithTextInputLayout.confirmationText
    }

    fun testGetConfirmationTextReturnsNull() {
        assertNull(mValidationHolderRegexTypeWithEditText.confirmationText)
        assertNull(mValidationHolderRangeTypeWithEditText.confirmationText)
        assertNull(mValidationHolderSimpleCustomTypeWithEditText.confirmationText)
        assertNull(mValidationHolderRegexTypeWithTextInputLayout.confirmationText)
        assertNull(mValidationHolderRangeTypeWithTextInputLayout.confirmationText)
        assertNull(mValidationHolderSimpleCustomTypeWithTextInputLayout.confirmationText)
        assertNull(mValidationHolderCustomTypeWithSomeSortOfView.confirmationText)
    }

    fun testGetEditTextFromEditText() {
        assertEquals(mMockEditText, mValidationHolderRegexTypeWithEditText.editText)
        assertEquals(mMockEditText, mValidationHolderRangeTypeWithEditText.editText)
        assertEquals(mMockConfirmationEditText, mValidationHolderConfirmationTypeWithEditText.editText)
        assertEquals(mMockEditText, mValidationHolderSimpleCustomTypeWithEditText.editText)
    }

    fun testGetEditTextFromTextInputLayout() {
        val mockEditText = mock<EditText>()
        val mockConfirmationEditText = mock<EditText>()
        `when`<EditText>(mMockTextInputLayout.editText).thenReturn(mockEditText)
        `when`<EditText>(mMockConfirmationTextInputLayout.editText).thenReturn(mockConfirmationEditText)
        assertEquals(mockEditText, mValidationHolderRegexTypeWithTextInputLayout.editText)
        assertEquals(mockEditText, mValidationHolderRangeTypeWithTextInputLayout.editText)
        assertEquals(mockConfirmationEditText, mValidationHolderConfirmationTypeWithTextInputLayout.editText)
        assertEquals(mockEditText, mValidationHolderSimpleCustomTypeWithTextInputLayout.editText)
    }

    fun testGetTextInputLayout() {
        assertEquals(mMockTextInputLayout, mValidationHolderRegexTypeWithTextInputLayout.textInputLayout)
        assertEquals(mMockTextInputLayout, mValidationHolderRangeTypeWithTextInputLayout.textInputLayout)
        assertEquals(mMockConfirmationTextInputLayout, mValidationHolderConfirmationTypeWithTextInputLayout.textInputLayout)
        assertEquals(mMockTextInputLayout, mValidationHolderSimpleCustomTypeWithTextInputLayout.textInputLayout)
    }

    fun testGetTextInputLayoutReturnsNull() {
        assertNull(mValidationHolderRegexTypeWithEditText.textInputLayout)
        assertNull(mValidationHolderRangeTypeWithEditText.textInputLayout)
        assertNull(mValidationHolderConfirmationTypeWithEditText.textInputLayout)
        assertNull(mValidationHolderSimpleCustomTypeWithEditText.textInputLayout)
        assertNull(mValidationHolderCustomTypeWithSomeSortOfView.textInputLayout)
    }

    fun testGetView() {
        assertEquals(mMockView, mValidationHolderCustomTypeWithSomeSortOfView.view)
    }

    fun testGetViewReturnsNull() {
        assertNull(mValidationHolderRegexTypeWithEditText.view)
        assertNull(mValidationHolderRangeTypeWithEditText.view)
        assertNull(mValidationHolderConfirmationTypeWithEditText.view)
        assertNull(mValidationHolderSimpleCustomTypeWithEditText.view)
        assertNull(mValidationHolderRegexTypeWithTextInputLayout.view)
        assertNull(mValidationHolderRangeTypeWithTextInputLayout.view)
        assertNull(mValidationHolderConfirmationTypeWithTextInputLayout.view)
        assertNull(mValidationHolderSimpleCustomTypeWithTextInputLayout.view)
    }

    fun testIsVisibleTrue() {
        `when`(mMockEditText.visibility).thenReturn(View.VISIBLE)
        `when`(mMockTextInputLayout.visibility).thenReturn(View.VISIBLE)
        `when`(mMockView.visibility).thenReturn(View.VISIBLE)
        assertTrue(mValidationHolderRegexTypeWithEditText.isVisible)
        assertTrue(mValidationHolderRangeTypeWithTextInputLayout.isVisible)
        assertTrue(mValidationHolderCustomTypeWithSomeSortOfView.isVisible)
    }

    fun testIsVisibleFalse() {
        `when`(mMockConfirmationEditText.visibility).thenReturn(View.INVISIBLE)
        `when`(mMockTextInputLayout.visibility).thenReturn(View.GONE)
        `when`(mMockView.visibility).thenReturn(View.INVISIBLE)
        assertFalse(mValidationHolderConfirmationTypeWithEditText.isVisible)
        assertFalse(mValidationHolderSimpleCustomTypeWithTextInputLayout.isVisible)
        assertFalse(mValidationHolderCustomTypeWithSomeSortOfView.isVisible)
    }

    fun testResetCustomError() {
        mValidationHolderCustomTypeWithSomeSortOfView.resetCustomError()
        verify(mMockCustomErrorReset, times(1)).reset(mValidationHolderCustomTypeWithSomeSortOfView)
    }
}