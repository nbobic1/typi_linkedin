package com.beta.typi_linkedin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.provider.Settings
import android.text.method.LinkMovementMethod
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
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

        var defaultKbBtn: Button = findViewById(R.id.defaultKbBtn)
        var alternativeBtn: Button = findViewById(R.id.alternative)
        val textView = findViewById<Button>(R.id.textView3)
        var tryBtn:Button=findViewById(R.id.textView2)
        var defaultViedo=findViewById<Button>(R.id.defaultVideo)
        var inputTry=findViewById<EditText>(R.id.inputTry)

        defaultViedo.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.veed.io/view/76de9444-80bc-4bdc-b355-74ad320b1641?panel=share")))
        }
        alternativeBtn.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/shorts/HQVchjFBoPM")))
        }
        var tryVideo=findViewById<Button>(R.id.tryVideo)
        tryVideo.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/shorts/YXwc-TIPC-w?feature=share")))
        }
        var enableKbBtn: Button = findViewById(R.id.enableKbBtn)
        var video:Button=findViewById(R.id.button5)
        enableKbBtn.setOnClickListener {
            defaultViedo.visibility=LinearLayout.VISIBLE
            video.visibility=LinearLayout.VISIBLE
            defaultKbBtn.visibility=LinearLayout.VISIBLE


            val mixpanel = MixpanelAPI.getInstance(applicationContext, GptApi_Clean.token, true)
            mixpanel.identify(mixpanel.distinctId)
            mixpanel.track("TypiEnable")
            val enableIntent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
            enableIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(enableIntent)
        }

        defaultKbBtn.setOnClickListener {

            inputTry.visibility=LinearLayout.VISIBLE
            textView.visibility=LinearLayout.VISIBLE
            defaultViedo.visibility=LinearLayout.VISIBLE
            tryVideo.visibility=LinearLayout.VISIBLE
            tryBtn.visibility=LinearLayout.VISIBLE
            video.visibility=LinearLayout.VISIBLE
            defaultKbBtn.visibility=LinearLayout.VISIBLE
            alternativeBtn.visibility=LinearLayout.VISIBLE

            val mixpanel = MixpanelAPI.getInstance(applicationContext, GptApi_Clean.token, true)
            mixpanel.track("TypiDefault")
            val imeManager: InputMethodManager =
                (applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
                    showInputMethodPicker()
                }
        }
        video.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.veed.io/view/783a6556-9ed6-4bb7-af39-24de7bbe2b1e?panel=share")))
        }
        tryBtn.setOnClickListener {
            var editText=findViewById<EditText>(R.id.inputTry)
            editText.requestFocus()
            editText.setSelection(11)
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
            var sc=findViewById<ScrollView>(R.id.mainScroll)
            sc.post { sc.fullScroll(ScrollView.FOCUS_DOWN) }

        }
        textView.setOnClickListener {
            val mixpanel: MixpanelAPI = MixpanelAPI.getInstance(applicationContext, GptApi_Clean.token, true)
            mixpanel.track("TypiFeedback")
            val feedbackIntent = Intent(applicationContext,feedback::class.java)
            startActivity(feedbackIntent)
        }
        textView.movementMethod = LinkMovementMethod.getInstance()
        inputTry.visibility=LinearLayout.GONE
        textView.visibility=LinearLayout.GONE
        defaultViedo.visibility=LinearLayout.GONE
        tryVideo.visibility=LinearLayout.GONE
        tryBtn.visibility=LinearLayout.GONE
        defaultKbBtn.visibility=LinearLayout.GONE
        alternativeBtn.visibility=LinearLayout.GONE
    }


}