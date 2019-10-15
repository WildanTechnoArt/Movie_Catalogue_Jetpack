package com.wildan.moviecatalogue.network

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi

class ConnectivityStatus {
    companion object {
        @RequiresApi(Build.VERSION_CODES.M)
        fun isConnected(context: Context?): Boolean {
            val manager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val connection =  manager.activeNetwork
            return connection != null
        }
    }
}