package com.example.typi_linkedin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Button

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }
}