package com.example.typi_linkedin

import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.view.inputmethod.InputConnection
import android.widget.LinearLayout
import android.widget.TextView

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
    }
}