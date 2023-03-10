package com.beta.typi_linkedin

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class tutu {

    var  uiDevice: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun prijeKlika(){

        val scenario: ActivityScenario<MainActivity> = activityRule.getScenario()
        onView(withId(R.id.input)).perform(typeText(" in italy"))
        //uiDevice.click(100,1000)

      //  onView(withId(R.id.input)).check(matches(withText("Name a city")))
    }

}