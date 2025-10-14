package com.studybuddy.app.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkUtils {
    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.activeNetwork ?: return false
        val actNw = cm.getNetworkCapabilities(capabilities) ?: return false
        return actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
