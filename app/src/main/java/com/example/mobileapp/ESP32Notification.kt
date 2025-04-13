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
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ESP32Notification : Service()  {

    private lateinit var realtimeDatabase: DatabaseReference

    
    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        listenToFirebase()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun listenToFirebase() {
        realtimeDatabase = FirebaseDatabase.getInstance().getReference("registeredNames")
        realtimeDatabase.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(
                parentSnapShot: DataSnapshot,
                previousChildName: String?
            ) {
                val nameOfEnteringFacility = parentSnapShot.key.toString()
                var initialChildCount = parentSnapShot.childrenCount
                showNotification(name = nameOfEnteringFacility)

                parentSnapShot.ref.addChildEventListener(object : ChildEventListener {
                    var childCount = initialChildCount
                    override fun onChildAdded(
                        snapshot: DataSnapshot,
                        previousChildName: String?
                    ) {
                        if (childCount > 0) {
                            childCount--
                            return
                        }
                        showNotification(name = nameOfEnteringFacility)
                    }

                    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
                    override fun onChildRemoved(snapshot: DataSnapshot) {}
                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                    override fun onCancelled(error: DatabaseError) {}
                })

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}

        })
    }

    fun showNotification(name: String) {
        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val messageAboutFrontDoor = NotificationCompat.Builder(this, "front_door_channel")
            .setSmallIcon(R.drawable.face_scan_icon)
            .setContentTitle("Facility update")
            .setContentText("$name is entering facility")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(1, messageAboutFrontDoor)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

