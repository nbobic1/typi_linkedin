package com.beta.typi_linkedin

import android.app.ActionBar
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.setPadding

class ViewMaker
{
    companion object{
        var paste= mutableListOf<String>()
        enum class Category {
            SMILY, FOOD, CARS, NATURE
        }
        fun     optionsSetup(keyboardRoot: View,context:Context,onKey: (primaryCode: Int, keyCodes: IntArray) -> Unit)
        {
            keyboardRoot.findViewById<Button>(R.id.help).setOnClickListener {
                var tk= FrameLayout(context)
                tk.layoutParams= ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                val custom: View = LayoutInflater.from(context)
                    .inflate(R.layout.popup_help,tk)
                val popup = PopupWindow(context)
                popup.contentView = custom
                popup.isOutsideTouchable=true
                if(popup.isShowing()){
                    popup.update(200, 200, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
                } else {
                    popup.setWidth(ActionBar.LayoutParams.WRAP_CONTENT)
                    popup.setHeight(ActionBar.LayoutParams.WRAP_CONTENT)
                    popup.showAtLocation(keyboardRoot, Gravity.CENTER, 0, 0)
                }
            }
            keyboardRoot.findViewById<Button>(R.id.answer).setOnClickListener {
                onKey(context.resources.getInteger(R.integer.gpt), intArrayOf(-1))
            }
            keyboardRoot.findViewById<Button>(R.id.rephrase).setOnClickListener {
                onKey(context.resources.getInteger(R.integer.rephrase), intArrayOf(-1))
            }
            keyboardRoot.findViewById<Button>(R.id.reverse).setOnClickListener {
                onKey(context.resources.getInteger(R.integer.gptBack), intArrayOf(-1))
            }
            keyboardRoot.findViewById<Button>(R.id.clip).setOnClickListener {
                onKey(context.resources.getInteger(R.integer.clip), intArrayOf(-1))
            }
            keyboardRoot.findViewById<Button>(R.id.translate).setOnClickListener {
                onKey(context.resources.getInteger(R.integer.translate), intArrayOf(-1))
            }
            keyboardRoot.findViewById<Button>(R.id.summerize).setOnClickListener {
                onKey(context.resources.getInteger(R.integer.summerize), intArrayOf(-1))
            }
            keyboardRoot.findViewById<Button>(R.id.changeKb).setOnClickListener {
                onKey(context.resources.getInteger(com.beta.typi_linkedin.R.integer.lanCustom),
                    kotlin.intArrayOf(-1)
                )
            }
            keyboardRoot.findViewById<Button>(R.id.grammar).setOnClickListener {
                onKey(context.resources.getInteger(com.beta.typi_linkedin.R.integer.grammar),
                    kotlin.intArrayOf(-1)
                )
            }
            keyboardRoot.findViewById<Button>(R.id.chat).setOnClickListener {

                var tk= FrameLayout(context)
                tk.layoutParams= ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                val custom: View = LayoutInflater.from(context).inflate(R.layout.chat_layout,tk)
                val popup = PopupWindow(context)

                var oldInputConnection=TypiInputMethodService.inputConnection
                tk.findViewById<Button>(R.id.zatvori_popup).setOnClickListener {
                   TypiInputMethodService.inputConnection=oldInputConnection
                    popup.dismiss()
                }
                tk.findViewById<Button>(R.id.send).setOnClickListener {
                    onKey(context.resources.getInteger(R.integer.chat), intArrayOf(-1))
                   }
                var editText=tk.findViewById<EditText>(R.id.chatInput1)
                TypiInputMethodService.inputConnection=editText.onCreateInputConnection(EditorInfo())
                popup.contentView = custom
                popup.showAtLocation(keyboardRoot, Gravity.CENTER, 0, -1150)
                TypiInputMethodService.output=tk.findViewById<TextView>(R.id.chatOutput)
            }
        }
        fun categorySetup(keyboardRoot: View,context:Context,onKey: (primaryCode: Int, keyCodes: IntArray) -> Unit):View
        {
            var linearLayoutEmoji=keyboardRoot.findViewById<LinearLayout>(R.id.emoji)
          //DODAVANJE HRANE-----------------------------------------------------------------------------
            var llFood=LinearLayout(context)
            llFood.orientation=LinearLayout.VERTICAL
            var listaFood= arrayListOf<Int>(127823, 127822, 127824, 127818, 127819, 127820, 127817, 127815, 127827, 129744,
                127816, 127826, 127825, 129389, 127821, 129381, 129373, 127813, 127814, 129361,
                129382, 129388, 129362, 127798, 65039, 129745, 127805, 129365, 129746, 129476,
                129477, 129364, 127840, 129360, 129391, 127838, 129366, 129384, 129472, 129370,
                127859, 129480, 129374, 129479, 129363, 129385, 127831, 127830, 129460, 127789,
                127828, 127839, 127829, 129747, 129386, 129369, 129478, 127790, 127791, 129748,
                129367, 129368, 129749, 129387, 129753, 127837, 127836, 127858, 127835, 127843,
                127857, 129375, 129450, 127844, 127833, 127834, 127832, 127845, 129376, 129390,
                127842, 127841, 127847, 127848, 127846, 129383, 129473, 127856, 127874, 127854,
                127853, 127852, 127851, 127871, 127849, 127850, 127792, 129372, 129752, 127855,
                129371, 129751, 127868, 129750, 9749, 65039, 127861, 129475, 129380, 129483,
                127862, 127866, 127867, 129346, 127863, 129347, 127864, 127865, 129481, 127870,
                129482, 129348, 127860, 127869, 65039, 129379, 129377, 129378, 129474)
            makeEmojiList(context,listaFood,onKey,llFood)
            linearLayoutEmoji.addView(llFood)
            //ADDING ANIMALS
            var listaAnimals= arrayListOf<Int>(128054,  128049,  128045,  128057,  128048,  129418,  128059,  128060,
                128059,  8205,  10052,  65039,  128040,
                128047,  129409,  128046,  128055,  128061,  128056,  128053,  128584,  128585,  128586,  128018,  128020,
                128039,  128038,  128036,  128035,  128037,  129414,  129413,  129417,  129415,  128058,  128023,  128052,  129412,  128029,  129713,  128027,  129419,  128012,  128030,
                128028,  129712,  129714,  129715,  129439,  129431,  128375,  65039,  128376,  65039,  129410,
                128034,  128013,  129422,  129430,
                129429,  128025,  129425,  129424,  129438,  129408,  128033,  128032,  128031,  128044,  128051,  128011,  129416,  129453,  128010,
                128005,  128006,  129427,  129421,  129447,  129443,  128024,  129435,  129423,  128042,  128043,  129426,  129432,  129452,  128003,  128002,  128004,  128014,  128022,  128015,  128017,  129433,  128016,  129420,
                128021,  128041,  129454,  128021,  8205,  129466,  128008,  128008,  8205,  11035,  129718,  128019,  129411,  129444,  129434,  129436,  129442,  129449,  128330,
                65039,  128007,  129437,  129448,  129441,  129451,  129446,  129445,  128001,  128000,
                128063,  65039,  129428,  128062,  128009,  128050,
                127797,  127876,  127794,  127795,  127796,  129717,  127793,  127807,  9752,  65039,  127808,  127885,  129716,  127883,  127811,  127810,
                127809,  129722,  129721,  127812,  128026,  129720,
                129704,  127806,  128144,  127799,  127801,  129344,  129719,  127802,  127800,
                127804,  127803,  127774,  127773,  127771,  127772,  127770,
                127765,  127766,  127767,  127768,  127761,  127762,  127763,  127764,  127769,  127758,  127757,  127759,  129680,
                128171,  11088,  65039,  127775,  10024,  9889,  65039,  9732,  65039,  128165,  128293,  127786,  65039,  127752,  9728,  65039,  127780,
                65039,  9925,  65039,  127781,  65039,  9729,  65039,  127782,  65039,  127783,  65039,  9928,  65039,  127785,  65039,  127784,  65039,  10052,  65039,  9731,  65039,  9924,  65039,  127788,  65039,  128168,  128167,  128166,  129767,  9748,  65039,  9730,
                65039,  127754,  127787 )
            var llAnimals=LinearLayout(context)
            llAnimals.orientation=LinearLayout.VERTICAL
            makeEmojiList(context,listaAnimals,onKey,llAnimals)
            linearLayoutEmoji.addView(llAnimals)
            //ADDING SPORTs
            var listaSports= arrayListOf<Int>( 9917,  65039,  127936,  127944,  9918,  65039,  129358,  127934,  127952,  127945,
                129359,  127921,  129664,  127955,  127992,  127954,  127953,  129357,  127951,  129667,  129349,
                9971,  65039,  129665,  128733,  127993,  127907,  129343,  129354,  129355,  127933,  128761,
                128764,  128759,  9976,  65039,  129356,  127935,  9975,  65039,  127942,  129351,  129352,
                129353,  127941,  127894,  65039,  127989,  65039,  127895,  65039,  127915,  127903,  65039,
                127914,  129337,  8205,  9792,  65039,  129337,  129337,  8205,  9794,  65039,  127917,  129648,
                127912,  127916,  127908,  127911,  127932,  127929,  129345,  129688,  127927,  127930,
                129687,  127928,  129685,  127931,  127922,  9823,  65039,  127919,  127923,  127918,  127920,
                129513)
            var llSport=LinearLayout(context)
            llSport.orientation=LinearLayout.VERTICAL
            makeEmojiList(context,listaSports,onKey,llSport)
            linearLayoutEmoji.addView(llSport)
            //ADDING OBJECts
            var listaObjects= arrayListOf<Int>(8986, 65039, 128241, 128242, 128187, 9000, 65039, 128421, 65039, 128424,
                65039, 128433, 65039, 128434, 65039, 128377, 65039, 128476, 65039, 128189,
                128190, 128191, 128192, 128252, 128247, 128248, 128249, 127909, 128253, 65039,
                127902, 65039, 128222, 9742, 65039, 128223, 128224, 128250, 128251, 127897,
                65039, 127898, 65039, 127899, 65039, 129517, 9201, 65039, 9202, 65039,
                9200, 128368, 65039, 8987, 65039, 9203, 128225, 128267, 129707, 128268,
                128161, 128294, 128367, 65039, 129684, 129519, 128738, 65039, 128184, 128181,
                128180, 128182, 128183, 129689, 128176, 128179, 129706, 128142, 9878, 65039,
                129692, 129520, 129691, 128295, 128296, 9874, 65039, 128736, 65039, 9935,
                65039, 129690, 128297, 9881, 65039, 129700, 129521, 9939, 65039, 129522,
                128299, 128163, 129512, 129683, 128298, 128481, 65039, 9876, 65039, 128737,
                65039, 128684, 9904, 65039, 129702, 9905, 65039, 127994, 128302, 128255,
                129535, 129708, 128136, 9879, 65039, 128301, 128300, 128371, 65039, 129659,
                129657, 129658, 128138, 128137, 129656, 129516, 129440, 129515, 129514, 127777,
                65039, 129529, 129696, 129530, 129531, 128701, 128688, 128703, 128705, 129532,
                129701, 129682, 129533, 129699, 129524, 128718, 65039, 128273, 128477, 65039,
                128682, 129681, 128715, 65039, 128719, 65039, 128716, 129528, 129670, 128444,
                65039, 129694, 129695, 128717, 65039, 128722, 127873, 127880, 127887, 127872,
                129668, 129669, 127882, 127881, 127886, 127982, 127888, 129705, 129511, 9993,
                65039, 128233, 128232, 128231, 128140, 128229, 128228, 128230, 127991, 65039,
                129703, 128234, 128235, 128236, 128237, 128238, 128239, 128220, 128195, 128196,
                128209, 129534, 128202, 128200, 128201, 128466, 65039, 128467, 65039, 128198,
                128197, 128465, 65039, 128199, 128451, 65039, 128499, 65039, 128452, 65039,
                128203, 128193, 128194, 128450, 65039, 128478, 65039, 128240, 128211, 128212,
                128210, 128213, 128215, 128216, 128217, 128218, 128214, 128278, 129527, 128279,
                128206, 128391, 65039, 128208, 128207, 129518, 128204, 128205, 9986, 65039,
                128394, 65039, 128395, 65039, 10002, 65039, 128396, 65039, 128397, 65039,
                128221, 9999, 65039, 128269, 128270, 128271, 128272, 128274, 128275)
            var llObjects=LinearLayout(context)
            llObjects.orientation=LinearLayout.VERTICAL
            makeEmojiList(context,listaObjects,onKey,llObjects)
            linearLayoutEmoji.addView(llObjects)
            //ADDING TURIST
            var listaTurism= arrayListOf<Int>(128663,  128661,  128665,  128652,  128654,  127950,  65039,  128659,  128657,  128658,
                128656,  128763,  128666,  128667,  128668,  129455,  129469,  129468,  129660,  128756,
                128690,  128757,  127949,  65039,  128762,  128734,  128680,  128660,  128653,  128664,
                128662,  128673,  128672,  128671,  128643,  128651,  128670,  128669,  128644,  128645,
                128648,  128642,  128646,  128647,  128650,  128649,  9992,  65039,  128747,  128748,
                128745,  65039,  128186,  128752,  65039,  128640,  128760,  128641,  128758,  9973,
                65039,  128676,  128741,  65039,  128755,  65039,  9972,  65039,  128674,  128735,
                9875,  65039,  129693,  9981,  65039,  128679,  128678,  128677,  128655,  128506,
                65039,  128511,  128509,  128508,  127984,  127983,  127967,  65039,  127905,  127906,
                127904,  9970,  65039,  9969,  65039,  127958,  65039,  127965,  65039,  127964,
                65039,  127755,  9968,  65039,  127956,  65039,  128507,  127957,  65039,  9978,
                65039,  128726,  127968,  127969,  127960,  65039,  127962,  65039,  127959,  65039,
                127981,  127970,  127980,  127971,  127972,  127973,  127974,  127976,  127978,  127979,
                127977,  128146,  127963,  65039,  9962,  65039,  128332,  128333,  128725,  128331,
                9961,  65039,  128740,  65039,  128739,  65039,  128510,  127889,  127966,  65039,
                127749,  127748,  127776,  127879,  127878,  127751,  127750,  127961,  65039,  127747,
                127756,  127753,  127745)
            var llTourism=LinearLayout(context)
            llTourism.orientation=LinearLayout.VERTICAL
            makeEmojiList(context,listaTurism,onKey,llTourism)
            linearLayoutEmoji.addView(llTourism)
           //ADDING SYMBOLS
            var listaSimbols= arrayListOf<Int>(10084, 65039, 129505, 128155, 128154, 128153, 128156, 128420, 129293, 129294,
                128148, 10084, 65039, 8205, 128293, 10084, 65039, 8205, 129657, 10083,
                65039, 128149, 128158, 128147, 128151, 128150, 128152, 128157, 128159, 9774,
                65039, 10013, 65039, 9770, 65039, 128329, 65039, 9784, 65039, 10017,
                65039, 128303, 128334, 9775, 65039, 9766, 65039, 128720, 9934, 9800,
                65039, 9801, 65039, 9802, 65039, 9803, 65039, 9804, 65039, 9805,
                65039, 9806, 65039, 9807, 65039, 9808, 65039, 9809, 65039, 9810,
                65039, 9811, 65039, 127380, 9883, 65039, 127569, 9762, 65039, 9763,
                65039, 128244, 128243, 127542, 127514, 65039, 127544, 127546, 127543, 65039,
                10036, 65039, 127386, 128174, 127568, 12953, 65039, 12951, 65039, 127540,
                127541, 127545, 127538, 127344, 65039, 127345, 65039, 127374, 127377, 127358,
                65039, 127384, 10060, 11093, 65039, 128721, 9940, 65039, 128219, 128683,
                128175, 128162, 9832, 65039, 128695, 128687, 128691, 128689, 128286, 128245,
                128685, 10071, 65039, 10069, 10067, 10068, 8252, 65039, 8265, 65039,
                128261, 128262, 12349, 65039, 9888, 65039, 128696, 128305, 9884, 65039,
                128304, 9851, 65039, 9989, 127535, 65039, 128185, 10055, 65039, 10035,
                65039, 10062, 127760, 128160, 9410, 65039, 127744, 128164, 127975, 128702,
                9855, 65039, 127359, 65039, 128727, 127539, 127490, 65039, 128706, 128707,
                128708, 128709, 128697, 128698, 128700, 9895, 65039, 128699, 128686, 127910,
                128246, 127489, 128291, 8505, 65039, 128292, 128289, 128288, 127382, 127383,
                127385, 127378, 127381, 127379, 9654, 65039, 9208, 65039, 9199, 65039,
                9209, 65039, 9210, 65039, 9197, 65039, 9198, 65039, 9193, 65039,
                9194, 65039, 9195, 65039, 9196, 65039, 9664, 65039, 128316, 128317,
                10145, 65039, 11013, 65039, 11014, 65039, 11015, 65039, 8599, 65039,
                8600, 65039, 8601, 65039, 8598, 65039, 8597, 65039, 8596, 65039,
                8618, 65039, 8617, 65039, 10548, 65039, 10549, 65039, 128256, 128257,
                128258, 128260, 128259, 128261, 128262, 12349, 65039, 9888, 65039, 128696,
                128305, 9884, 65039, 128304, 9851, 65039, 9989, 127535, 65039, 128185,
                10055, 65039, 10035, 65039, 10062, 127760, 128160, 9410, 65039, 127744,
                128164, 127975, 128702, 9855, 65039, 127359, 65039, 128727, 127539, 127490,
                65039, 128706, 128707, 128708, 128709, 128697, 128698, 128700, 9895, 65039,
                128699, 128686, 127910, 128246, 127489, 128291, 8505, 65039, 128292, 128289,
                128288, 127382, 127383, 127385, 127378, 127381, 127379, 9167, 65039, 9654,
                65039, 9208, 65039, 9199, 65039, 9209, 65039, 9210, 65039, 9197,
                65039, 9198, 65039, 9193, 65039, 9194, 65039, 9195, 65039, 9196,
                65039, 9664, 65039, 128316, 128317, 10145, 65039, 11013, 65039, 11014,
                65039, 11015, 65039, 8599, 65039, 8600, 65039, 8601, 65039, 8598,
                65039, 8597, 65039, 8596, 65039, 8618, 65039, 8617, 65039, 10548,
                65039, 10549, 65039, 128256, 128257, 128258, 128260, 128259)

            var llSymbols=LinearLayout(context)
            llSymbols.orientation=LinearLayout.VERTICAL
            makeEmojiList(context,listaSimbols,onKey,llSymbols)
            linearLayoutEmoji.addView(llSymbols)
            //dinin string u int
            /*
            var t=0;
            for(i in listaHrana.codePoints()){
               t++;
                println(i.toInt().toString()+", ")
                if(t%10==0)
                    println(" ")
            }
            */

           //DODAVANJE smajlija----------------------------------------------------------------
            var llSmily=LinearLayout(context)
            llSmily.orientation=LinearLayout.VERTICAL

            var listaSmily= arrayListOf<Int>(128512, 128515, 128516, 128513, 128518, 129401, 128517, 128514, 129315, 129394,
                9786, 65039, 128522, 128519, 128578, 128579, 128521, 128524, 128525, 129392,
                128536, 128535, 128537, 128538, 128523, 128539, 128541, 128540, 129322, 129320,
                129488, 129299, 128526, 129400, 129321, 129395, 128527, 128530, 128542, 128532,
                128543, 128533, 128577, 9785, 65039, 128547, 128534, 128555, 128553, 129402, 128546,
                128557, 128548, 128544, 128545, 129324, 129327, 128563, 129397, 129398, 128566, 8205,
                127787, 65039, 128561, 128552, 128560, 128549, 128531, 129303, 129300, 129763, 129325,
                129762, 129761, 129323, 129760, 129317, 128566, 128528, 129764, 128529, 128556, 128580,
                128559, 128550, 128551, 128558, 128562, 129393, 128564, 129316, 128554, 128558, 8205, 128168,
                128565, 128565, 8205, 128171, 129296, 129396, 129314, 129326, 129319, 128567, 129298, 129301,
                129297, 129312, 128520, 128127, 128121, 128122, 129313, 128169, 128123, 128128, 9760, 65039,
                128125, 128126, 129302, 127875, 128570, 128568, 128569, 128571, 128572, 128573, 128576, 128575,
                128574, 129782, 129330, 128080, 128588, 128079, 129309, 128077, 128078, 128074, 9994, 129307,
                129308, 129310, 9996, 65039, 129776, 129311, 129304, 128076, 129292, 129295, 129779, 129780,
                128072, 128073, 128070, 128071, 9757, 65039, 9995, 129306, 128400, 65039, 128406, 128075, 9995,
                129778, 129305, 129777, 128170, 129470, 128405, 9997, 65039, 128591, 129781, 129462, 129461, 129471,
                128132, 128139, 128068, 129766, 129463, 128069, 128066, 129467, 128067, 128099, 128065, 65039, 128064,
                129728, 129729, 129504, 128483, 65039, 128100, 128101, 129730, 128118, 128103, 129490, 128102, 128105,
                129489, 128104, 128105, 8205, 55358, 129526, 129525, 129697, 129509, 129404, 129466, 128090, 128085, 128086,
                129650, 129651, 128084, 128087, 128089, 129649, 128088, 129403, 129652, 129407, 128096, 128097, 128098, 128094,
                128095, 129406, 129510, 129508, 129507, 127913, 129506, 128082, 127891, 9937, 65039, 129686, 128081, 128141,
                128093, 128091, 128092, 128188, 127890, 129523, 128083, 128374, 65039, 129405, 127746)
            makeEmojiList(context,listaSmily,onKey,llSmily)
            linearLayoutEmoji.addView(llSmily)
            var foodBtn=keyboardRoot.findViewById<Button>(R.id.food)
            foodBtn.setOnClickListener {

                llSmily.visibility=View.GONE
                llTourism.visibility=View.GONE
                llSport.visibility=View.GONE
                llObjects.visibility=View.GONE
                llSymbols.visibility=View.GONE
                llAnimals.visibility=View.GONE
                llFood.visibility=View.VISIBLE
            }

            var smilyBtn=keyboardRoot.findViewById<Button>(R.id.smily)
            smilyBtn.setOnClickListener {
                llFood.visibility=View.GONE
                llTourism.visibility=View.GONE
                llAnimals.visibility=View.GONE
                llSymbols.visibility=View.GONE
                llSport.visibility=View.GONE
                llObjects.visibility=View.GONE
                llSmily.visibility=View.VISIBLE
            }

            var animalsBtn=keyboardRoot.findViewById<Button>(R.id.animals)
            animalsBtn.setOnClickListener {
                llFood.visibility=View.GONE
                llSymbols.visibility=View.GONE
                llSmily.visibility=View.GONE
                llTourism.visibility=View.GONE
                llSport.visibility=View.GONE
                llObjects.visibility=View.GONE
                llAnimals.visibility=View.VISIBLE
            }
            var objectsBtn=keyboardRoot.findViewById<Button>(R.id.objects)
            objectsBtn.setOnClickListener {
                llFood.visibility=View.GONE
                llSmily.visibility=View.GONE
                llSymbols.visibility=View.GONE
                llSport.visibility=View.GONE
                llTourism.visibility=View.GONE
                llAnimals.visibility=View.GONE
                llObjects.visibility=View.VISIBLE
            }

            var sportBtn=keyboardRoot.findViewById<Button>(R.id.sport)
            sportBtn.setOnClickListener {
                llFood.visibility=View.GONE
                llSmily.visibility=View.GONE
                llAnimals.visibility=View.GONE
                llObjects.visibility=View.GONE
                llTourism.visibility=View.GONE
                llSymbols.visibility=View.GONE
                llSport.visibility=View.VISIBLE
            }
            var urbanBtn=keyboardRoot.findViewById<Button>(R.id.urban)
            urbanBtn.setOnClickListener {
                llFood.visibility=View.GONE
                llSmily.visibility=View.GONE
                llAnimals.visibility=View.GONE
                llObjects.visibility=View.GONE
                llSymbols.visibility=View.GONE
                llSport.visibility=View.GONE
                llTourism.visibility=View.VISIBLE
            }
            var simbolsBtn=keyboardRoot.findViewById<Button>(R.id.symbols)
            simbolsBtn.setOnClickListener {
                llFood.visibility=View.GONE
                llSmily.visibility=View.GONE
                llAnimals.visibility=View.GONE
                llObjects.visibility=View.GONE
                llSport.visibility=View.GONE
                llTourism.visibility=View.GONE
                llSymbols.visibility=View.VISIBLE
            }
            return llSmily
        }
        fun makeEmojiList(context: Context,listaSmily:ArrayList<Int>,onKey: (primaryCode: Int, keyCodes: IntArray) -> Unit,llSmily:LinearLayout)
        {

            for(i in 0 until listaSmily.size step 10)
            {
                var lp= LinearLayout(context)
                lp.orientation= LinearLayout.HORIZONTAL
                for (j in i until i + 10)
                {
                    if(j==listaSmily.size)
                        break;
                    var too = TextView(context)
                    val charArray = Character.toChars(listaSmily[j])
                    val surrogatePair = String(charArray)
                    too.text = surrogatePair
                    val params =
                        LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f)
                    too.layoutParams = params
                    too.setTextSize(30F)
                    too.setOnClickListener {
                        onKey(listaSmily[j], intArrayOf(listaSmily[j]))
                    }
                    lp.addView(too)
                }
                llSmily.addView(lp)
            }
            llSmily.visibility=View.GONE
        }
        fun allViewSetup(keyboardRoot: View,context: Context, onKey: (primaryCode: Int, keyCodes: IntArray) -> Unit )
        {

            var scroll=keyboardRoot.findViewById<ScrollView>(R.id.topHScrollView)
            scroll.visibility=View.GONE
            var linearLayoutEmoji=keyboardRoot.findViewById<LinearLayout>(R.id.emoji)
            linearLayoutEmoji.visibility=View.GONE
            var linearLayoutEmojiCategory=keyboardRoot.findViewById<LinearLayout>(R.id.emoij_categoires)
            linearLayoutEmojiCategory.visibility=View.GONE
            var linearLayoutClipboard=keyboardRoot.findViewById<LinearLayout>(R.id.clipboard)
            linearLayoutClipboard.visibility=View.GONE
//treba dodati sve emoji u vise linear layout i to u ovaj linear laoyut, pa mjenajti kategorije sa View gone i visible
         /*   for (i in 127744 until 128000 step 10)
            {
                var lp= LinearLayout(context)
                lp.orientation= LinearLayout.HORIZONTAL
                for(j in i until i+10)
                {
                    var too= TextView(context)
                    val charArray = Character.toChars(j)
                    val surrogatePair = String(charArray)
                    too.setText(surrogatePair)
                    val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f)
                    too.layoutParams = params
                    too.setTextSize(30F)
                    too.setOnClickListener {
                        onKey(j, intArrayOf(j))
                    }
                    lp.addView(too)
                }
                linearLayoutEmoji.addView(lp)
            }
*/
        }
                fun emoji(keyboardRoot: View, context: Context, onKey: (primaryCode: Int, keyCodes: IntArray) -> Unit ,view:View)
                {
                    view.visibility=View.VISIBLE
                    var kbAgain=keyboardRoot.findViewById<Button>(R.id.type)
                    kbAgain.setOnClickListener{
                        showKeyboard(keyboardRoot)
                    }
                    var scroll=keyboardRoot.findViewById<ScrollView>(R.id.topHScrollView)
                    scroll.visibility=View.VISIBLE
                    var keyboardView=keyboardRoot.findViewById<TypiKeyboardView>(R.id.keyboard_view)
                    keyboardView.visibility=View.GONE
                    var linearLayoutEmoji=keyboardRoot.findViewById<LinearLayout>(R.id.emoji)
                    linearLayoutEmoji.visibility=View.VISIBLE
                    var linearLayoutEmojiCategory=keyboardRoot.findViewById<LinearLayout>(R.id.emoij_categoires)
                    linearLayoutEmojiCategory.visibility=View.VISIBLE
                    var linearLayout=keyboardRoot.findViewById<LinearLayout>(R.id.clipboard)
                    linearLayout.visibility=View.GONE

                    //   treba priakzivati samo 1 kategoirj, odraditi
                }
        fun clipBoard(keyboardRoot: View, context: Context, ic: InputConnection)
        {
            var scroll=keyboardRoot.findViewById<ScrollView>(R.id.topHScrollView)
            scroll.visibility=View.VISIBLE
            var keyboardView=keyboardRoot.findViewById<TypiKeyboardView>(R.id.keyboard_view)
            keyboardView.visibility=View.GONE
            var linearLayoutEmoji=keyboardRoot.findViewById<LinearLayout>(R.id.emoji)
            linearLayoutEmoji.visibility=View.GONE
            var linearLayoutEmojiCategory=keyboardRoot.findViewById<LinearLayout>(R.id.emoij_categoires)
            linearLayoutEmojiCategory.visibility=View.GONE
            var linearLayout=keyboardRoot.findViewById<LinearLayout>(R.id.clipboard)
            linearLayout.visibility=View.VISIBLE
            val clipboard: ClipboardManager =context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            linearLayout.removeAllViews()
            var k=Button(context)
            k.setText("Back to keyboard")
            k.background=context.resources.getDrawable(R.drawable.key_bg)
            k.setTextColor(context.getColor(R.color.white))
            k.setOnClickListener {
                showKeyboard(keyboardRoot)
            }
            linearLayout.addView(k)
            /*if(clipboard.primaryClip?.itemCount ?: 0==0)
            {
                linearLayout.removeAllViews()
                linearLayout.addView(k)
            }*/
            if(clipboard.primaryClip?.getItemAt(0)?.text ?: ""!="")
            paste.add(clipboard.primaryClip?.getItemAt(0)?.text.toString() ?: "")
            if(paste.size>5)
                paste.removeAt(0)
            for(i in 0 until paste.size)
            {
                var t=TextView(context)
                t.setText(paste[i])
                t.textSize=30f
                t.setPadding(10)
                t.gravity=Gravity.CENTER_HORIZONTAL
                t.setOnClickListener { 
                    ic.commitText(t.text.toString(),t.text.length)
                }
                linearLayout.addView(t)
            }
        }
        fun showKeyboard(keyboardRoot: View)
        { var scroll=keyboardRoot.findViewById<ScrollView>(R.id.topHScrollView)
            scroll.visibility=View.GONE
            var keyboardView=keyboardRoot.findViewById<TypiKeyboardView>(R.id.keyboard_view)
            keyboardView.visibility=View.VISIBLE
            var linearLayoutEmoji=keyboardRoot.findViewById<LinearLayout>(R.id.emoji)
            linearLayoutEmoji.visibility=View.GONE
            var linearLayoutEmojiCategory=keyboardRoot.findViewById<LinearLayout>(R.id.emoij_categoires)
            linearLayoutEmojiCategory.visibility=View.GONE
            var linearLayout=keyboardRoot.findViewById<LinearLayout>(R.id.clipboard)
            linearLayout.visibility=View.GONE
        }

        fun popupInput(context:Context, root:View,onKey: (primaryCode: Int, keyCodes: IntArray) -> Unit ,options:Array<String>,ic:InputConnection,keyCodes: IntArray,order:String)
        {
            var tk= FrameLayout(context)
            tk.layoutParams= ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val custom: View = LayoutInflater.from(context)
                .inflate(R.layout.popup,tk)
            var vie=custom.findViewById<TextView>(R.id.textView3)
            var lista=custom.findViewById<LinearLayout>(R.id.ll)
            val popup = PopupWindow(context)
            for(i in options)
            {
                var tt = Button(context)
                tt.setText(i)
                tt.textSize= 10.0F
                tt.background=context.resources.getDrawable(R.drawable.button_border)
                tt.gravity=Gravity.CENTER
                tt.setTextColor(context.getColor(R.color.white))
                tt.setOnClickListener {
                    TypiInputMethodService.callGptForInput(keyCodes,ic,order+" "+i)
                    popup.dismiss()
                }
                lista.addView(tt)
            }

            popup.contentView = custom
            popup.isOutsideTouchable=true
            if(popup.isShowing()){
                popup.update(200, 200, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
            } else {
                popup.setWidth(ActionBar.LayoutParams.WRAP_CONTENT)
                popup.setHeight(ActionBar.LayoutParams.WRAP_CONTENT)
                popup.showAtLocation(root, Gravity.CENTER, 0, 0)
            }
            vie.setText(order)
            /*
            vie.setOnClickListener {
                popup.dismiss()
            }*/
        }
    }
}