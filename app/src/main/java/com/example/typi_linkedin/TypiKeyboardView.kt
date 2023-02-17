package com.example.typi_linkedin

import android.app.ActionBar.LayoutParams
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.internal.wait
import kotlin.math.roundToInt

class TypiKeyboardView(context: Context, attrs: AttributeSet) : KeyboardView(context, attrs) {
//ovaj klas omogucuje cutom crtanje dugmadi kako sva dugmad ne bi morala izgledati isto
    //da bi radio mora se u xml koji se u njega ucitava staviti on kao root tag
companion object{
    var popupWindow:ArrayList<PopupWindow> = ArrayList()
    var xovi:ArrayList<Int> = ArrayList()
    var yoni:ArrayList<Int> = ArrayList()
    lateinit var  context: Context
    lateinit var ovajView:View
    fun capsNot()
    {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView1 = inflater.inflate(R.layout.popup_layout, null)
        val popupText = popupView1.findViewById<TextView>(R.id.popup_text)
        popupText.setText("CapsLock")
        popupText.setTextColor(context.getColor(R.color.white))
        popupText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,35f)
        var popupWindow2=PopupWindow(popupView1,WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        popupWindow2.showAtLocation(ovajView,Gravity.NO_GRAVITY,100,100)
        println("pusten" )

        Handler(Looper.getMainLooper()).postDelayed({
            popupWindow2.dismiss()
        }, 500)

    }
}
    var inputMethod: TypiInputMethodService = TypiInputMethodService()
        fun pripremi()
        {

            popupWindow.clear()
            for(i in 97 until 122)
            {
                var t=keyboard.keys.filter { v->v.codes[0]==i }[0]
                popupWindow.add(PopupWindow(createPopupView(i.toChar().toString()), WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT))
                popupWindow[i-97].isOutsideTouchable=true
                popupWindow[i-97].width=t.width
                xovi.add(t.x)
                yoni.add(t.y-10)
            }
        }

    override fun onLongPress(popupKey: Keyboard.Key?): Boolean
    {
        var kod= popupKey?.codes?.get(0)
        if (kod==32) {
            val imeManager: InputMethodManager =
                context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imeManager.showInputMethodPicker()
            return false
        }

        return super.onLongPress(popupKey)

    }
    fun showPopupWindow(i:Int)
    {
        popupWindow[i].showAtLocation(this, Gravity.NO_GRAVITY,xovi[i],yoni[i])
    }

    // Create a custom view for the popup window
    private fun createPopupView(popupCharacters: String): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_layout, null)
        val popupText = popupView.findViewById<TextView>(R.id.popup_text)
       popupText.setText(popupCharacters)
        popupText.setTextColor(context.getColor(R.color.white))
        popupText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,35f)
        return popupView
    }


    // Dismiss the popup window
     fun dismissPopupWindow(i: Int) {
        // If the popup window is currently displayed, dismiss it
        Handler(Looper.getMainLooper()).postDelayed({
            popupWindow[i].dismiss()
        }, 60)
       // popupWindow.dismiss()
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
        var tt = Button(context)
        tt.background=context.resources.getDrawable(R.drawable.button_border)
        tt.setText(i.loadLabel(context.packageManager))
        tt.textSize= 10.0F
        tt.setTextColor(context.getColor(R.color.white))
        tt.gravity=Gravity.CENTER
        tt.setOnClickListener {
            imeManager.setInputMethod(token, i.id)
        }
        lista.addView(tt)
    }
    val popup = PopupWindow(context)
    popup.contentView = custom
    popup.isOutsideTouchable=true
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

      //  super.onDraw(canvas)
       var keys: List<Keyboard.Key> = getKeyboard().getKeys()
        for (key: Keyboard.Key in keys) {
            if (key.codes.size!=0&&key.codes[0] == 10700) {
              /*  Log.e("KEY", "Drawing key with code " + key.codes[0]);
                //crtanje pozadine
                var dr: Drawable = context.getResources().getDrawable(R.drawable.key_bg_gpt);
                dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                if (canvas != null) {
                    dr.draw(canvas)
                }
*/
            } else {
                //crtanje pozadine dugmeeta
                var horizontalGap:Int= (width*0.008).roundToInt()
                var dr1:Drawable =
                    context.getResources().getDrawable(R.drawable.key_bg)
                dr1.setBounds(key.x+horizontalGap, key.y+10, key.x + key.width-horizontalGap, key.y + key.height);
                if (canvas != null) {
                    dr1.draw(canvas)
                   // dr.draw(canvas)
                    val paint = Paint()
                    paint.textAlign = Paint.Align.CENTER
                    paint.textSize = 48f
                    paint.color = Color.WHITE
                    //crtanje slova/ikone dgmeta
                    if (key.label != null) {
                        canvas.drawText(
                            key.label.toString(), (key.x + key.width / 2).toFloat(),
                            (key.y + key.height / 2+5).toFloat(), paint
                        )
                    } else if(key.icon!=null) {
                        key.icon.setBounds(key.x, key.y, key.x + key.width, key.y + key.height)
                        key.icon.draw(canvas)
                    }
                }
            }

        }


    }
}