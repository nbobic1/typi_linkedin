package com.beta.typi_linkedin

import android.app.ActionBar.LayoutParams
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.AttributeSet
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.mixpanel.android.mpmetrics.MixpanelAPI
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
        popupWindow2.isOutsideTouchable=true
        Handler(Looper.getMainLooper()).postDelayed({
            popupWindow2.dismiss()
        }, 500)

    }
}
    var inputMethod: TypiInputMethodService = TypiInputMethodService()
        fun pripremi()
        {
            popupWindow.clear()
            for(i in 97 until 123)
            {
                var t1=keyboard.keys.filter { v->v.codes.size>0&&v.codes[0]==i }
                if(t1.size==0)
                    break
                var t=t1[0]
                popupWindow.add(PopupWindow(createPopupView(i.toChar().toString()), WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT))
                popupWindow[i-97].isOutsideTouchable=true
                popupWindow[i-97].width=t.width
                xovi.add(t.x)
                yoni.add(t.y-10)
            }
            for(i in 65 until 91)
            {

                var t1=keyboard.keys.filter { v->v.codes.size>0&&v.codes[0]==i }
                if(t1.size==0)
                    break
                var t=t1[0]
                popupWindow.add(PopupWindow(createPopupView(i.toChar().toString()), WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT))
                popupWindow[i-65].isOutsideTouchable=true
                popupWindow[i-65].width=t.width
                xovi.add((t.x*0.9).toInt())
                yoni.add(t.y-10)
            }
        }

    override fun onLongPress(popupKey: Keyboard.Key?): Boolean
    {
        var kod= popupKey?.codes?.get(0)
        if (kod==32) {

            val mixpanel = MixpanelAPI.getInstance(TypiInputMethodService.context, GptApi_Clean.token, true)
            mixpanel.track("TypiKbChange")

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

    fun dismissPopupWindowImedietly(i: Int) {
        // If the popup window is currently displayed, dismiss it
            popupWindow[i].dismiss()
        // popupWindow.dismiss()
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
            val mixpanel = MixpanelAPI.getInstance(TypiInputMethodService.context, GptApi_Clean.token, true)
            mixpanel.track("TypiKbChange")
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
        var horizontalGap:Int=0
        if(Pref_Clean.getIntPref(context,"jezik")==0)
        {
            horizontalGap=(width*resources.getInteger(R.integer.hGap)*3/40000.0f).roundToInt()
        }
        else
            horizontalGap=(width*resources.getInteger(R.integer.hGapBos)/10000.0f).roundToInt()
        var verticalGap:Int= (width*resources.getInteger(R.integer.vGap)/10000.0f).roundToInt()
        var dr1:Drawable = context.getResources().getDrawable(R.drawable.key_bg)
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

                dr1.setBounds(key.x+horizontalGap, key.y+verticalGap, key.x + key.width-horizontalGap, key.y + key.height-verticalGap);
                if (canvas != null) {
                    dr1.draw(canvas)
                   // dr.draw(canvas)
                    val paint = Paint()
                    paint.textAlign = Paint.Align.CENTER
                    if (key.codes.size!=0 && (key.codes[0]==32) || key.codes[0]==-6 || key.codes[0]==-2 || key.codes[0]==-10){
                        paint.textSize=40f
                    }
                    else {
                        paint.textSize = 60f
                    }
                    paint.color = Color.WHITE
                    //crtanje slova/ikone dgmeta
                    if (key.label != null) {
                        canvas.drawText(
                            key.label.toString(), (key.x + key.width / 2).toFloat(),
                            (key.y + key.height / 2+20).toFloat(), paint
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