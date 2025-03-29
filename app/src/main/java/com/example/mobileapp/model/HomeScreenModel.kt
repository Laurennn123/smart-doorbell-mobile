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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.io.IOException
import java.net.Socket
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


    fun sendMessageToESP32(message: String  ) { 
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val socket = Socket("192.168.250.160", 80)
                val writer = socket.getOutputStream().bufferedWriter()
                writer.write(message + "\n")
                writer.flush()
                writer.close()
                socket.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



}