package com.beta.typi_linkedin

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
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
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
            caps=true
            keyboardView.pripremi()
        } else
        {
            val keyboard = Keyboard(this, R.xml.bosanska_google2)
            keyboardView.keyboard = keyboard
            caps=true
        }
    }


    override fun onKey(primaryCode: Int, keyCodes: IntArray)
    {
        val ic = currentInputConnection ?: return
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
                    mixpanel.track("TypiReverse")
                    //vracanjena verziju sto je usla u gpt
                }
                resources.getInteger(R.integer.clip) ->
                {
                    ViewMaker.clipBoard(keyboardRoot, context, ic)
                }
                //funkcija za rephrase dok ona ne proradi bolje
                resources.getInteger(R.integer.rephrase) ->
                {
                    ViewMaker.popupInput(
                        context,
                        keyboardRoot,
                        ::onKey,
                        arrayOf<String>("excited","formal","sincere","caring","friendly","humoristic", "sympathetic","sarcastic","authoritative"),
                        ic,
                        keyCodes,
                        "Rephrase this text so it sounds"
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
                    callGptForInput(keyCodes, ic)
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
                    if (Pref_Clean.getIntPref(context, "jezik") == 0)
                    {
                        keyboardView.dismissPopupWindowImedietly(primaryCode-65)
                        var capitalLettersKeyboard = Keyboard(this, R.xml.bosanska_google)
                        keyboardView.keyboard = capitalLettersKeyboard
                        caps=false
                        println("tri")
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
        println("Pressed="+primaryCode)

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
                    println("prikaz")
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
        println("relead="+primaryCode)
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
            }}

    }

    companion object
    {
        lateinit var mixpanel: MixpanelAPI
        var capsLock:Boolean=true
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
                ic.commitText("wait...", 7)
                GptApi_Clean.paste("Old text", text.toString(), context)
                container = text.toString()
                GlobalScope.launch {
                    //response je ono sto chatgpt vrati
                    println("pozivam iz 1")
                    val response = GptApi_Clean.gptRequest(text.toString(), context, str3)
                    ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                        ?.let { ic.setSelection(it.length - 7, it.length) }
                    ic.commitText(response, response.length)
                }
            } else
            {
                ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                    ?.let { ic.setSelection(0, it.length) }
                var text = ic.getSelectedText(0);
                if (text != null)
                {
                    ic.commitText("wait...", 7)
                    container = text.toString()
                    GptApi_Clean.paste("Old text", text.toString(), context)
                    GlobalScope.launch {
                        //response je ono sto chatgpt vrati
                        println("pozivam iz 2")
                        val response = GptApi_Clean.gptRequest(text.toString(), context, str3)
                        ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                            ?.let { ic.setSelection(it.length - 7, it.length) }
                        ic.commitText(response, response.length)
                    }
                }
            }
        }
    }
}