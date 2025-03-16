package com.example.mobileapp.model

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mobileapp.data.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.io.IOException
import java.net.URI
import java.util.UUID

class HomeScreenModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    var userMessage by mutableStateOf("")
        private set

    var index by mutableStateOf(0)

    fun updateMessage(userTyped: String) {
        userMessage = userTyped
    }

    fun clearMessage() {
        userMessage = ""
    }

    var webSocket: WebSocketClient? = null

    fun connectToESP32() {
        val uri = URI("ws://192.168.1.14:81") // Replace with ESP32 IP
        webSocket = object : WebSocketClient(uri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                println("Connected to ESP32")
            }
            override fun onMessage(message: String?) {}
            override fun onClose(code: Int, reason: String?, remote: Boolean) {}
            override fun onError(ex: Exception?) {}
        }
        webSocket?.connect()
    }

    fun sendTextToESP32(text: String) {
        if (webSocket == null || !webSocket!!.isOpen) {
            println("WebSocket is not connected! Trying to connect...")
            connectToESP32()
            Thread.sleep(3000) // Wait 3 seconds for connection
        }
        if (webSocket!!.isOpen) {
            webSocket?.send(text)
            println("Sent: $text")
        } else {
            println("WebSocket failed to connect!")
        }
    }



}