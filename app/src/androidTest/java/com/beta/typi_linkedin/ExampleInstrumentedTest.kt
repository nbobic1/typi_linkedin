package com.beta.typi_linkedin

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.SystemClock.sleep
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest
{
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext()
    {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        //val pokreniDetalje: Intent = Intent(ApplicationProvider.getApplicationContext(),MainActivity::class.java)
        //pokreniDetalje.flags=FLAG_ACTIVITY_NEW_TASK
       // val scenario =appContext.startActivity(pokreniDetalje)
     //   onView(withId(R.id.movie_website)).perform(click())
       // intended(hasAction(Intent.ACTION_VIEW))
        //Intents.release()
       // assertEquals("com.example.typi_linkedin", appContext.packageName)
        var  uiDevice: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
Log.d("dsfs","glupannnnnnnnnnn")
        sleep(20000)
        val appCompatEditText = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.input), ViewMatchers.withText("Name a city"),
                ViewMatchers.isDisplayed()
            )
        )
        val dugmeStep2 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.textView2)
            )
        )

        dugmeStep2.perform(click())
        uiDevice.pressKeyCode(32)
        appCompatEditText.perform(ViewActions.replaceText("Name a city in italy"))


    }
}