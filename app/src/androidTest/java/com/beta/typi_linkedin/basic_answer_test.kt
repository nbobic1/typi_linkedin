package com.beta.typi_linkedin


import android.os.SystemClock.sleep
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.UiDevice
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class basic_answer_test
{


    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun basic_answer_test()
    {
        sleep(10000)
        val appCompatEditText = onView(
            allOf(
                withId(R.id.input), withText("Name a city"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("Name a city in italy"))



        val button = onView(
            allOf(
                withId(R.id.answer), withText("Answer"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.optionScroll),
                        0
                    ),
                    9
                )
            )
        )
        button.perform(scrollTo(), click())


        val editText = onView(
            allOf(
                withId(R.id.input), withText(" Rome."),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        editText.check(matches(withText(" Rome.")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View>
    {

        return object : TypeSafeMatcher<View>()
        {
            override fun describeTo(description: Description)
            {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean
            {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
