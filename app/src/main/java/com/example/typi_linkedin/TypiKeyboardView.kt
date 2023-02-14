package com.example.typi_linkedin

import android.app.ActionBar.LayoutParams
import android.content.Context
import android.graphics.Canvas
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.IBinder
import android.util.AttributeSet
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
        }*/
        return super.onLongPress(popupKey)
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
                var dr: Drawable = context.getResources().getDrawable(R.drawable.key_bg);
                dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                if (canvas != null) {
                    dr.draw(canvas)
                }

            } else {
                //crtanje pozadine dugmeeta
                var dr: Drawable = context.getResources().getDrawable(R.drawable.key_bg_gpt);
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