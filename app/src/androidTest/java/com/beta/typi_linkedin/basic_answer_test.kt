package com.beta.typi_linkedin

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.filters.SdkSuppress
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertThat


private const val BASIC_SAMPLE_PACKAGE = "com.beta.typi_linkedin"
private const val LAUNCH_TIMEOUT = 5000L
private const val STRING_TO_BE_TYPED = "UiAutomator"

@RunWith(AndroidJUnit4::class)
class basic_answer_test {

    private lateinit var mDevice: UiDevice

    @Before
    fun startMainActivityFromHomeScreen()
    {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation())

        // Start from the home screen
        mDevice.pressHome()

        // Wait for launcher
        val launcherPackage = getLauncherPackageName()
        assertThat(launcherPackage, notNullValue())
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT)

        // Launch the blueprint app
        val context = getApplicationContext<Context>()
        val intent = context.packageManager
            .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) // Clear out any previous instances
        context.startActivity(intent)

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT)
    }

    @Test
    fun checkPreconditions()
    {
        assertThat(mDevice, notNullValue())
    }

    @Test
    fun testChangeText_sameActivity()
    {
        // Type text and then press the button.
   //      .setText(STRING_TO_BE_TYPED)
        mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "OPEN VIDEO"))
            .click()

        // Verify the test is displayed in the Ui
        val changedText: UiObject2 = mDevice
            .wait(
                Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "textToBeChanged")),
                500 /* wait 500ms */
            )
        assertThat(changedText.getText(), `is`(equalTo(STRING_TO_BE_TYPED)))
    }

    @Test
    fun testChangeText_newActivity()
    {
        // Type text and then press the button.
        mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "editTextUserInput"))
            .setText(STRING_TO_BE_TYPED)
        mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "activityChangeTextBtn"))
            .click()

        // Verify the test is displayed in the Ui
        val changedText: UiObject2 = mDevice
            .wait(
                Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "show_text_view")),
                500 /* wait 500ms */
            )
        //assertEqual(changedText.getText(), STRING_TO_BE_TYPED)
    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private fun getLauncherPackageName(): String
    {
        // Create launcher Intent
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)

        // Use PackageManager to get the launcher package name
        val pm = getApplicationContext<Context>().packageManager
        val resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return resolveInfo!!.activityInfo.packageName
    }
}
