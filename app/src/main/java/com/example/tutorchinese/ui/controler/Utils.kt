package com.example.tutorchinese.ui.controler

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class Utils {
    companion object {
        const val host = "http://192.168.1.7/"
        //val host = "http://10.255.252.44/"
    }

    fun getGson(): Gson? {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setDateFormat("M/d/yy hh:mm a")
        return gsonBuilder.create()
    }
}