package com.example.typi_linkedin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Pref_Clean.setIntPref(this,"jezik",0)
        var enableKbBtn: Button = findViewById(R.id.enableKbBtn)
        enableKbBtn.setOnClickListener {
             val enableIntent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
              enableIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
              this.startActivity(enableIntent)
        }

        var defaultKbBtn: Button = findViewById(R.id.defaultKbBtn)
        defaultKbBtn.setOnClickListener {
            val imeManager: InputMethodManager = applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imeManager.showInputMethodPicker()
            }
        // Get the string resource that contains the link URL
        val linkUrl = resources.getString(R.string.hyperlink)

// Create a SpannableString with your custom text
        val customText = "Step 4: Make a meaningful impact on our journey! Once you've used Typi, simply fill out our form and help us grow! "
        val spannableString = SpannableString(customText)

// Create a clickable URL span for the link
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                // Open the link URL in a web browser
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl))
                startActivity(browserIntent)
            }
        }
        spannableString.setSpan(clickableSpan, 0, customText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

// Set the SpannableString to your TextView
        val textView = findViewById<TextView>(R.id.textView3)
        textView.text = spannableString
        textView.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl))
            startActivity(browserIntent)
        }
        textView.movementMethod = LinkMovementMethod.getInstance()

    }


}