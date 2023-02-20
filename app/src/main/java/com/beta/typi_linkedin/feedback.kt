package com.beta.typi_linkedin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject
import org.w3c.dom.Text

class feedback : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        val linkUrl = resources.getString(R.string.hyperlink)
        var text=findViewById<TextView>(R.id.textView4)
        var token=Pref_Clean.getIntPref(applicationContext,"tokens")
        var tokens1=token*0.00003
            text.setText("This service would cost You "+tokens1+"$ and the cost of this service is based on your usage. Considering the value you receive from our services, would You pay to use this app?")
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl))
        var da=findViewById<Button>(R.id.button4)
        da.setOnClickListener {

            val mixpanel: MixpanelAPI = MixpanelAPI.getInstance(applicationContext, GptApi_Clean.token, true)
            val props = JSONObject()
            props.put("TypiYes tokens", token)
            props.put("TypiYes price", tokens1)
            mixpanel.track("TypiYes", props)
            startActivity(browserIntent)
            finish()
        }
        var no=findViewById<Button>(R.id.button)
        no.setOnClickListener {

            val mixpanel: MixpanelAPI = MixpanelAPI.getInstance(applicationContext, GptApi_Clean.token, true)
            val props = JSONObject()
            props.put("TypiNo tokens", token)
            props.put("TypiNo price", tokens1)
            mixpanel.track("TypiNo", props)
            startActivity(browserIntent)
            finish()
        }
    }
}