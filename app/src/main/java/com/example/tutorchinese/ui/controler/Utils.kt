package com.example.tutorchinese.ui.controler

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.text.DateFormat
import java.text.SimpleDateFormat

class Utils {
    companion object {
        const val host = "http://192.168.1.7/"
        //const val host = "http://10.255.252.44/"
        //const val host = "http://10.255.252.45"
        //const val host = "http:/192.168.2.183"

         val dateFormatter: DateFormat = SimpleDateFormat("yyyy-MM-dd")
         val timeFormatter: DateFormat = SimpleDateFormat("HH:mm")
    }

    fun getGson(): Gson? {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setDateFormat("M/d/yy hh:mm a")
        return gsonBuilder.create()
    }
}