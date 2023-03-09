package com.beta.typi_linkedin

import android.annotation.SuppressLint
import android.app.ActionBar.LayoutParams
import android.content.Context
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ImageSpan
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.ExtractedText
import android.view.inputmethod.ExtractedTextRequest
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.UiThread
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import androidx.core.view.setMargins
import com.mixpanel.android.mpmetrics.MixpanelAPI
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TypiInputMethodService : InputMethodService(), OnKeyboardActionListener
{
    lateinit var keyboardView: TypiKeyboardView

    lateinit var keyboardRoot: LinearLayout

    lateinit var llSmily: View
    var letterBefore:Int=0
    override fun onCreateInputView(): View
    {
        inputConnection=currentInputConnection
        mixpanel = MixpanelAPI.getInstance(this, "04a8679d9c235e46100327d4f06c43aa", true);
        context = this
        keyboardRoot = layoutInflater.inflate(R.layout.root_keyboard_view, null) as LinearLayout
        keyboardView = keyboardRoot.findViewById(R.id.keyboard_view) as TypiKeyboardView
        //var  keyboardViewOptions = keyboardRoot.findViewById(R.id.keyboard_view_options) as TypiKeyboardView
        //keyboardView = layoutInflater.inflate(R.layout.keyboard_view, null) as ba.etf.us.typi.KeyboardView
        // get the KeyboardView and add our Keyboard layout to it
        var keyboard: Keyboard = Keyboard(this, R.xml.bosanska_google)
        caps=false
        keyboardView.keyboard = keyboard
        keyboardView.setOnKeyboardActionListener(this)
        keyboardView.isPreviewEnabled = false
        keyboardView.pripremi()
        TypiKeyboardView.context= keyboardView.context
        TypiKeyboardView.ovajView=keyboardView
        /*
         keyboard = Keyboard(this, R.xml.options)
         keyboardViewOptions.keyboard = keyboard
         keyboardViewOptions.isPreviewEnabled=false
         keyboardViewOptions.setOnKeyboardActionListener(this)
         */
        ViewMaker.allViewSetup(keyboardRoot, context, ::onKey)
        ViewMaker.optionsSetup(keyboardRoot, context, ::onKey)
        llSmily = ViewMaker.categorySetup(keyboardRoot, context, ::onKey)
        return keyboardRoot
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean)
    {
        inputConnection=currentInputConnection
        super.onStartInputView(info, restarting)
        var optionScroll = keyboardRoot.findViewById<HorizontalScrollView>(R.id.optionScroll)
        optionScroll.post { optionScroll.fullScroll(View.FOCUS_RIGHT) }
        //different keyboard_layout
        capsLock=false
        var highScore = Pref_Clean.getIntPref(context, "jezik")

        if (highScore == 0)
        {
            val keyboard = Keyboard(this, R.xml.bosanska_google)
            keyboardView.keyboard = keyboard
            caps=false
            keyboardView.pripremi()
        } else
        {
            val keyboard = Keyboard(this, R.xml.bosanska_google2)
            keyboardView.keyboard = keyboard
            caps=false
        }
    }

    override fun onFinishInputView(finishingInput: Boolean)
    {
        ViewMaker.closeChat()
        super.onFinishCandidatesView(finishingInput)
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray)
    {
        var ic= inputConnection
        if (primaryCode < 0)
        {

            when (primaryCode)
            {
                Keyboard.KEYCODE_DELETE ->
                {
                    val selectedText = ic.getSelectedText(0)
                    if (TextUtils.isEmpty(selectedText))
                    {
                        // no selection, so delete previous character
                        var t = ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                        if (t != null && t.length > 2 && t.endsWith(getString(R.string.gptChar)))
                        {
                            ic.deleteSurroundingText(2, 0)
                        } else
                        {
                            ic.deleteSurroundingText(1, 0)
                        }
                    } else
                    {
                        // delete the selection
                        ic.commitText("", 1)
                    }
                    var text = ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                    if (text==null||(text != null && text.length > 1 && text[text.length - 2] == '.' && text[text.length - 1] == ' '))
                    {
                        if (Pref_Clean.getIntPref(context, "jezik") == 0)
                        {
                            var capitalLettersKeyboard = Keyboard(this, R.xml.google_capslock)
                            keyboardView.keyboard = capitalLettersKeyboard
                            caps=true
                            keyboardView.pripremi()
                        } else
                        {
                            var capitalLettersKeyboard = Keyboard(this, R.xml.google2_capslock)
                            keyboardView.keyboard = capitalLettersKeyboard
                            caps=true
                        }
                    }


                }
                resources.getInteger(R.integer.gptCharCode) ->
                {
                    val ss = SpannableString(getString(R.string.gptChar))
                    val d = ContextCompat.getDrawable(this, android.R.drawable.btn_star)
                    d!!.setBounds(0, 0, d!!.intrinsicWidth, d!!.intrinsicHeight)
                    val span = ImageSpan(d!!, ImageSpan.ALIGN_BASELINE)
                    ss.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                    ic.commitText(ss, ss.length)
                }
                resources.getInteger(R.integer.lanCustom) ->
                {
                    window.window?.attributes?.let { keyboardView.returnInput(it.token) }
                }
                resources.getInteger(R.integer.gptBack) ->
                {
                    if (container != "")
                    {
                        ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                            ?.let { ic.setSelection(0, it.length) }
                        GptApi_Clean.paste("Old text", ic.getSelectedText(0).toString(), context)
                        ic.commitText(container, container.length)
                    }
                    val mixpanel = MixpanelAPI.getInstance(context, GptApi_Clean.token, true)
                    if(ViewMaker.chatPopup!=null)
                        mixpanel.track("TypiChatReverse")
                    else
                        mixpanel.track("TypiReverse")
                    //vracanjena verziju sto je usla u gpt
                }
                resources.getInteger(R.integer.clip) ->
                {
                    ViewMaker.clipBoard(keyboardRoot, context, ic)
                }
                -399  -> {
                    ViewMaker.helpPopupInput(
                        context,
                        keyboardRoot,
                        ::onKey,
                        arrayOf("Answer","Rephrase", "Chat","Correct grammar", "History","Reverse", "Translate", "Summarize","Change keyboard"),
                        ic,
                        keyCodes
                    )
                }
                //funkcija za rephrase dok ona ne proradi bolje
                resources.getInteger(R.integer.rephrase) ->
                {
                    ViewMaker.popupInput(
                        context,
                        keyboardRoot,
                        ::onKey,
                        arrayOf<String>("exciteing","formal","sincere","caring","friendly","humoristic", "sympathetic","sarcastic","authoritative"),
                        ic,
                        keyCodes,
                        "Rephrase this text so it sounds more: "
                    )
                    /*
                    var selectedText = ic.getSelectedText(0);
                    if (selectedText == null)
                    {
                        ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                            ?.let { ic.setSelection(0, it.length) }
                        selectedText = ic.getSelectedText(0);
                    }
                    if (selectedText != null)
                    {
                        ic.commitText("wait...", 7);
                        container = selectedText.toString()
                        GptApi_Clean.paste("Old text", selectedText.toString(), this);
                        GlobalScope.launch {
                            var result = GptApi_Clean.rase2(selectedText.toString(), context);
                            ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                                ?.let { ic.setSelection(it.length - 7, it.length) }

                            ic.commitText(result, result.length)
                        }
                    }
                    */
                }
                resources.getInteger(R.integer.gpt) ->
                {
                    if(ViewMaker.chatPopup!=null)
                        callChatGptForInput(keyCodes,ic,"")
                    else
                    callGptForInput(keyCodes, ic,"")
                }

                -2 ->
                {
                    var smallLettersKeyboard = Keyboard(this, R.xml.bosanska_google)
                    keyboardView.keyboard = smallLettersKeyboard
                    caps=false
                    keyboardView.pripremi()
                    Pref_Clean.setIntPref(context, "jezik", 0)
                    capsLock=false
                }
                -22 ->
                {
                    var smallLettersKeyboard = Keyboard(this, R.xml.bosanska_google2)
                    keyboardView.keyboard = smallLettersKeyboard
                    caps=false
                    Pref_Clean.setIntPref(context, "jezik", 1)
                    capsLock=false
                }
                -3 ->
                {
                    ViewMaker.emoji(keyboardRoot, context, ::onKey, llSmily)
                }
                -6 ->
                {
                    var numbersKeyboard = Keyboard(this, R.xml.numbers)
                    keyboardView.keyboard = numbersKeyboard
                    caps=false

                }
                -420 ->
                {
                    var bosanskaKeyboard = Keyboard(this, R.xml.bosanska_google)
                    keyboardView.keyboard = bosanskaKeyboard
                    caps=false
                    keyboardView.pripremi()
                    Pref_Clean.setIntPref(context, "jezik", 0)
                }
                -10 ->
                {
                    var specialKeyboard = Keyboard(this, R.xml.special_symbols)
                    keyboardView.keyboard = specialKeyboard
                    caps=false
                }
                resources.getInteger(R.integer.enter) ->
                {
                    //  this.requestHideSelf(0)
                    //  ic.commitText("\n", 1)
                    ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
                }
                //options
                resources.getInteger(R.integer.summerize) ->
                {
                    callGptForInput(keyCodes, ic, "Summarize this text: ")
                }
                resources.getInteger(R.integer.grammar) ->
                {
                    callGptForInput(keyCodes, ic, "Correct grammar in this text: ")
                }
                resources.getInteger(R.integer.chat) ->{
                    callChatGptForInput(intArrayOf(-1), ic, "")
                }
                resources.getInteger(R.integer.translate) ->
                {
                    ViewMaker.popupInput(
                        context,
                        keyboardRoot,
                        ::onKey,
                        arrayOf<String>("english", "german", "italian", "bosnian", "spanish"),
                        ic,
                        keyCodes,
                        "Translate this text to: "
                    )
                }
                -11->
                {
                    var smallLettersKeyboard = Keyboard(this, R.xml.google2_capslock)
                    keyboardView.keyboard = smallLettersKeyboard
                    caps=true
                }
                -1->{
                    var capitalLettersKeyboard = Keyboard(this, R.xml.google_capslock)
                    keyboardView.keyboard = capitalLettersKeyboard
                    caps=true
                    keyboardView.pripremi()
                }
            }
        } else
        {
            if (keyCodes.size == 1)
            {
                val emojiString = String(keyCodes, 0, keyCodes.size)
                ic.commitText(emojiString, 1)
            } else if (primaryCode == 32)
            {
                ic.commitText(primaryCode.toChar().toString(), 1)
                var text = ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                if (text != null && text.length > 1 && text[text.length - 2] == '.')
                {
                    if (Pref_Clean.getIntPref(context, "jezik") == 0)
                    {
                        var capitalLettersKeyboard = Keyboard(this, R.xml.google_capslock)
                        keyboardView.keyboard = capitalLettersKeyboard
                        caps=true
                        keyboardView.pripremi()
                    } else
                    {
                        var capitalLettersKeyboard = Keyboard(this, R.xml.google2_capslock)
                        keyboardView.keyboard = capitalLettersKeyboard
                        caps=true
                    }
                }
            }
            else
            {

                ic.commitText(primaryCode.toChar().toString(), 1)
                var text = ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                if ((!capsLock&&caps)||(text != null && text.length > 2 && text[text.length - 3] == '.' && text[text.length - 2] == ' '))
                {
                    Log.v("jel Capslock ", capsLock.toString()+", a caps "+caps.toString())
                    if (Pref_Clean.getIntPref(context, "jezik") == 0)
                    {
                        keyboardView.dismissPopupWindowImedietly(primaryCode-65)
                        var capitalLettersKeyboard = Keyboard(this, R.xml.bosanska_google)
                        keyboardView.keyboard = capitalLettersKeyboard
                        caps=false
                        keyboardView.pripremi()
                    } else
                    {
                        var capitalLettersKeyboard = Keyboard(this, R.xml.bosanska_google2)
                        keyboardView.keyboard = capitalLettersKeyboard
                        caps=false
                    }

                }
            }


        }
        letterBefore=primaryCode
    }

    override fun onText(text: CharSequence)
    {
    }

    override fun swipeLeft()
    {
    }

    override fun swipeRight()
    {
    }

    override fun swipeDown()
    {
    }

    override fun swipeUp()
    {
    }



    override fun onPress(primaryCode: Int)
    {

        if (primaryCode > 95 && primaryCode < 123&&Pref_Clean.getIntPref(context,"jezik")==0)
        {
            keyboardView.showPopupWindow(primaryCode - 97)
        }
        else  if (primaryCode > 64 && primaryCode < 91&&Pref_Clean.getIntPref(context,"jezik")==0)
        {
            keyboardView.showPopupWindow(primaryCode - 65)
        }
        else if(primaryCode==-1||primaryCode==-11)
        {
            Handler(Looper.getMainLooper()).postDelayed({
                if(proba==0)
                {
                    TypiInputMethodService.capsLock=true
                    TypiKeyboardView.capsNot()
                    proba=2
                }
                else{
                    proba=0
                    Log.d("caps","ne radi :.(")
                }
            }, 150)

        }
    }

    override fun onRelease(primaryCode: Int)
    {
        if (primaryCode > 96 && primaryCode < 123&&Pref_Clean.getIntPref(context,"jezik")==0)
        {
            keyboardView.dismissPopupWindow(primaryCode - 97)
        }
        else  if (primaryCode > 64 && primaryCode < 91&&Pref_Clean.getIntPref(context,"jezik")==0)
        {
            keyboardView.dismissPopupWindow(primaryCode - 65)
        }
        else if(primaryCode==-1||primaryCode==-11)
        {
            if(proba==2)
            {
                proba = 0
            }else
            {   proba=1
            }
        }
    }

    companion object
    {
        var textSize=18f //text size in chat
        var chatScroll:ScrollView?=null
        lateinit var mixpanel: MixpanelAPI
        lateinit var inputConnection: InputConnection //added to solve bug which prevent as from typeing in chat (redirects inputConnection of TypiInputMethodeService to edit text of chat, and return it after chat closes)
        var history="" //contains prompts that were sent in that chat before current one, and current one(enables chat memory)
        var capsLock:Boolean=true
        var output: LinearLayout?=null //layout that contains prompts and answers in chat
        var caps:Boolean=true
        var proba=0;
        lateinit var context: Context
        var container: String = ""//spremam sto je uslo u gpt kako bi mogao vratiti
        fun callGptForInput(keyCodes: IntArray, ic: InputConnection, str3: String = "")
        {
            val text = ic.getSelectedText(0)
            //ako je selectovan
            if (keyCodes[0] == -1 && text != null)
            {
                var textBefore=ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                ic.commitText("processing...", 13)
                var len:Int= textBefore?.length!!
                ic.setSelection(len+13,len+13)
                GptApi_Clean.paste("Old text", text.toString(), context)
                container = text.toString()
                GlobalScope.launch {
                    //response je ono sto chatgpt vrati
                    val response = GptApi_Clean.gptRequest(text.toString(), context, str3)
                    ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                        ?.let { ic.setSelection(it.length - 13, it.length) }
                    ic.commitText(response, response.length)
                    var extractedText=ExtractedTextRequest()
                    var et = ic.getExtractedText(extractedText,0)
                    var  text1=""
                    if(et!=null)
                        text1=et.text.toString()
                    ic.setSelection(text1.length,text1.length)
                }
            }
            //ako tekst nije selektovan
            else
            {
                var extractedText=ExtractedTextRequest()
               var et = ic.getExtractedText(extractedText,0)
                var  text1=""
                if(et!=null)
                    text1=et.text.toString()
                print("text11111111111111=")
                println(text1)
                ic.setSelection(0, text1.length)
             if (text1 != null)
                {
                    ic.commitText("processing...", 13)
                    container = text1.toString()
                    GptApi_Clean.paste("Old text", text1.toString(), context)
                    GlobalScope.launch {
                        //response je ono sto chatgpt vrati

                        val response = GptApi_Clean.gptRequest(text1.toString(), context, str3)
                        ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                            ?.let { ic.setSelection(it.length - 13, it.length) }
                        ic.commitText(response, response.length)
                    }
                }
            }
        }

        fun setOutputChat(tempText:String)
        {
            print("tem u fun=")
            println(tempText)
            var linearLayout1=LinearLayout(output?.context)
            //  linearLayout1.orientation=LinearLayout.L
            linearLayout1.setPadding(0,10,0,0)
            var textView=TextView(linearLayout1.context)
            textView.text="Typi:\n"+tempText
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize)
            textView.setTextColor(context.getColor(R.color.white))
            var btn=Button(linearLayout1.context)
            btn.background= context.resources.getDrawable(R.drawable.baseline_content_copy_24)
            var parms=LinearLayout.LayoutParams(60,70)
            parms.weight=0.05f
            parms.gravity=Gravity.CENTER_VERTICAL
            parms.setMargins(10,10,50,10)
            btn.layoutParams=parms
            var parms1=LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            parms1.weight=0.95f
            textView.layoutParams=parms1
            btn.setOnClickListener {
                GptApi_Clean.paste("chat answer",textView.text.toString().replace("Typi:\n",""), context)
            }
            textView.setOnClickListener {
                GptApi_Clean.paste("chat answer",textView.text.toString().replace("Typi:\n",""), context)
            }
            linearLayout1.addView(textView)
            linearLayout1.addView(btn)
            output?.addView(linearLayout1)
        }
        fun callChatGptForInput(keyCodes: IntArray, ic: InputConnection, str3: String)
        {
            val text = ic.getSelectedText(0)
            //ako je selectovan
            if (keyCodes[0] == -1 && text != null)
            {
                ic.commitText("", 0)
                GptApi_Clean.paste("Old text", str3+" "+text.toString(), context)
                container = str3+" "+text.toString()
                var textView=TextView(output?.context)
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize)
                textView.setText("\n\nYou: \n"+str3+" "+text.toString()+"\n\nprocessing...")
                textView.setTextColor(context.getColor(R.color.white))
                output?.addView(textView)
                history= history+"{\"role\":\"user\",\"content\":\"$str3 $text\"},"
                chatScroll?.post { chatScroll?.fullScroll(View.FOCUS_DOWN) }
                GlobalScope.launch {
                    //response je ono sto chatgpt vrati
                    val response = GptApi_Clean.gptRequest(text.toString(), context, str3, history)

                    println("1=")
                    Handler(Looper.getMainLooper()).post{
                        textView.text=textView.text.toString().replace("processing...","")
                        var tempText=response
                        setOutputChat(tempText)
                        chatScroll?.post { chatScroll?.fullScroll(View.FOCUS_DOWN) }
                    }
                }
            } else
            {

                var extractedText=ExtractedTextRequest()
                var et = ic.getExtractedText(extractedText,0)
                var  text1=""
                if(et!=null)
                    text1=et.text.toString()
                ic.setSelection(0, text1.length)
                if (text1 != null)
                {
                    ic.commitText("",0)
                    container =str3+" "+ text1.toString()
                    var textView=TextView(output?.context)
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize)
                    textView.text="\n\nYou: \n"+str3+" "+text1+"\n\nprocessing..."
                    textView.setTextColor(context.getColor(R.color.white))
                    output?.addView(textView)
                    GptApi_Clean.paste("Old text", str3+" "+text1, context)
                    history= history+"{\"role\":\"user\",\"content\":\"$str3 $text1\"},"
                    chatScroll?.post { chatScroll?.fullScroll(View.FOCUS_DOWN) }
                    GlobalScope.launch {
                        //response je ono sto chatgpt vrati
                        print("laucan====")
                        println(text1)
                        val response = GptApi_Clean.gptRequest(text1, context, str3, history)

                        println("2=")
                        Handler(Looper.getMainLooper()).post{
                            textView.text=textView.text.toString().replace("processing...","")
                            var tempText=response
                            setOutputChat(tempText)
                            chatScroll?.post { chatScroll?.fullScroll(View.FOCUS_DOWN) }
                        }
                    }
                }
            }
        }

    }
}