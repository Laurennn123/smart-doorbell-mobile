package com.example.mobileapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.data.repo.AccountRepository
import com.example.mobileapp.ui.UserHandler
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.Socket

class HomeScreenViewModel(private val accountRepository: AccountRepository) : ViewModel(), UserHandler<String> {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    fun setFullName(email: String) {
        viewModelScope.launch {
            val fullName = accountRepository.getName(email).first()
            withContext(Dispatchers.Main) {
                _homeUiState.update { currentState ->
                    currentState.copy(fullNameOfCurrentUser = fullName)
                }
            }
        }
    }

    override fun handleChange(name: String) {
        _homeUiState.update { currentState -> currentState.copy(nameOfRegisteringForCard = name) }
    }

    fun clearName() {
        _homeUiState.update { currentState -> currentState.copy(nameOfRegisteringForCard = "") }
    }

    fun doorClick() {
        _homeUiState.update { currentState ->
            currentState.copy(isUnlockDoorClicked = !_homeUiState.value.isUnlockDoorClicked)
        }
    }

    fun sendMessageToESP32(message: String) {
        val ipRef = FirebaseDatabase.getInstance().getReference("ipAddress").child("IP")
        if (message !== "~" || message !== "!") {
            _homeUiState.update { currentState -> currentState.copy(nameOfRegisteringForCard = "") }
        }

        ipRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val ipValue = snapshot.child("updatedIp").getValue(String::class.java)

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val socket = Socket(ipValue, 80)
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

            override fun onCancelled(error: DatabaseError) {
                Log.e("databasedb", "Error fetching IP: ${error.message}")
            }

        })

    }

}