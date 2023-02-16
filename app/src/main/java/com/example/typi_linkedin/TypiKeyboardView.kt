package com.example.typi_linkedin

import android.app.ActionBar.LayoutParams
import android.content.Context
import android.graphics.Canvas
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.IBinder
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TypiKeyboardView(context: Context, attrs: AttributeSet) : KeyboardView(context, attrs) {
//ovaj klas omogucuje cutom crtanje dugmadi kako sva dugmad ne bi morala izgledati isto
    //da bi radio mora se u xml koji se u njega ucitava staviti on kao root tag

    //when u hold letter there will be additional options for that letter, that
    override fun onLongPress(popupKey: Keyboard.Key?): Boolean
    {
        var keyCode= popupKey?.codes?.get(0)
        var label=popupKey?.label




        Log.v("POPUP KEY je ", popupKey.toString()+" a label "+keyCode)
        if (isPopupKey(label as String)) {
            if (popupKey != null) {
                Log.v("POPUP CODE JE ",keyCode.toString())
                showPopupWindow(keyCode, popupKey.x.toFloat(), popupKey.y.toFloat())
            }
        }



        /*  if (popupKey != null)
          {
              println("gori"+popupKey.codes[0])
              val custom: View = LayoutInflater.from(context)
                  .inflate(R.layout.popup_input_picker, FrameLayout(context))
              var vie=custom.findViewById<TextView>(R.id.textView3)

              val popup = PopupWindow(context)
              popup.contentView = custom
              if(popup.isShowing()){
                  popup.update(200, 200, 30, 30);
              } else {
                  popup.setWidth(100);
                  popup.setHeight(100);
                  popup.showAtLocation(this, Gravity.NO_GRAVITY, 100, 100);
              }
              vie.setOnClickListener {
                  popup.dismiss()
              }
          }

         */
          return super.onLongPress(popupKey)


    }


    // Override the onLongPress method to handle long-press events for keys with popup characters


    // Check if the specified key code is a popup key
    private fun isPopupKey(label:String): Boolean {
        return label=="c" || label=="s" || label=="d"
    }

    private fun getPopupCharactersForKey(code:Int):ArrayList<String>{
        if (code==99) return arrayListOf("č","ć")
        if (code==100) return arrayListOf("đ","dž")
        return if (code==115) arrayListOf("š")
        else arrayListOf()
    }
    // Show the popup window for the specified key
   private fun showPopupWindow(label: Int?, x: Float, y: Float) {
        val popupCharacters = label?.let { getPopupCharactersForKey(it) }
        Log.v("POPUP CHARACTERS ",popupCharacters.toString())
        val popupView = popupCharacters?.let { createPopupView(it) }
        val popupWindow = PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        popupWindow.showAtLocation(this, Gravity.NO_GRAVITY, x.toInt(), y.toInt())
    }

    // Create a custom view for the popup window
    private fun createPopupView(popupCharacters: ArrayList<String>): View {
        val context = context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_layout, null)
        val popupText = popupView.findViewById<TextView>(R.id.popup_text)

        val popupTextBuilder = SpannableStringBuilder()

        for (i in popupCharacters) {
            val popup = PopupWindow(context)
            // ovo ne moze ovako nego mora nekako jedno po jedno i onClick metoda

            val popupCharWithSeparator = "$i"
            popupTextBuilder.append(popupCharWithSeparator)


        }

       popupText.setText(popupTextBuilder, TextView.BufferType.SPANNABLE)
        return popupView
    }


    // Dismiss the popup window
    private fun dismissPopupWindow() {
        // If the popup window is currently displayed, dismiss it
    }

    // In the onTouchEvent method, handle the MotionEvent.ACTION_UP and MotionEvent.ACTION_CANCEL events
// If the user releases the key without selecting a popup character, dismiss the popup window:
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                dismissPopupWindow()
            }
        }
        return super.onTouchEvent(event)
    }



    fun returnInput(token: IBinder)
{
    var tk= FrameLayout(context)
    tk.layoutParams= ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
    val custom: View = LayoutInflater.from(context)
        .inflate(R.layout.popup_input_picker,tk)
    var vie=custom.findViewById<TextView>(R.id.textView3)

    var lista=custom.findViewById<LinearLayout>(R.id.ll)

    val imeManager: InputMethodManager =
        context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    for(i in imeManager.inputMethodList)
    {
        var tt = TextView(context)
        tt.setText(i.loadLabel(context.packageManager))
        tt.textSize= 20.0F
        tt.setOnClickListener {
            imeManager.setInputMethod(token, i.id)
        }
        lista.addView(tt)
    }
    val popup = PopupWindow(context)
    popup.contentView = custom

    if(popup.isShowing()){
        popup.update(200, 200, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    } else {
        popup.setWidth(LayoutParams.WRAP_CONTENT)
        popup.setHeight(LayoutParams.WRAP_CONTENT)
        popup.showAtLocation(this, Gravity.CENTER, 0, 0)
    }
    vie.setOnClickListener {
        popup.dismiss()
    }
}

    //funkcija u kojoj obavljamo crtanje
    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)
      /* var keys: List<Keyboard.Key> = getKeyboard().getKeys()
        for (key: Keyboard.Key in keys) {
            if (key.codes.size!=0&&key.codes[0] == 107) {
                Log.e("KEY", "Drawing key with code " + key.codes[0]);
                //crtanje pozadine
                var dr: Drawable = context.getResources().getDrawable(R.drawable.key_bg_gpt);
                dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                if (canvas != null) {
                    dr.draw(canvas)
                }

            } else {
                //crtanje pozadine dugmeeta
                var dr: Drawable = context.getResources().getDrawable(R.drawable.key_bg);
                dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                if (canvas != null) {
                    dr.draw(canvas)
                    val paint = Paint()
                    paint.textAlign = Paint.Align.CENTER
                    paint.textSize = 48f
                    paint.color = Color.GRAY
                    //crtanje slova/ikone dgmeta
                    if (key.label != null) {
                        canvas.drawText(
                            key.label.toString(), (key.x + key.width / 2).toFloat(),
                            (key.y + key.height / 2).toFloat(), paint
                        )
                    } else if(key.icon!=null) {
                        key.icon.setBounds(key.x, key.y, key.x + key.width, key.y + key.height)
                        key.icon.draw(canvas)
                    }
                }
            }

        }

       */



    }
}