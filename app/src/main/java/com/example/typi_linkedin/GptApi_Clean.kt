package com.example.typi_linkedin


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener




import android.os.Handler
import android.os.Looper
import java.net.URL

import android.widget.Toast
import java.io.*
import java.net.HttpURLConnection


class GptApi_Clean
{
    //sadrži funkcije koje se ponavljaju kako bi kod bio pregleniji
    companion object
    {
        fun apiKey(context: Context):String{

            return  BuildConfig.API_KEY
        }
        //HTTP GLUPOSTI
        //api call
        fun gptApiCall(text:String,context: Context,specialQuery:String="", ):String
        {
            var text=text
            if(specialQuery!="")
                text=specialQuery+text
            var key = GptApi_Clean.apiKey(context)
            val url = URL("https://api.openai.com/v1/completions")
            val postData = """{ "model": "text-davinci-003",
                                    "prompt": "$text",
                                    "max_tokens": 7,
                                    "temperature": 0.7,
                                    "frequency_penalty": 0.5 }"""
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.doOutput = true
            conn.setRequestProperty("Content-Type", "application/json")
            conn.setRequestProperty("Content-Length", postData.length.toString())
            conn.setRequestProperty("Authorization", key)
            conn.useCaches = false

            var rez:String=""
            DataOutputStream(conn.outputStream).use { it.writeBytes(postData) }
            BufferedReader(InputStreamReader(conn.inputStream)).use { br ->
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    println(line)
                    rez+=line
                }
            }
            return  jsonToRez(rez)
        }
        fun jsonToRez(result:String): String
        {
            val jsonObject = JSONTokener(result).nextValue() as JSONObject
            val choices1 = jsonObject.getString("choices")
            val choices = JSONTokener(choices1).nextValue() as JSONArray
            return  choices.getJSONObject(0).getString("text")
        }

        fun countMatches(string: String, pattern: String): Int {
            return Regex(pattern).findAll(string).count()
        }
        suspend fun gptRequest(str:String, context:Context,str2:String=""):String
        {
            var k=countMatches(str,context.getString(R.string.gptChar));
            println("k="+k)
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