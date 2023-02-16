package com.example.typi_linkedin

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ImageSpan
import android.view.KeyEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TypiInputMethodService : InputMethodService(), OnKeyboardActionListener
{
    lateinit var keyboardView: TypiKeyboardView

    lateinit var keyboardRoot: LinearLayout
    var context: Context = this
    var container: String = ""//spremam sto je uslo u gpt kako bi mogao vratiti
    lateinit var llSmily:View
    override fun onCreateInputView(): View
    {
         keyboardRoot = layoutInflater.inflate(R.layout.root_keyboard_view, null) as LinearLayout
        keyboardView = keyboardRoot.findViewById(R.id.keyboard_view) as TypiKeyboardView
       var  keyboardViewOptions = keyboardRoot.findViewById(R.id.keyboard_view_options) as TypiKeyboardView
        //keyboardView = layoutInflater.inflate(R.layout.keyboard_view, null) as ba.etf.us.typi.KeyboardView
        // get the KeyboardView and add our Keyboard layout to it
        var keyboard: Keyboard = Keyboard(this, R.xml.google)
        keyboardView.keyboard = keyboard
        keyboardView.setOnKeyboardActionListener(this)
        keyboardView.isPreviewEnabled=false
        keyboard = Keyboard(this, R.xml.options)
        keyboardViewOptions.keyboard = keyboard
        keyboardViewOptions.isPreviewEnabled=false
        keyboardViewOptions.setOnKeyboardActionListener(this)
        ViewMaker.allViewSetup(keyboardRoot,context,::onKey)
        llSmily=ViewMaker.categorySetup(keyboardRoot,context,::onKey)
        return keyboardRoot
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean)
    {
        super.onStartInputView(info, restarting)
        //different keyboard_layout
       /* we dont have this functionality yet
        var highScore = Pref_Clean.getIntPref(context, "moj")

        if (highScore == 1)
        {
            val keyboard = Keyboard(this, R.xml.google)
            keyboardView.keyboard = keyboard
        } else
        {
            val keyboard = Keyboard(this, R.xml.google)
            keyboardView.keyboard = keyboard
        }  */
    }


    override fun onKey(primaryCode: Int, keyCodes: IntArray)
    {
        val ic = currentInputConnection ?: return
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
                println("ne b bunkcioniral")
                window.window?.attributes?.let { keyboardView.returnInput(it.token) }
            }
            resources.getInteger(R.integer.gptBack) ->
            {
                //vracanjena verziju sto je usla u gpt
                ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                    ?.let { ic.setSelection(0, it.length) }
                GptApi_Clean.paste("Old text", ic.getSelectedText(0).toString(), context)
                ic.commitText(container, container.length)
            }
            resources.getInteger(R.integer.clip) ->
            {
                // val keyboard = Keyboard(this, R.xml.keyboard_layout2)
                //  keyboardView.keyboard = keyboard
            /*    val imeManager: InputMethodManager =
                    applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imeManager.showInputMethodPicker()
                */
                ViewMaker.clipBoard(keyboardRoot,context,ic)
            }

            //funkcija za rephrase dok ona ne proradi bolje
            resources.getInteger(R.integer.rephrase) ->
            {
                    ViewMaker.returnInput(context,keyboardRoot,::onKey)
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
                        var result = GptApi_Clean.rephrase2(selectedText.toString(), context);
                        ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                            ?.let { ic.setSelection(it.length - 7, it.length) }

                        ic.commitText(result, result.length)
                    }
                }
                */

            }
            resources.getInteger(R.integer.gpt)->
            {
                println("djsalfjdlasjfladjfladlsfsalnabkndbkanbćanbćab")
                //gpt dugme
                val text = ic.getSelectedText(0)
                //ako je selectovan
                if (keyCodes[0] == -1 && text != null)
                {
                    ic.commitText("wait...", 7)
                    GptApi_Clean.paste("Old text", text.toString(), context)
                    container = text.toString()
                    GlobalScope.launch {
                        //response je ono sto chatgpt vrati
                        val response = GptApi_Clean.gptRequest(text.toString(), context)
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
                            val response = GptApi_Clean.gptRequest(text.toString(), context)
                            ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                                ?.let { ic.setSelection(it.length - 7, it.length) }
                            ic.commitText(response, response.length)
                        }
                    }
                }
            }
            -1 ->
            {
                var capitalLettersKeyboard = Keyboard(this, R.xml.google_capslock)
                keyboardView.keyboard = capitalLettersKeyboard
            }
            -2 ->
            {
                var smallLettersKeyboard = Keyboard(this, R.xml.google)
                keyboardView.keyboard = smallLettersKeyboard
            }
            -3 ->
            {

                 ViewMaker.emoji(keyboardRoot,context,::onKey,llSmily)
            }
            -6 ->
            {
                var numbersKeyboard = Keyboard(this, R.xml.numbers)
                keyboardView.keyboard = numbersKeyboard
            }
            -7 ->
            {
                var lettersKeyboard = Keyboard(this, R.xml.google)
                keyboardView.keyboard = lettersKeyboard
            }
            -420->
            {
                var bosanskaKeyboard = Keyboard(this, R.xml.bosanska_google)
                keyboardView.keyboard = bosanskaKeyboard

            }

            -10 ->
            {
                var specialKeyboard = Keyboard(this, R.xml.special_symbols)
                keyboardView.keyboard = specialKeyboard
            }

            -9 ->
            {
                this.requestHideSelf(0)
            }
            else ->
            {
                if (keyCodes.size == 1) {
                    val emojiString = String(keyCodes, 0, keyCodes.size)
                    ic.commitText(emojiString,1)

                } else {
                    ic.commitText(primaryCode.toChar().toString(), 1)
                }


            }
        }
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

    override fun onPress(p0: Int)
    {
    }

    override fun onRelease(p0: Int)
    {
    }

}