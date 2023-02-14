package com.example.typi_linkedin

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class Pref_Clean
{
    companion object{
        fun getIntPref(context: Context, key:String, defualt:Int=0):Int
        {
            val sharedPref = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
            return sharedPref.getInt(key, defualt)
        }
        fun setIntPref(context: Context, key:String, value:Int)
        {
            val sharedPref = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
            var editor=sharedPref.edit()
            editor.putInt(key, value)
            editor.apply()
        }
        fun setEncStringPref(context: Context, key: String, value: String)
        {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            val sharedPreferences = EncryptedSharedPreferences.create(
                context,
                "secret_shared_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            var editor=sharedPreferences.edit()
            editor.putString(key,value)
            editor.apply()
        }

    }
}