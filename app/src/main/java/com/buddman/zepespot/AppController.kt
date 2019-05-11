package com.buddman.zepespot

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import com.tsengvn.typekit.Typekit

class AppController : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
//        Glide.with(this)
        initNotification()
        initFont()
    }

    fun initNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !getSharedPreferences("ZEPESPOT", 0).getBoolean("isOReady", false)) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            val channelMessage = NotificationChannel("ZEPESPOT", getString(R.string.app_name), android.app.NotificationManager.IMPORTANCE_LOW)
            channelMessage.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            channelMessage.enableVibration(false)
            notificationManager.createNotificationChannel(channelMessage)
            getSharedPreferences("ZEPESPOT", 0).edit().putBoolean("isOReady", true).apply()
        }
    }

    private fun initFont() {
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "Gilroy-Light.otf"))
                .addBold(Typekit.createFromAsset(this, "Gilroy-ExtraBold.otf"))
    }

    companion object {
        lateinit var context: Context

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