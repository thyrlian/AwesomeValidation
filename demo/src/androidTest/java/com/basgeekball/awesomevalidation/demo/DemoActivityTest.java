package com.basgeekball.awesomevalidation.demo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.basgeekball.awesomevalidation.demo.ViewMatcher.hasAncestorAndSelfMatcher;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(AndroidJUnit4.class)
public class DemoActivityTest {

    @Rule
    public ActivityTestRule<DemoActivity> mActivityRule = new ActivityTestRule<>(DemoActivity.class);

    @Test
    public void testValidatePass() {
        enterText(R.id.edt_userid, "Flash");
        enterText(R.id.edt_password, "FatherPrime#3");
        enterText(R.id.edt_password_confirmation, "FatherPrime#3");
        enterText(R.id.edt_firstname, "Dwyane");
        enterText(R.id.edt_lastname, "Wade");
        enterText(R.id.edt_email, "D-Wade@nba.com");
        enterText(R.id.edt_ip, "184.154.83.119");
        enterText(R.id.edt_tel, "312-455-4000");
        enterText(R.id.edt_zipcode, "53201");
        enterText(R.id.edt_year, "1982");
        enterText(R.id.edt_height, "1.93");
        clickButton(R.id.btn_done);
        scrollToTheBottom();
        assertViewIsDisplayed(R.id.container_success);
    }

    @Test
    public void testValidateTextInputLayoutPass() {
        navigateToMenuItem(3);
        enterTextToTextInputLayout(R.id.til_email, "Jean-ClaudeVanDamme@jcvdworld.com");
        enterTextToTextInputLayout(R.id.til_password, "Muscles4Brussels!");
        enterTextToTextInputLayout(R.id.til_password_confirmation, "Muscles4Brussels!");
        enterTextToTextInputLayout(R.id.til_year, "1960");
        clickButton(R.id.btn_done);
        scrollToTheBottom();
        assertViewIsDisplayed(R.id.container_success);
    }

    private ViewInteraction findView(int viewId) {
        return onView(withId(viewId));
    }

    private void enterText(int viewId, String text) {
        findView(viewId).perform(scrollTo(), typeText(text), closeSoftKeyboard());
    }

    private void enterTextToTextInputLayout(int viewIdOfTextInputLayout, String text) {
        Matcher<View> ancestorMatcher = withId(viewIdOfTextInputLayout);
        Matcher<View> selfMatcher = withClassName(containsString("TextInputEditText"));
        onView(hasAncestorAndSelfMatcher(ancestorMatcher, selfMatcher)).perform(scrollTo(), typeText(text), closeSoftKeyboard());
    }

    private void clickButton(int viewId) {
        findView(viewId).perform(click());
    }

    private void assertViewIsDisplayed(int viewId) {
        findView(viewId).check(matches(isDisplayed()));
    }

    private void scrollToTheBottom() {
        findView(R.id.scroll_view).perform(swipeUp());
    }

    private void navigateToMenuItem(int menuItemIdx) {
        onView(withContentDescription(R.string.app_name)).perform(click());
        Context context = InstrumentationRegistry.getTargetContext();
        if (context != null) {
            String menuItem = context.getResources().getStringArray(R.array.styles)[menuItemIdx];
            onView(withText(menuItem)).perform(click());
        }
    }

}
