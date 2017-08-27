package com.basgeekball.awesomevalidation.demo;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;

public class ViewMatcher {

    private ViewMatcher() {}

    public static Matcher<View> hasAncestorAndSelfMatcher(final Matcher<View> ancestorMatcher, final Matcher<View> selfMatcher) {
        checkNotNull(ancestorMatcher);
        checkNotNull(selfMatcher);
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("has parent: ");
                ancestorMatcher.describeTo(description);
                description.appendText(" and self: ");
                selfMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return (hasParent(view) && selfMatcher.matches(view));
            }

            private boolean hasParent(View view) {
                ViewParent parent = view.getParent();
                if (!(parent instanceof ViewGroup)) return false;
                return ancestorMatcher.matches(parent) || hasParent((View) parent);
            }
        };
    }

}
