package com.example.typi_linkedin

import android.content.ClipboardManager
import android.content.Context
import android.graphics.Typeface
import android.text.Html
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class ViewMaker
{
    companion object{
        enum class Category {
            SMILEY, FOOD, CARS, NATURE
        }
        var listaSmajli1="\uD83D\uDE00\uD83D\uDE03\uD83D\uDE04\uD83D\uDE01\uD83D\uDE06\uD83E\uDD79\uD83D\uDE05\uD83D\uDE02\uD83E\uDD23\uD83E\uDD72☺️\uD83D\uDE0A\uD83D\uDE07\uD83D\uDE42\uD83D\uDE43\uD83D\uDE09\uD83D\uDE0C\uD83D\uDE0D\uD83E\uDD70\uD83D\uDE18\uD83D\uDE17\uD83D\uDE19\uD83D\uDE1A\uD83D\uDE0B\uD83D\uDE1B\uD83D\uDE1D\uD83D\uDE1C\uD83E\uDD2A\uD83E\uDD28\uD83E\uDDD0\uD83E\uDD13\uD83D\uDE0E\uD83E\uDD78\uD83E\uDD29\uD83E\uDD73\uD83D\uDE0F\uD83D\uDE12\uD83D\uDE1E\uD83D\uDE14\uD83D\uDE1F\uD83D\uDE15\uD83D\uDE41☹️\uD83D\uDE23\uD83D\uDE16\uD83D\uDE2B\uD83D\uDE29\uD83E\uDD7A\uD83D\uDE22\uD83D\uDE2D\uD83D\uDE24\uD83D\uDE20\uD83D\uDE21\uD83E\uDD2C\uD83E\uDD2F\uD83D\uDE33\uD83E\uDD75\uD83E\uDD76\uD83D\uDE36\u200D\uD83C\uDF2B️\uD83D\uDE31\uD83D\uDE28\uD83D\uDE30\uD83D\uDE25\uD83D\uDE13\uD83E\uDD17\uD83E\uDD14\uD83E\uDEE3\uD83E\uDD2D\uD83E\uDEE2\uD83E\uDEE1\uD83E\uDD2B\uD83E\uDEE0\uD83E\uDD25\uD83D\uDE36\uD83D\uDE10\uD83E\uDEE4\uD83D\uDE11\uD83D\uDE2C\uD83D\uDE44\uD83D\uDE2F\uD83D\uDE26\uD83D\uDE27\uD83D\uDE2E\uD83D\uDE32\uD83E\uDD71\uD83D\uDE34\uD83E\uDD24\uD83D\uDE2A\uD83D\uDE2E\u200D\uD83D\uDCA8\uD83D\uDE35\uD83D\uDE35\u200D\uD83D\uDCAB\uD83E\uDD10\uD83E\uDD74\uD83E\uDD22\uD83E\uDD2E\uD83E\uDD27\uD83D\uDE37\uD83E\uDD12\uD83E\uDD15\uD83E\uDD11\uD83E\uDD20\uD83D\uDE08\uD83D\uDC7F\uD83D\uDC79\uD83D\uDC7A\uD83E\uDD21\uD83D\uDCA9\uD83D\uDC7B\uD83D\uDC80☠️\uD83D\uDC7D\uD83D\uDC7E\uD83E\uDD16\uD83C\uDF83\uD83D\uDE3A\uD83D\uDE38\uD83D\uDE39\uD83D\uDE3B\uD83D\uDE3C\uD83D\uDE3D\uD83D\uDE40\uD83D\uDE3F\uD83D\uDE3E\uD83E\uDEF6"
        var listaSmajli2="\uD83E\uDD32\uD83D\uDC50\uD83D\uDE4C\uD83D\uDC4F\uD83E\uDD1D\uD83D\uDC4D\uD83D\uDC4E\uD83D\uDC4A✊\uD83E\uDD1B\uD83E\uDD1C\uD83E\uDD1E✌️\uD83E\uDEF0\uD83E\uDD1F\uD83E\uDD18\uD83D\uDC4C\uD83E\uDD0C\uD83E\uDD0F\uD83E\uDEF3\uD83E\uDEF4\uD83D\uDC48\uD83D\uDC49\uD83D\uDC46\uD83D\uDC47☝️✋\uD83E\uDD1A\uD83D\uDD90️\uD83D\uDD96\uD83D\uDC4B✋\uD83E\uDEF2\uD83E\uDD19\uD83E\uDEF1\uD83D\uDCAA\uD83E\uDDBE\uD83D\uDD95✍️\uD83D\uDE4F\uD83E\uDEF5\uD83E\uDDB6\uD83E\uDDB5\uD83E\uDDBF\uD83D\uDC84\uD83D\uDC8B\uD83D\uDC44\uD83E\uDEE6\uD83E\uDDB7\uD83D\uDC45\uD83D\uDC42\uD83E\uDDBB\uD83D\uDC43\uD83D\uDC63\uD83D\uDC41️\uD83D\uDC40\uD83E\uDEC0\uD83E\uDEC1\uD83E\uDDE0\uD83D\uDDE3️\uD83D\uDC64\uD83D\uDC65\uD83E\uDEC2\uD83D\uDC76\uD83D\uDC67\uD83E\uDDD2\uD83D\uDC66\uD83D\uDC69\uD83E\uDDD1\uD83D\uDC68\uD83D\uDC69\u200D\uD83E"
        var listaSmajli3="\uD83E\uDDF6\uD83E\uDDF5\uD83E\uDEA1\uD83E\uDDE5\uD83E\uDD7C\uD83E\uDDBA\uD83D\uDC5A\uD83D\uDC55\uD83D\uDC56\uD83E\uDE72\uD83E\uDE73\uD83D\uDC54\uD83D\uDC57\uD83D\uDC59\uD83E\uDE71\uD83D\uDC58\uD83E\uDD7B\uD83E\uDE74\uD83E\uDD7F\uD83D\uDC60\uD83D\uDC61\uD83D\uDC62\uD83D\uDC5E\uD83D\uDC5F\uD83E\uDD7E\uD83E\uDDE6\uD83E\uDDE4\uD83E\uDDE3\uD83C\uDFA9\uD83E\uDDE2\uD83D\uDC52\uD83C\uDF93⛑️\uD83E\uDE96\uD83D\uDC51\uD83D\uDC8D\uD83D\uDC5D\uD83D\uDC5B\uD83D\uDC5C\uD83D\uDCBC\uD83C\uDF92\uD83E\uDDF3\uD83D\uDC53\uD83D\uDD76️\uD83E\uDD7D\uD83C\uDF02"
        var listaSmajlija=listaSmajli1+ listaSmajli2+ listaSmajli3;

        var listaZivotinje1="\uD83D\uDC36\uD83D\uDC31\uD83D\uDC2D\uD83D\uDC39\uD83D\uDC30\uD83E\uDD8A\uD83D\uDC3B\uD83D\uDC3C\uD83D\uDC3B\u200D❄️\uD83D\uDC28\uD83D\uDC2F\uD83E\uDD81\uD83D\uDC2E\uD83D\uDC37\uD83D\uDC3D\uD83D\uDC38\uD83D\uDC35\uD83D\uDE48\uD83D\uDE49\uD83D\uDE4A\uD83D\uDC12\uD83D\uDC14\uD83D\uDC27\uD83D\uDC26\uD83D\uDC24\uD83D\uDC23\uD83D\uDC25\uD83E\uDD86\uD83E\uDD85\uD83E\uDD89\uD83E\uDD87\uD83D\uDC3A\uD83D\uDC17\uD83D\uDC34\uD83E\uDD84\uD83D\uDC1D\uD83E\uDEB1\uD83D\uDC1B\uD83E\uDD8B\uD83D\uDC0C\uD83D\uDC1E\uD83D\uDC1C\uD83E\uDEB0\uD83E\uDEB2\uD83E\uDEB3\uD83E\uDD9F\uD83E\uDD97\uD83D\uDD77️\uD83D\uDD78️\uD83E\uDD82\uD83D\uDC22\uD83D\uDC0D\uD83E\uDD8E\uD83E\uDD96\uD83E\uDD95\uD83D\uDC19\uD83E\uDD91\uD83E\uDD90\uD83E\uDD9E\uD83E\uDD80\uD83D\uDC21\uD83D\uDC20\uD83D\uDC1F\uD83D\uDC2C\uD83D\uDC33\uD83D\uDC0B\uD83E\uDD88\uD83E\uDDAD\uD83D\uDC0A\uD83D\uDC05\uD83D\uDC06\uD83E\uDD93\uD83E\uDD8D\uD83E\uDDA7\uD83E\uDDA3\uD83D\uDC18\uD83E\uDD9B\uD83E\uDD8F\uD83D\uDC2A\uD83D\uDC2B\uD83E\uDD92\uD83E\uDD98\uD83E\uDDAC\uD83D\uDC03\uD83D\uDC02\uD83D\uDC04\uD83D\uDC0E\uD83D\uDC16\uD83D\uDC0F\uD83D\uDC11\uD83E\uDD99\uD83D\uDC10\uD83E\uDD8C\uD83D\uDC15\uD83D\uDC29\uD83E\uDDAE\uD83D\uDC15\u200D\uD83E\uDDBA\uD83D\uDC08\uD83D\uDC08\u200D⬛\uD83E\uDEB6\uD83D\uDC13\uD83E\uDD83\uD83E\uDDA4\uD83E\uDD9A\uD83E\uDD9C\uD83E\uDDA2\uD83E\uDDA9\uD83D\uDD4A️\uD83D\uDC07\uD83E\uDD9D\uD83E\uDDA8\uD83E\uDDA1\uD83E\uDDAB\uD83E\uDDA6\uD83E\uDDA5\uD83D\uDC01\uD83D\uDC00\uD83D\uDC3F️\uD83E\uDD94\uD83D\uDC3E"
        var listaZivotnje2="\uD83D\uDC09\uD83D\uDC32\uD83C\uDF35\uD83C\uDF84\uD83C\uDF32\uD83C\uDF33\uD83C\uDF34\uD83E\uDEB5\uD83C\uDF31\uD83C\uDF3F☘️\uD83C\uDF40\uD83C\uDF8D\uD83E\uDEB4\uD83C\uDF8B\uD83C\uDF43\uD83C\uDF42\uD83C\uDF41\uD83E\uDEBA\uD83E\uDEB9\uD83C\uDF44\uD83D\uDC1A\uD83E\uDEB8\uD83E\uDEA8\uD83C\uDF3E\uD83D\uDC90\uD83C\uDF37\uD83C\uDF39\uD83E\uDD40\uD83E\uDEB7\uD83C\uDF3A\uD83C\uDF38\uD83C\uDF3C\uD83C\uDF3B\uD83C\uDF1E\uD83C\uDF1D\uD83C\uDF1B\uD83C\uDF1C\uD83C\uDF1A\uD83C\uDF15\uD83C\uDF16\uD83C\uDF17\uD83C\uDF18\uD83C\uDF11\uD83C\uDF12\uD83C\uDF13\uD83C\uDF14\uD83C\uDF19\uD83C\uDF0E\uD83C\uDF0D\uD83C\uDF0F\uD83E\uDE90\uD83D\uDCAB⭐️\uD83C\uDF1F✨⚡️☄️\uD83D\uDCA5\uD83D\uDD25\uD83C\uDF2A️\uD83C\uDF08☀️\uD83C\uDF24️⛅️\uD83C\uDF25️☁️\uD83C\uDF26️\uD83C\uDF27️⛈️\uD83C\uDF29️\uD83C\uDF28️❄️☃️⛄️\uD83C\uDF2C️\uD83D\uDCA8\uD83D\uDCA7\uD83D\uDCA6\uD83E\uDEE7☔️☂️\uD83C\uDF0A\uD83C\uDF2B"
       var listaZivotinja= listaZivotinje1+ listaZivotnje2;

        var listaHrana1="\uD83C\uDF4F\uD83C\uDF4E\uD83C\uDF50\uD83C\uDF4A\uD83C\uDF4B\uD83C\uDF4C\uD83C\uDF49\uD83C\uDF47\uD83C\uDF53\uD83E\uDED0\uD83C\uDF48\uD83C\uDF52\uD83C\uDF51\uD83E\uDD6D\uD83C\uDF4D\uD83E\uDD65\uD83E\uDD5D\uD83C\uDF45\uD83C\uDF46\uD83E\uDD51\uD83E\uDD66\uD83E\uDD6C\uD83E\uDD52\uD83C\uDF36️\uD83E\uDED1\uD83C\uDF3D\uD83E\uDD55\uD83E\uDED2\uD83E\uDDC4\uD83E\uDDC5\uD83E\uDD54\uD83C\uDF60\uD83E\uDD50\uD83E\uDD6F\uD83C\uDF5E\uD83E\uDD56\uD83E\uDD68\uD83E\uDDC0\uD83E\uDD5A\uD83C\uDF73\uD83E\uDDC8\uD83E\uDD5E\uD83E\uDDC7\uD83E\uDD53\uD83E\uDD69\uD83C\uDF57\uD83C\uDF56\uD83E\uDDB4\uD83C\uDF2D\uD83C\uDF54\uD83C\uDF5F\uD83C\uDF55\uD83E\uDED3\uD83E\uDD6A\uD83E\uDD59\uD83E\uDDC6\uD83C\uDF2E\uD83C\uDF2F\uD83E\uDED4\uD83E\uDD57\uD83E\uDD58\uD83E\uDED5\uD83E\uDD6B\uD83E\uDED9\uD83C\uDF5D\uD83C\uDF5C\uD83C\uDF72\uD83C\uDF5B\uD83C\uDF63\uD83C\uDF71\uD83E\uDD5F\uD83E\uDDAA\uD83C\uDF64\uD83C\uDF59\uD83C\uDF5A\uD83C\uDF58\uD83C\uDF65\uD83E\uDD60\uD83E\uDD6E\uD83C\uDF62\uD83C\uDF61\uD83C\uDF67\uD83C\uDF68\uD83C\uDF66\uD83E\uDD67\uD83E\uDDC1\uD83C\uDF70\uD83C\uDF82\uD83C\uDF6E\uD83C\uDF6D\uD83C\uDF6C\uD83C\uDF6B\uD83C\uDF7F\uD83C\uDF69\uD83C\uDF6A\uD83C\uDF30\uD83E\uDD5C\uD83E\uDED8\uD83C\uDF6F"
        var listaHrana2="\uD83E\uDD5B\uD83E\uDED7\uD83C\uDF7C\uD83E\uDED6☕️\uD83C\uDF75\uD83E\uDDC3\uD83E\uDD64\uD83E\uDDCB\uD83C\uDF76\uD83C\uDF7A\uD83C\uDF7B\uD83E\uDD42\uD83C\uDF77\uD83E\uDD43\uD83C\uDF78\uD83C\uDF79\uD83E\uDDC9\uD83C\uDF7E\uD83E\uDDCA\uD83E\uDD44\uD83C\uDF74\uD83C\uDF7D️\uD83E\uDD63\uD83E\uDD61\uD83E\uDD62\uD83E\uDDC2"
        var listaHrana= listaHrana1+ listaHrana2

        var listaSport1="⚽️\uD83C\uDFC0\uD83C\uDFC8⚾️\uD83E\uDD4E\uD83C\uDFBE\uD83C\uDFD0\uD83C\uDFC9\uD83E\uDD4F\uD83C\uDFB1\uD83E\uDE80\uD83C\uDFD3\uD83C\uDFF8\uD83C\uDFD2\uD83C\uDFD1\uD83E\uDD4D\uD83C\uDFCF\uD83E\uDE83\uD83E\uDD45⛳️\uD83E\uDE81\uD83D\uDEDD\uD83C\uDFF9\uD83C\uDFA3\uD83E\uDD3F\uD83E\uDD4A\uD83E\uDD4B\uD83C\uDFBD\uD83D\uDEF9\uD83D\uDEFC\uD83D\uDEF7⛸️\uD83E\uDD4C\uD83C\uDFBF⛷️"
        var listaSportObjekti="\uD83C\uDFC6\uD83E\uDD47\uD83E\uDD48\uD83E\uDD49\uD83C\uDFC5\uD83C\uDF96️\uD83C\uDFF5️\uD83C\uDF97️\uD83C\uDFAB\uD83C\uDF9F️\uD83C\uDFAA\uD83E\uDD39\u200D♀️\uD83E\uDD39\uD83E\uDD39\u200D♂️\uD83C\uDFAD\uD83E\uDE70\uD83C\uDFA8\uD83C\uDFAC\uD83C\uDFA4\uD83C\uDFA7\uD83C\uDFBC\uD83C\uDFB9\uD83E\uDD41\uD83E\uDE98\uD83C\uDFB7\uD83C\uDFBA\uD83E\uDE97\uD83C\uDFB8\uD83E\uDE95\uD83C\uDFBB\uD83C\uDFB2♟️\uD83C\uDFAF\uD83C\uDFB3\uD83C\uDFAE\uD83C\uDFB0\uD83E\uDDE9"
        var listaSport=listaSport1+  listaSportObjekti

        var prevoznaSredstva="\uD83D\uDE97\uD83D\uDE95\uD83D\uDE99\uD83D\uDE8C\uD83D\uDE8E\uD83C\uDFCE️\uD83D\uDE93\uD83D\uDE91\uD83D\uDE92\uD83D\uDE90\uD83D\uDEFB\uD83D\uDE9A\uD83D\uDE9B\uD83D\uDE9C\uD83E\uDDAF\uD83E\uDDBD\uD83E\uDDBC\uD83E\uDE7C\uD83D\uDEF4\uD83D\uDEB2\uD83D\uDEF5\uD83C\uDFCD️\uD83D\uDEFA\uD83D\uDEDE\uD83D\uDEA8\uD83D\uDE94\uD83D\uDE8D\uD83D\uDE98\uD83D\uDE96\uD83D\uDEA1\uD83D\uDEA0\uD83D\uDE9F\uD83D\uDE83\uD83D\uDE8B\uD83D\uDE9E\uD83D\uDE9D\uD83D\uDE84\uD83D\uDE85\uD83D\uDE88\uD83D\uDE82\uD83D\uDE86\uD83D\uDE87\uD83D\uDE8A\uD83D\uDE89✈️\uD83D\uDEEB\uD83D\uDEEC\uD83D\uDEE9️\uD83D\uDCBA\uD83D\uDEF0️\uD83D\uDE80\uD83D\uDEF8\uD83D\uDE81\uD83D\uDEF6⛵️\uD83D\uDEA4\uD83D\uDEE5️\uD83D\uDEF3️⛴️\uD83D\uDEA2\uD83D\uDEDF⚓️\uD83E\uDE9D⛽️\uD83D\uDEA7\uD83D\uDEA6\uD83D\uDEA5\uD83D\uDE8F\uD83D\uDDFA️\uD83D\uDDFF\uD83D\uDDFD\uD83D\uDDFC\uD83C\uDFF0\uD83C\uDFEF\uD83C\uDFDF️\uD83C\uDFA1\uD83C\uDFA2\uD83C\uDFA0⛲️⛱️\uD83C\uDFD6️\uD83C\uDFDD️\uD83C\uDFDC️\uD83C\uDF0B⛰️\uD83C\uDFD4️\uD83D\uDDFB\uD83C\uDFD5️⛺️\uD83D\uDED6"
        var zgrade="\uD83C\uDFE0\uD83C\uDFE1\uD83C\uDFD8️\uD83C\uDFDA️\uD83C\uDFD7️\uD83C\uDFED\uD83C\uDFE2\uD83C\uDFEC\uD83C\uDFE3\uD83C\uDFE4\uD83C\uDFE5\uD83C\uDFE6\uD83C\uDFE8\uD83C\uDFEA\uD83C\uDFEB\uD83C\uDFE9\uD83D\uDC92\uD83C\uDFDB️⛪️\uD83D\uDD4C\uD83D\uDD4D\uD83D\uDED5\uD83D\uDD4B⛩️\uD83D\uDEE4️\uD83D\uDEE3️\uD83D\uDDFE\uD83C\uDF91\uD83C\uDFDE️\uD83C\uDF05\uD83C\uDF04\uD83C\uDF20\uD83C\uDF87\uD83C\uDF86\uD83C\uDF07\uD83C\uDF06\uD83C\uDFD9️\uD83C\uDF03\uD83C\uDF0C\uD83C\uDF09\uD83C\uDF01"
        var listaTurizam= prevoznaSredstva+zgrade;

        var listaPredmeta="⌚️\uD83D\uDCF1\uD83D\uDCF2\uD83D\uDCBB⌨️\uD83D\uDDA5️\uD83D\uDDA8️\uD83D\uDDB1️\uD83D\uDDB2️\uD83D\uDD79️\uD83D\uDDDC️\uD83D\uDCBD\uD83D\uDCBE\uD83D\uDCBF\uD83D\uDCC0\uD83D\uDCFC\uD83D\uDCF7\uD83D\uDCF8\uD83D\uDCF9\uD83C\uDFA5\uD83D\uDCFD️\uD83C\uDF9E️\uD83D\uDCDE☎️\uD83D\uDCDF\uD83D\uDCE0\uD83D\uDCFA\uD83D\uDCFB\uD83C\uDF99️\uD83C\uDF9A️\uD83C\uDF9B️\uD83E\uDDED⏱️⏲️⏰\uD83D\uDD70️⌛️⏳\uD83D\uDCE1\uD83D\uDD0B\uD83E\uDEAB\uD83D\uDD0C\uD83D\uDCA1\uD83D\uDD26\uD83D\uDD6F️\uD83E\uDE94\uD83E\uDDEF\uD83D\uDEE2️\uD83D\uDCB8\uD83D\uDCB5\uD83D\uDCB4\uD83D\uDCB6\uD83D\uDCB7\uD83E\uDE99\uD83D\uDCB0\uD83D\uDCB3\uD83E\uDEAA\uD83D\uDC8E⚖️\uD83E\uDE9C\uD83E\uDDF0\uD83E\uDE9B\uD83D\uDD27\uD83D\uDD28⚒️\uD83D\uDEE0️⛏️\uD83E\uDE9A\uD83D\uDD29⚙️\uD83E\uDEA4\uD83E\uDDF1⛓️\uD83E\uDDF2\uD83D\uDD2B\uD83D\uDCA3\uD83E\uDDE8\uD83E\uDE93\uD83D\uDD2A\uD83D\uDDE1️⚔️\uD83D\uDEE1️\uD83D\uDEAC⚰️\uD83E\uDEA6⚱️\uD83C\uDFFA\uD83D\uDD2E\uD83D\uDCFF\uD83E\uDDFF\uD83E\uDEAC\uD83D\uDC88⚗️\uD83D\uDD2D\uD83D\uDD2C\uD83D\uDD73️\uD83E\uDE7B\uD83E\uDE79\uD83E\uDE7A\uD83D\uDC8A\uD83D\uDC89\uD83E\uDE78\uD83E\uDDEC\uD83E\uDDA0\uD83E\uDDEB\uD83E\uDDEA\uD83C\uDF21️\uD83E\uDDF9\uD83E\uDEA0\uD83E\uDDFA\uD83E\uDDFB\uD83D\uDEBD\uD83D\uDEB0\uD83D\uDEBF\uD83D\uDEC1\uD83E\uDDFC\uD83E\uDEA5\uD83E\uDE92\uD83E\uDDFD\uD83E\uDEA3\uD83E\uDDF4\uD83D\uDECE️\uD83D\uDD11\uD83D\uDDDD️\uD83D\uDEAA\uD83E\uDE91\uD83D\uDECB️\uD83D\uDECF️\uD83D\uDECC\uD83E\uDDF8\uD83E\uDE86\uD83D\uDDBC️\uD83E\uDE9E\uD83E\uDE9F\uD83D\uDECD️\uD83D\uDED2\uD83C\uDF81\uD83C\uDF88\uD83C\uDF8F\uD83C\uDF80\uD83E\uDE84\uD83E\uDE85\uD83C\uDF8A\uD83C\uDF89\uD83C\uDF8E\uD83C\uDFEE\uD83C\uDF90\uD83E\uDEA9\uD83E\uDDE7✉️\uD83D\uDCE9\uD83D\uDCE8\uD83D\uDCE7\uD83D\uDC8C\uD83D\uDCE5\uD83D\uDCE4\uD83D\uDCE6\uD83C\uDFF7️\uD83E\uDEA7\uD83D\uDCEA\uD83D\uDCEB\uD83D\uDCEC\uD83D\uDCED\uD83D\uDCEE\uD83D\uDCEF\uD83D\uDCDC\uD83D\uDCC3\uD83D\uDCC4\uD83D\uDCD1\uD83E\uDDFE\uD83D\uDCCA\uD83D\uDCC8\uD83D\uDCC9\uD83D\uDDD2️\uD83D\uDDD3️\uD83D\uDCC6\uD83D\uDCC5\uD83D\uDDD1️\uD83D\uDCC7\uD83D\uDDC3️\uD83D\uDDF3️\uD83D\uDDC4️\uD83D\uDCCB\uD83D\uDCC1\uD83D\uDCC2\uD83D\uDDC2️\uD83D\uDDDE️\uD83D\uDCF0\uD83D\uDCD3\uD83D\uDCD4\uD83D\uDCD2\uD83D\uDCD5\uD83D\uDCD7\uD83D\uDCD8\uD83D\uDCD9\uD83D\uDCDA\uD83D\uDCD6\uD83D\uDD16\uD83E\uDDF7\uD83D\uDD17\uD83D\uDCCE\uD83D\uDD87️\uD83D\uDCD0\uD83D\uDCCF\uD83E\uDDEE\uD83D\uDCCC\uD83D\uDCCD✂️\uD83D\uDD8A️\uD83D\uDD8B️✒️\uD83D\uDD8C️\uD83D\uDD8D️\uD83D\uDCDD✏️\uD83D\uDD0D\uD83D\uDD0E\uD83D\uDD0F\uD83D\uDD10\uD83D\uDD12\uD83D\uDD13"
        var listaPredmeta2 = "⌚️,📱,💻,📚,⌨️,🚥,🚦,🛋️,🛌,📝,📒,📓,📔,📼,📷,📸,📹,🎥,🎞️,📺,🥇,📭,📮,🗣️,📯,🎙️,🎚️,🎛️,📻,🎧,🎤,🎬,🎭,🎨,🎻,🎹,⚖️,🦺,🥼,🥽,🥾,⚒️,🛠️,⛏️,🪚,🗜️,⚙️,🪛,⛓️,🔫,💣,🔪,🗡️,⚔️,🛡️,⚰️,🪦,⚱️,🔮,🧭,🪶,🔍,🔎,🕯️,💡,🔦,🏮,🪔,🧯,🛢️,🧴,🧹,🧺,🧻,🪣,🧼,🧽,🧾,🛒,🚬,⚰️,🦷,🩸,🧸,🃏,🀄,🎴,🎭,🖼️,🎨,🛍️,🛒,🎁,🎀,🎉,🎊,🎈,🎂,🎂,🧁,🍰,🥧,🍮,🍭,🍬,🍫,🍩,🍪,🌰,🥜,🍯,🥛,🍼,☕,🍵,🧉,🍹,🍸,🍺,🍻,🥂,🥃,🥤,🧊,🍾,🥄,🍴,🍽️,🥢,🥡,🥢,🥄,🔪,🏺,🧊,🧂,🥄,🔑,🗝️,🚪,🛋️,🛌,🚽,🚿,🛀,🪒,🧴,🧹,🧺,🧻,🪣,🌡️,⏱️,⏲️,⏰,🕰️,🕛,🕧,🕐,🕜,🕑,🕝,🕒,🕞,🕓,🕟,🕔,🕠,🕕,🕡,🕖,🕢,🕗,🕣,🕘,🕤"

        var listaPredmeta3="&#8986;&#128241;&#128242;&#128187;&#9000;&#129301;&#129304;&#129425;&#129426;&#128377;&#129660;&#128205;&#128206;&#128207;&#127909;&#128247;&#128248;&#128249;&#127989;&#128253;&#127974;&#128222;&#9742;&#128223;&#128224;&#128250;&#128251;&#128121;&#128244;&#128092;&#128095;&#128681;&#9201;&#9203;&#128336;&#127920;&#8987;&#128079;&#129317;&#129318;&#128102;&#128103;&#127773;&#129516;&#129517;&#129518;&#129805;&#8988;&#9874;&#129466;&#127929;&#129469;&#129470;&#127998;&#128711;&#128706;&#128705;&#128707;&#128708;&#129033;&#128736;&#128740;&#128739;&#129303;&#128739;&#128733;&#128734;&#128732;&#128755;&#128752;&#128751;&#128753;&#129489;&#128736;&#128118;&#129352;&#129347;&#129346;&#128683;&#128656;&#128155;&#129474;&#127910;&#128661;&#128663;&#129297;&#129299;&#128737;&#128738;&#128742;&#128741;&#129412;&#129413;&#128704;&#128295;&#128296;&#128298;&#128300;&#128299;&#128302;&#129440;&#128701;&#128700;&#128703;&#129350;&#129349;&#128686;&#128685;&#128689;&#129491;&#128275;&#128276;&#128279;&#128277;&#128278;&#129456;&#128303;&#128304;&#129312;&#129495;&#127873;&#128187;&#128220;&#128338;&#128286;&#128255;&#128256;&#128176;&#129319;&#128581;&#128580;&#128583;&#129331;&#128192;&#128227;&#129519;&#128196;&#128197;&#128199;&#128198;&#129342;&#129344;&#128202;&#128203;&#128204;&#129392;&#129393;&#128135;&#128154;&#128290;&#128291;&#129476;&#129303;&#128113;&#127936;&#128288;&#128289;&#128295;&#128294;&#128297;&#129480;&#128283;&#128284;&#128286;&#129457;&#129458;&#129464;&#128581;&#128175;&#127925;&#128230;&#129462;&#129463;&#128169;&#128167;&#128680;&#128167;&#129488;&#129489;&#128077;&#128078;&#128079;&#128074;&#129408;&#129409;&#128069;&#128076;&#129459;&#129460;&#128071;&#128072;&#129481;&#128081;&#128068;&#128073;&#128077;&#128664;&#128690;&#128662;&#129328;&#129329;&#128100;&#128101;&#128140;&#128129;&#128130;&#128131;&#128132;&#128135;&#128138;&#128139;&#129422;&#129423;&#129424;&#128133;&#128134;&#129488;&#127980;&#127985;&#127986;&#127987;&#127988;&#128276;&#129491;&#129464;&#9972;&#9973;&#128674;&#129472;&#128181;&#129408;&#128118;&#127916;&#128680;&#128424;&#128425;&#128422;&#128423;&#128334;&#128336;&#128335;&#128337;&#128348;&#129392;&#128133;&#128132;&#129435;&#128665"

        var simboli1="❤️\uD83E\uDDE1\uD83D\uDC9B\uD83D\uDC9A\uD83D\uDC99\uD83D\uDC9C\uD83D\uDDA4\uD83E\uDD0D\uD83E\uDD0E\uD83D\uDC94❤️\u200D\uD83D\uDD25❤️\u200D\uD83E\uDE79❣️\uD83D\uDC95\uD83D\uDC9E\uD83D\uDC93\uD83D\uDC97\uD83D\uDC96\uD83D\uDC98\uD83D\uDC9D\uD83D\uDC9F☮️✝️☪️\uD83D\uDD49️☸️✡️\uD83D\uDD2F\uD83D\uDD4E☯️☦️\uD83D\uDED0⛎♈️♉️♊️♋️♌️♍️♎️♏️♐️♑️♒️♓️\uD83C\uDD94⚛️\uD83C\uDE51☢️☣️\uD83D\uDCF4\uD83D\uDCF3\uD83C\uDE36\uD83C\uDE1A️\uD83C\uDE38\uD83C\uDE3A\uD83C\uDE37️✴️\uD83C\uDD9A\uD83D\uDCAE\uD83C\uDE50㊙️㊗️\uD83C\uDE34\uD83C\uDE35\uD83C\uDE39\uD83C\uDE32\uD83C\uDD70️\uD83C\uDD71️\uD83C\uDD8E\uD83C\uDD91\uD83C\uDD7E️\uD83C\uDD98❌⭕️\uD83D\uDED1⛔️\uD83D\uDCDB\uD83D\uDEAB\uD83D\uDCAF\uD83D\uDCA2♨️\uD83D\uDEB7\uD83D\uDEAF\uD83D\uDEB3\uD83D\uDEB1\uD83D\uDD1E\uD83D\uDCF5\uD83D\uDEAD❗️❕❓❔‼️⁉️\uD83D\uDD05\uD83D\uDD06〽️⚠️\uD83D\uDEB8\uD83D\uDD31⚜️\uD83D\uDD30♻️✅\uD83C\uDE2F️\uD83D\uDCB9❇️✳️❎\uD83C\uDF10\uD83D\uDCA0Ⓜ️\uD83C\uDF00\uD83D\uDCA4\uD83C\uDFE7\uD83D\uDEBE♿️\uD83C\uDD7F️\uD83D\uDED7\uD83C\uDE33\uD83C\uDE02️\uD83D\uDEC2\uD83D\uDEC3\uD83D\uDEC4\uD83D\uDEC5\uD83D\uDEB9\uD83D\uDEBA\uD83D\uDEBC⚧️\uD83D\uDEBB\uD83D\uDEAE\uD83C\uDFA6\uD83D\uDCF6\uD83C\uDE01\uD83D\uDD23ℹ️\uD83D\uDD24\uD83D\uDD21\uD83D\uDD20\uD83C\uDD96\uD83C\uDD97\uD83C\uDD99\uD83C\uDD92\uD83C\uDD95\uD83C\uDD93▶️⏸️⏯️⏹️⏺️⏭️⏮️⏩️⏪️⏫️⏬️◀️\uD83D\uDD3C\uD83D\uDD3D➡️⬅️⬆️⬇️↗️↘️↙️↖️↕️↔️↪️↩️⤴️⤵️\uD83D\uDD00\uD83D\uDD01\uD83D\uDD02\uD83D\uDD04\uD83D\uDD03"
        var simboli2="\uD83D\uDD05\uD83D\uDD06〽️⚠️\uD83D\uDEB8\uD83D\uDD31⚜️\uD83D\uDD30♻️✅\uD83C\uDE2F️\uD83D\uDCB9❇️✳️❎\uD83C\uDF10\uD83D\uDCA0Ⓜ️\uD83C\uDF00\uD83D\uDCA4\uD83C\uDFE7\uD83D\uDEBE♿️\uD83C\uDD7F️\uD83D\uDED7\uD83C\uDE33\uD83C\uDE02️\uD83D\uDEC2\uD83D\uDEC3\uD83D\uDEC4\uD83D\uDEC5\uD83D\uDEB9\uD83D\uDEBA\uD83D\uDEBC⚧️\uD83D\uDEBB\uD83D\uDEAE\uD83C\uDFA6\uD83D\uDCF6\uD83C\uDE01\uD83D\uDD23ℹ️\uD83D\uDD24\uD83D\uDD21\uD83D\uDD20\uD83C\uDD96\uD83C\uDD97\uD83C\uDD99\uD83C\uDD92\uD83C\uDD95\uD83C\uDD93⏏️▶️⏸️⏯️⏹️⏺️⏭️⏮️⏩️⏪️⏫️⏬️◀️\uD83D\uDD3C\uD83D\uDD3D➡️⬅️⬆️⬇️↗️↘️↙️↖️↕️↔️↪️↩️⤴️⤵️\uD83D\uDD00\uD83D\uDD01\uD83D\uDD02\uD83D\uDD04\uD83D\uDD03"
        var listaSimbola= simboli1+simboli2


        fun convertEmojisIntoList(emojis:String): ArrayList<String> {
            Log.v("ima" ,emojis.length.toString()+" elemenata");
            var lista= arrayListOf<String>()
            for(i in  emojis.codePoints()){
               Log.v("Dodaje se " ,"&#"+i.toInt()+";")
                lista.add("&#"+i.toInt()+";")
                }
            return lista
        }


        fun categorySetup()
        {

        }
                fun emoji(keyboardRoot: View, context: Context, onKey: (primaryCode: Int, keyCodes: IntArray) -> Unit ,category:Category)
                {
                    var linearLayoutEmoji=keyboardRoot.findViewById<LinearLayout>(R.id.emoji)

                    var lp= LinearLayout(context)
                    lp.orientation= LinearLayout.VERTICAL

                    Log.v("Kategorija je ",category.toString());
                    var kbAgain=keyboardRoot.findViewById<Button>(R.id.type)
                    kbAgain.setOnClickListener{
                        showKeyboard(keyboardRoot)
                    }
                    var keyboardView=keyboardRoot.findViewById<TypiKeyboardView>(R.id.keyboard_view)
                    keyboardView.visibility=View.GONE
                    linearLayoutEmoji.visibility=View.VISIBLE
                    var linearLayoutEmojiCategory=keyboardRoot.findViewById<LinearLayout>(R.id.emoij_categoires)
                    linearLayoutEmojiCategory.visibility=View.VISIBLE
                    var linearLayout=keyboardRoot.findViewById<LinearLayout>(R.id.clipboard)
                    linearLayout.visibility=View.GONE


                    val emojiji2 = convertEmojisIntoList(listaZivotinja)
                    val emojiji= listaPredmeta2
                    var j = 0
                    val rowSize = 10
                    var currentRow = LinearLayout(context)
                    for (i in emojiji) {
                        val emojiView = TextView(context)
                        emojiView.text = Html.fromHtml(i.toString(), Html.FROM_HTML_MODE_COMPACT)
                        emojiView.textSize = 30f
                      //  emojiView.typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                      //  val codePoints = i.codePoints().toArray()
                        emojiView.layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )


                     //   Log.v("STA JE I: ", i+", a sta MYINT: "+myInt);
                        emojiView.setOnClickListener {
                            onKey(i.code, intArrayOf(i.toInt()))
                        }
                        if (currentRow.childCount >= rowSize) {
                            lp.addView(currentRow)
                            currentRow = LinearLayout(context)
                        }
                        currentRow.addView(emojiView)
                        j++
                    }
                    lp.addView(currentRow)
                    //  if (Category.SMILEY==category)
                        linearLayoutEmoji.addView(lp);


                    /*   for (i in 127744 until 128000 step 10)
                       {
                           var lp= LinearLayout(context)
                           lp.orientation= LinearLayout.HORIZONTAL
                           for(j in i until i+10)
                           {
                               var too= TextView(context)
                               val charArray = Character.toChars(j)
                               Log.v("CharArray", charArray.toString())
                               val surrogatePair = String(charArray)
                               Log.v("SurrogatePair", surrogatePair)
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

                     */
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