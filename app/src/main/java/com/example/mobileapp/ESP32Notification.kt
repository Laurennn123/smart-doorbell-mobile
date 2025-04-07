package com.example.mobileapp

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ESP32Notification : Service()  {

    private val esp32_url = "http://192.168.49.132/"
    private val client = OkHttpClient()
    private val handler = Handler(Looper.getMainLooper())

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        when(intent?.action) {
            Actions.START.toString() -> checkButtonStatus()
            Actions.STOP.toString() -> stopSelf()
        }
        checkButtonStatus()
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("ForegroundServiceType")
    private fun checkButtonStatus() {
        val notification = NotificationCompat.Builder(this, "front_door_channel")
            .setContentTitle("Foreground Service")
            .setContentText("Running...")
            .setSmallIcon(R.drawable.smart)
            .build()
        startForeground(1, notification)
        handler.postDelayed({
            val tag = "esp32"
            val request = Request.Builder().url(esp32_url).build()

            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    Log.d(tag, "failed")
                    println("ESP32 Request Failed: ${e.message}")
                }

                override fun onResponse(call: Call, response: okhttp3.Response) {
                    Log.d(tag, "sucess")
                    response.body?.string()?.let { responseData ->
                        if (responseData.contains("pressed")) {
                            showNotification()
                        }
                    }
                }

            })
            checkButtonStatus()
        }, 2000)
    }

    fun showNotification() {
        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val messageAboutFrontDoor = NotificationCompat.Builder(this, "front_door_channel")
            .setSmallIcon(R.drawable.smart)
            .setContentTitle("Check your front door!")
            .setContentText("There is someone outside on your front door, check it right now.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(2, messageAboutFrontDoor)
    }

    enum class Actions {
        START, STOP
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

