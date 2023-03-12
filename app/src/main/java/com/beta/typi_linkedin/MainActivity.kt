package com.beta.typi_linkedin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.method.LinkMovementMethod
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.mixpanel.android.mpmetrics.MixpanelAPI


class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(
            this
        ) { }
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
        var video:Button=findViewById(R.id.button5)
        video.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/shorts/HQVchjFBoPM")))
        }
        var tryBtn:Button=findViewById(R.id.textView2)
        tryBtn.setOnClickListener {
            var editText=findViewById<EditText>(R.id.input)
            editText.requestFocus()
            editText.setSelection(11)
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)

        }
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