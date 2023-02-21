package com.beta.typi_linkedin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mixpanel.android.mpmetrics.MixpanelAPI


class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Pref_Clean.setIntPref(this,"jezik",0)
        var enableKbBtn: Button = findViewById(R.id.enableKbBtn)
        enableKbBtn.setOnClickListener {
            val mixpanel = MixpanelAPI.getInstance(applicationContext, GptApi_Clean.token, true)
            mixpanel.identify(mixpanel.distinctId)
            mixpanel.track("TypiEnable")
            val enableIntent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
            enableIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(enableIntent)
        }

        var defaultKbBtn: Button = findViewById(R.id.defaultKbBtn)
        defaultKbBtn.setOnClickListener {
            val mixpanel = MixpanelAPI.getInstance(applicationContext, GptApi_Clean.token, true)
            mixpanel.track("TypiDefault")
            val imeManager: InputMethodManager =
                (applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
                    showInputMethodPicker()
                }
        }
        var tryBtn:Button=findViewById(R.id.textView2)
        tryBtn.setOnClickListener {
            var editText=findViewById<EditText>(R.id.input)
            editText.requestFocus()
            editText.setSelection(11)
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)

        }
        // Get the string resource that contains the link URL

// Create a SpannableString with your custom text

// Set the SpannableString to your TextView
        val textView = findViewById<Button>(R.id.textView3)
        textView.setOnClickListener {
            val mixpanel: MixpanelAPI = MixpanelAPI.getInstance(applicationContext, GptApi_Clean.token, true)
            mixpanel.track("TypiFeedback")
            val feedbackIntent = Intent(applicationContext,feedback::class.java)
            startActivity(feedbackIntent)
        }
        textView.movementMethod = LinkMovementMethod.getInstance()

    }


}