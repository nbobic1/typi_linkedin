package com.example.typi_linkedin

import android.app.ActionBar
import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputConnection
import android.widget.*

class ViewMaker
{
    companion object{
        enum class Category {
            SMILY, FOOD, CARS, NATURE
        }
        fun categorySetup()
        {

        }
                fun emoji(keyboardRoot: View, context: Context, onKey: (primaryCode: Int, keyCodes: IntArray) -> Unit ,categoriy:Category)
                {
                    var kbAgain=keyboardRoot.findViewById<Button>(R.id.type)
                    kbAgain.setOnClickListener{
                        showKeyboard(keyboardRoot)
                    }
                    var keyboardView=keyboardRoot.findViewById<TypiKeyboardView>(R.id.keyboard_view)
                    keyboardView.visibility=View.GONE
                    var linearLayoutEmoji=keyboardRoot.findViewById<LinearLayout>(R.id.emoji)
                    linearLayoutEmoji.visibility=View.VISIBLE
                    var linearLayoutEmojiCategory=keyboardRoot.findViewById<LinearLayout>(R.id.emoij_categoires)
                    linearLayoutEmojiCategory.visibility=View.VISIBLE
                    var linearLayout=keyboardRoot.findViewById<LinearLayout>(R.id.clipboard)
                    linearLayout.visibility=View.GONE
                    
                    for (i in 127744 until 128000 step 10)
                    {
                        var lp= LinearLayout(context)
                        lp.orientation= LinearLayout.HORIZONTAL
                        for(j in i until i+10)
                        {
                            var too= TextView(context)
                            val charArray = Character.toChars(j)
                            val surrogatePair = String(charArray)
                            too.setText(surrogatePair)
                            too.setTextSize(30F)
                            too.setOnClickListener {
                                onKey(j, intArrayOf(j))
                            }
                            lp.addView(too)
                        }
                        linearLayoutEmoji.addView(lp)
                    }
                    //   emji.visibility=View.GONE
                    // emji.visibility=View.INVISIBLE
                }
        fun clipBoard(keyboardRoot: View, context: Context, ic: InputConnection)
        {

            var keyboardView=keyboardRoot.findViewById<TypiKeyboardView>(R.id.keyboard_view)
            keyboardView.visibility=View.GONE
            var linearLayoutEmoji=keyboardRoot.findViewById<LinearLayout>(R.id.emoji)
            linearLayoutEmoji.visibility=View.GONE
            var linearLayoutEmojiCategory=keyboardRoot.findViewById<LinearLayout>(R.id.emoij_categoires)
            linearLayoutEmojiCategory.visibility=View.GONE
            var linearLayout=keyboardRoot.findViewById<LinearLayout>(R.id.clipboard)
            linearLayout.visibility=View.VISIBLE
            val clipboard: ClipboardManager =context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            for(i in 0 until (clipboard.primaryClip?.itemCount ?: -1))
            {
                var t=TextView(context)
                t.setText(clipboard.primaryClip?.getItemAt(i)?.text ?: "")
                t.setOnClickListener { 
                    ic.commitText(t.text.toString(),t.text.length)
                }
                linearLayout.addView(t)
            }
        }
        fun showKeyboard(keyboardRoot: View)
        {
            var keyboardView=keyboardRoot.findViewById<TypiKeyboardView>(R.id.keyboard_view)
            keyboardView.visibility=View.VISIBLE
            var linearLayoutEmoji=keyboardRoot.findViewById<LinearLayout>(R.id.emoji)
            linearLayoutEmoji.visibility=View.GONE
            var linearLayoutEmojiCategory=keyboardRoot.findViewById<LinearLayout>(R.id.emoij_categoires)
            linearLayoutEmojiCategory.visibility=View.GONE
            var linearLayout=keyboardRoot.findViewById<LinearLayout>(R.id.clipboard)
            linearLayout.visibility=View.GONE
        }

        fun returnInput(context:Context, root:View,onKey: (primaryCode: Int, keyCodes: IntArray) -> Unit )
        {
            var tk= FrameLayout(context)
            tk.layoutParams= ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val custom: View = LayoutInflater.from(context)
                .inflate(R.layout.popup,tk)
            var vie=custom.findViewById<TextView>(R.id.textView3)

            var lista=custom.findViewById<LinearLayout>(R.id.ll)



            var tt = TextView(context)
            tt.setText("sad")
            tt.textSize= 20.0F
            tt.setOnClickListener {

            }
            lista.addView(tt)
            var tt1 = TextView(context)
            tt1.setText("funny")
            tt1.textSize= 20.0F
            tt1.setOnClickListener {

            }
            lista.addView(tt1)
            var tt2 = TextView(context)
            tt2.setText("proffesional")
            tt2.textSize= 20.0F
            tt2.setOnClickListener {

            }
            lista.addView(tt2)

            val popup = PopupWindow(context)
            popup.contentView = custom
            popup.isOutsideTouchable=true
            if(popup.isShowing()){
                popup.update(200, 200, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
            } else {
                popup.setWidth(ActionBar.LayoutParams.WRAP_CONTENT)
                popup.setHeight(ActionBar.LayoutParams.WRAP_CONTENT)
                popup.showAtLocation(root, Gravity.CENTER, 0, 0)
            }
            vie.setOnClickListener {
                popup.dismiss()
            }
        }
    }
}