package com.example.tutorchinese.ui.controler

import android.content.Context
import android.net.ConnectivityManager

class NetworkConnectCheck {
    fun isNetworkConnected(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}