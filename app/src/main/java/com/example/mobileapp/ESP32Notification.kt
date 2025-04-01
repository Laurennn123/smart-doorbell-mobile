package com.example.mobileapp

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

    private val client = OkHttpClient()
    private val url = "" // IP ADDRESS OF ESP 32
    private val notificationChannelId = "front_door_channel"
    private val handler = Handler(Looper.getMainLooper())
    var isRunning = false

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
        fetchDataPeriodically()
        isRunning = true
    }

    private fun startForegroundService() {
        val TAG = "foreground"
        Log.d(TAG, "check")
        val notification = NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("ESP32 Service Running")
            .setContentText("Waiting for data...")
            .setSmallIcon(R.drawable.smart)
            .build()
        startForeground(1, notification)
    }

    private fun fetchDataPeriodically() {
        val TAG = "esp32"
        val request = Request.Builder().url(url).get().build()
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "Not received")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let {
                    val data = it.string()
                    Log.d(TAG, data)
                    showNotification()
                }
            }

        })

        handler.postDelayed({ fetchDataPeriodically() }, 1000)
    }

    private fun showNotification() {
        val notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val messageForFrontDoor = NotificationCompat.Builder(this, notificationChannelId)
            .setSmallIcon(R.drawable.smart)
            .setContentTitle("Check your front door!")
            .setContentText("There is someone outside on your front door, check it right now.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(2, messageForFrontDoor)
    }

    fun isServiceRunning(): Boolean {
        return isRunning
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

