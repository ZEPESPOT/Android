package com.buddman.zepespot

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build

class AppController : Application(){

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
//        Glide.with(this)
        initNotification()
    }

    fun initNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !getSharedPreferences("JoypassApp", 0).getBoolean("isOReady_Re", false)) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            val channelMessage = NotificationChannel("JoypassApp", getString(R.string.app_name), android.app.NotificationManager.IMPORTANCE_LOW)
            channelMessage.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            channelMessage.enableVibration(false)
            notificationManager.createNotificationChannel(channelMessage)
            getSharedPreferences("JoypassApp", 0).edit().putBoolean("isOReady_Re", true).apply()
        }
    }
    companion object {
        lateinit var context : Context

        fun checkInternetConnection(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }


//    override fun onTerminate() {
//        super.onTerminate()
//        Log.e("asdf", "terminate")
//        .cancelAll()
//    }
}