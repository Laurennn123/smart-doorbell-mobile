package com.example.mobileapp

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat
import com.example.mobileapp.data.database.AccountDatabase
import com.example.mobileapp.data.repo.OfflineLogsRepository
import com.example.mobileapp.data.table.AccessLogs
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ESP32Notification : Service()  {

    private lateinit var realtimeDatabase: DatabaseReference
    private lateinit var accessLog: OfflineLogsRepository

    override fun onCreate() {
        super.onCreate()
        val database = AccountDatabase.getDatabase(this)
        accessLog = OfflineLogsRepository(database.accessLogDao())
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        if (intent?.action == "STOP_SERVICE") {
            stopSelf()
        }
        val notification = NotificationCompat.Builder(this, "front_door_channel")
            .setContentTitle("Smart Security Running")
            .setContentText("this notification is for users entering room")
            .setSmallIcon(R.drawable.face_scan_icon)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .setOngoing(true)
            .build()

        startForeground(1, notification)
        listenToFirebaseParent("notifyApp")
        listenToFirebaseParent("successNotify")
        listenToFirebaseParent("unauthorizedAttempt")
        listenToFirebaseParent("doorAttempt")
        listenToFirebaseParent("notRegisteredRFID")
        listenToFirebaseParent("guestUnauthorizedBuzzer")
        listenToEnteringUser(referenceName = "registeredNamesCamera")
        listenToEnteringUser(referenceName = "registeredNames")
        return super.onStartCommand(intent, flags, startId)
    }

    private fun listenToEnteringUser(referenceName: String) {
        var logsState by mutableStateOf(Logs())

        realtimeDatabase = FirebaseDatabase.getInstance().getReference(referenceName)

        realtimeDatabase.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(
                parentSnapShot: DataSnapshot,
                previousChildName: String?
            ) {
                val nameOfEnteringFacility = parentSnapShot.key.toString()
                var initialChildCount = parentSnapShot.childrenCount
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
                        val currentDateTime = LocalDateTime.now()
                        val formattedDateTime = currentDateTime.format(
                            DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy - hh:mm a")
                        )
                        logsState = logsState.copy(
                            name = nameOfEnteringFacility,
                            timeInEntered = formattedDateTime,
                            timeOut = "",
                            isEntered = true
                        )
                        CoroutineScope(Dispatchers.IO).launch {
                            val isNameTappedIn = accessLog.isUserTappedIn(nameOfEnteringFacility).firstOrNull()
                            if (isNameTappedIn == null) {
                                accessLog.insertLog(logsState.toAccessLogs())
                            } else {
                                accessLog.updateLog(logsState.toAccessLogs())
                            }
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

    private fun showNotification(name: String) {
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
        notificationManager.notify(2, messageAboutFrontDoor)
    }

    private fun listenToFirebaseParent(ref: String) {
        realtimeDatabase = FirebaseDatabase.getInstance().getReference(ref)

        realtimeDatabase.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val initialChildCount = snapshot.childrenCount
                snapshot.ref.addChildEventListener(object : ChildEventListener {
                    var childCount = initialChildCount

                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                        if (childCount > 0) {
                            childCount--
                            return
                        }

                        if (ref == "notifyApp") {
                            notify(
                                contentTitle = "Tap in your card! \uD83E\uDEAA",
                                contentText = "It's ready to register your card"
                            )
                        } else if(ref == "successNotify") {
                            notify(
                                contentTitle = "Card registered successfully! \uD83E\uDEAA",
                                contentText = "It's ready to tap in now"
                            )
                        } else if (ref == "unauthorizedAttempt") {
                            notify(
                                contentTitle = "Someone trying to enter facility ⚠\uFE0F",
                                contentText = "unauthorized person at front door"
                            )
                        } else if (ref == "doorAttempt") {
                            notify(
                                contentTitle = "Someone trying to breach the door \uD83D\uDEAA",
                                contentText = "unauthorized person at front door"
                            )
                        } else if (ref == "notRegisteredRFID") {
                            notify(
                                contentTitle = "Someone tapped in unregistered card  ⚠\uFE0F",
                                contentText = "unauthorized person at front door"
                            )
                        } else if (ref == "guestUnauthorizedBuzzer") {
                            notify(
                                contentTitle = "Someone guest person at front door restricted area",
                                contentText = "guest person at front door"
                            )
                        }
                    }

                    override fun onChildChanged(
                        snapshot: DataSnapshot,
                        previousChildName: String?
                    ) {}
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

    private fun notify(
        contentTitle: String,
        contentText: String
    ) {
        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val messageAboutFrontDoor = NotificationCompat.Builder(this, "front_door_channel")
            .setSmallIcon(R.drawable.face_scan_icon)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(3, messageAboutFrontDoor)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

data class Logs(
    val name: String = "",
    val timeInEntered: String = "",
    val timeOut: String = "",
    val isEntered: Boolean = false
)

fun Logs.toAccessLogs(): AccessLogs = AccessLogs(
    name = name,
    timeInEntered = timeInEntered,
    timeOut = timeOut,
    isEntered = isEntered
)

