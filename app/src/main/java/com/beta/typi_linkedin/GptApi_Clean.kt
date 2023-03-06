package com.beta.typi_linkedin


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.mixpanel.android.mpmetrics.MixpanelAPI
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener


class GptApi_Clean
{
    //sadrži funkcije koje se ponavljaju kako bi kod bio pregleniji
    companion object
    {
        var token="04a8679d9c235e46100327d4f06c43aa"
        fun apiKey(context: Context):String{

            return  BuildConfig.API_KEY
        }
        //HTTP GLUPOSTI
        //api call
        suspend fun gptApiCall(text:String,context: Context,specialQuery:String="" ):String
        {
            var text=text
            if(specialQuery!="")
                text=specialQuery+text
            text=text.replace("\n"," ")
            var key = apiKey(context)
            val mediaType = "application/json".toMediaType()
            val requestBody = """{ "model": "gpt-3.5-turbo",
                                    "messages": [{"role": "user", "content": "$text"}],
                                    "max_tokens": 40,
                                    "temperature": 0.7,
                                    "frequency_penalty": 0.5 }"""
                .toRequestBody(mediaType)
            Log.d("key", requestBody.toString())
            val request = Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .addHeader("Authorization", key)
                .post(requestBody)
                .build()
            return withContext(Dispatchers.IO) {
                try
                {
                    OkHttpClient().newCall(request).execute().use {
                        var rez = it.body?.string() ?: ""
                        jsonToRez(rez, specialQuery, context).toString().replace("\n\n", " ")
                    }
                }
                catch (exx: Exception)
                {
                    "connection error"
                }
            }
        }
        fun jsonToRez(result:String,text:String,context:Context): String
        {
            try
            {
                println(result)
                val jsonObject = JSONTokener(result).nextValue() as JSONObject
                val choices1 = jsonObject.getString("choices")
                print("choices=")
                println(choices1)
                val choices = JSONTokener(choices1).nextValue() as JSONArray
                val tokens=JSONTokener(jsonObject.getString("usage")).nextValue() as JSONObject
                println("dgjladčgjakčdgjag")
                val tokensCount=tokens.getString("total_tokens").toInt()
                var tSto= Pref_Clean.getIntPref(context, "tokens")
                Pref_Clean.setIntPref(context, "tokens", tokensCount + tSto)
                val mixpanel: MixpanelAPI = MixpanelAPI.getInstance(context, token, true)
                if(text=="")
                {
                    val props = JSONObject()
                    props.put("TypiAnswer tokens", tokensCount)
                    mixpanel.track("TypiAnswer", props)
                }
                else if(text.contains("Translate"))
                {
                    val props = JSONObject()
                    props.put("TypiTranslate tokens", tokensCount)
                    mixpanel.track("TypiTranslate", props)
                }
                else if(text.contains("Correct"))
                {
                    val props = JSONObject()
                    props.put("TypiCorrect tokens", tokensCount)
                    mixpanel.track("TypiCorrect", props)
                }else if(text.contains("Summarize"))
                {
                    val props = JSONObject()
                    props.put("TypiSummarize tokens", tokensCount)
                    mixpanel.track("TypiSummarize", props)
                }else if(text.contains("Rephrase"))
                {
                    val props = JSONObject()
                    props.put("TypiRephrase tokens", tokensCount)
                    mixpanel.track("TypiRephrase", props)
                }
                print("kill seisapg")
                var kte=choices.getJSONObject(0).getString("message")
                println(kte)
                var message=JSONTokener(kte).nextValue() as JSONObject

                return  message.getString("content")

            }
            catch (ex: Exception)
            {
                return "Error"
            }
        }

        fun countMatches(string: String, pattern: String): Int {
            return Regex(pattern).findAll(string).count()
        }
        suspend fun gptRequest(str:String, context:Context,str2:String=""):String
        {
            var k=countMatches(str,context.getString(R.string.gptChar));
            if(k>1)
            {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "There shuold be only 1 special sign!", Toast.LENGTH_LONG).show()
                }
                return str;
            }

            else if(k==1)
            {
                return gptApiCall(str.split(context.getString(R.string.gptChar))[0],context,str.split(context.getString(R.string.gptChar))[1])
                  //  return apiCallEdit(str.split(context.getString(R.string.gptChar))[0],str.split(context.getString(R.string.gptChar))[1],context)
            }
            else
            {
             return gptApiCall(str,context,str2)
            }


        }
/*
SVE RADI S NASE STRANE; NJIHOV ENDOPOINT SUCKS
        suspend fun apiCallEdit(text: String, edit: String, context: Context): String
        {
            Log.d("zahtjev2",text+"="+edit)
            //uzimanje kljuca
            val masterKey: MasterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
                context,
                "secret_shared_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            var key = sharedPreferences.getString("key", "").toString()

            //pravljenje zahtjeva
            val mediaType = "application/json".toMediaType()
            val requestBody = """{ "model": "text-davinci-edit-001",
                                    "input": "$text",
                                    "instruction": "$edit"
                                     }"""
                .toRequestBody(mediaType)
            Log.d("key", requestBody.toString())
            val request = Request.Builder()
                .url("https://api.openai.com/v1/edits")
                .addHeader("Authorization", key)
                .post(requestBody)
                .build()
            return withContext(Dispatchers.IO) {
                OkHttpClient().newCall(request).execute().use {
                    jsonToRez(it.body?.string() ?: "")
                }
            }
        }
*/
        //CLIPBOARD
        fun paste(lable:String,text:String,context: Context)
        {
            val clipboard: ClipboardManager =context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(lable, text)
            clipboard.setPrimaryClip(clip)
        }
    }

}