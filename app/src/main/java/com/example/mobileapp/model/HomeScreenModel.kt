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
import java.io.IOException
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

    @SuppressLint("MissingPermission")
    fun connectToESP32(device: BluetoothDevice): BluetoothSocket? {
        return try {
            val SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
            val socket = device.createRfcommSocketToServiceRecord(SPP_UUID)
            socket.connect()
            socket
        } catch (e: IOException) {
            Log.e("Bluetooth", "Connection failed", e)
            null
        }
    }

    fun sendTextToESP32(socket: BluetoothSocket?, message: String) {
        socket?.outputStream?.write(message.toByteArray())
    }



}