package com.example.typi_linkedin



import android.content.Context
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ImageSpan
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TypiInputMethodService : InputMethodService(), OnKeyboardActionListener
{
    lateinit var keyboardView: TypiKeyboardView
    var context: Context = this
    var container:String=""//spremam sto je uslo u gpt kako bi mogao vratiti

    override fun onCreateInputView(): View
    {
        println("keycoe="+Keyboard.KEYCODE_DELETE)

        var tu=layoutInflater.inflate(R.layout.gif, null)
        keyboardView=tu.findViewById(R.id.keyboard_view) as TypiKeyboardView
        //keyboardView = layoutInflater.inflate(R.layout.keyboard_view, null) as ba.etf.us.typi.KeyboardView
        // get the KeyboardView and add our Keyboard layout to it
        val keyboard: Keyboard = Keyboard(this, R.xml.google)
        keyboardView.keyboard = keyboard
        keyboardView.setOnKeyboardActionListener(this)
        var packLIst=tu.findViewById(R.id.packsList) as LinearLayout
      /*  for(i in 1..10)
        {
            var img= WebView(packLIst.context)
         var k=LayoutParams(300,300)

            img.layoutParams=k
            img.loadUrl("https://i.giphy.com/media/l0ExqbRzq05DHIlJm/100w.gif")
          packLIst.addView(img)
        }*/
        return tu
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean)
    {
        super.onStartInputView(info, restarting)
        Log.d("key1", "wiiiiiiiiiiiiii")
        var highScore = Clean.getIntPref(context, "moj")
        Log.d("key", "auuuuuuuuuuuuuuuuu")
        if (highScore == 1)
        {
            val keyboard = Keyboard(this, R.xml.google)
            keyboardView.keyboard = keyboard
        } else
        {
            val keyboard = Keyboard(this, R.xml.google)
            keyboardView.keyboard = keyboard
        }
    }


    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean
    {
        println("wii6ii")

        return super.onKeyLongPress(keyCode, event)
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray)
    {
        val ic = currentInputConnection ?: return
        when (primaryCode)
        {

            Keyboard.KEYCODE_DELETE ->
            {
                val selectedText = ic.getSelectedText(0)

                //val keyboard = Keyboard(this, R.xml.key_layout)
                //keyboardView.keyboard = keyboard
                if (TextUtils.isEmpty(selectedText))
                {
                    // no selection, so delete previous character
                        var t=ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                    if(t!=null&&t.length>2&&t.endsWith(getString(R.string.gptChar)))
                    {
                        ic.deleteSurroundingText(2, 0)
                    }
                    else
                    ic.deleteSurroundingText(1, 0)
                } else
                {
                    // delete the selection
                    ic.commitText("", 1)
                }
                var text=ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)

            }
            resources.getInteger(R.integer.gptCharCode)->{
                val ss = SpannableString(getString(R.string.gptChar))
                val d = ContextCompat.getDrawable(this, android.R.drawable.btn_star)
                d!!.setBounds(0, 0, d!!.intrinsicWidth, d!!.intrinsicHeight)
                val span = ImageSpan(d!!, ImageSpan.ALIGN_BASELINE)
                ss.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                ic.commitText(ss,ss.length)
               // ic.commitText("\uDB40\uDD34",2)
            }
            32->{
                window.window?.attributes?.let { keyboardView.returnInput(it.token) }
            }
            -66 ->
            {
                //vracanjena verziju sto je usla u gpt
                ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                    ?.let { ic.setSelection(0, it.length) }
                Clean.paste("Old text", ic.getSelectedText(0).toString(), context)
                ic.commitText(container, container.length)
            }
            -68 ->
            {
               // val keyboard = Keyboard(this, R.xml.keyboard_layout2)
              //  keyboardView.keyboard = keyboard
                val imeManager: InputMethodManager =
                    applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imeManager.showInputMethodPicker()
            }
            -33->{

                ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                    ?.let { Log.d("key88", it.length.toString())
                    Log.d("key88",it.toString())
                    Log.d("key88",it[it.length-1].toInt().toString())}
            }
            -67 ->
            {
                val keyboard = Keyboard(this, R.xml.google)
                keyboardView.keyboard = keyboard
            }
            -69 ->
            {
                //gpt dugme
                val text = ic.getSelectedText(0)
                //ako je selectovan
                if (keyCodes[0] == -1 && text != null)
                {

                    ic.commitText("wait...", 7)
                    Clean.paste("Old text", text.toString(), context)
                    container=text.toString()
                    GlobalScope.launch {
                        //response je ono sto chatgpt vrati
                        val response = Clean.gptRequest(text.toString(),context)
                        ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                            ?.let { ic.setSelection(it.length - 7, it.length) }
                        ic.commitText(response, response.length)
                    }
                } else{
                   ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                        ?.let { ic.setSelection(0, it.length) }
                    var text=ic.getSelectedText(0);
                    ic.commitText("wait...", 7)
                    container=text.toString()
                    Clean.paste("Old text", text.toString(), context)
                    GlobalScope.launch {
                        //response je ono sto chatgpt vrati


                       val response = Clean.gptRequest(text.toString(), context)
                        ic.getTextBeforeCursor(Integer.MAX_VALUE, 0)
                            ?.let { ic.setSelection(it.length - 7, it.length) }
                        ic.commitText(response, response.length)
                    }
                }

            }
            else ->
            {
                val code = primaryCode.toChar()
                Log.d("key", code.toString())

                Log.d("key1", keyCodes[0].toString())

                ic.commitText(code.toString(), 1)
            }
        }
    }

    override fun onPress(primaryCode: Int)
    {
    }

    override fun onRelease(primaryCode: Int)
    {
    }

    override fun onText(text: CharSequence)
    {
        Log.d("kay", "ma goriiiiiiiiii selecteddd imamoooooo")
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
}