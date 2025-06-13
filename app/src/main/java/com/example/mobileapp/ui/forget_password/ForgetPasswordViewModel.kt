package com.example.mobileapp.ui.forget_password

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgetPasswordViewModel() : ViewModel() {

    fun sendPasswordReset(email: String): String {
        var errorMessage = ""

        try {
            Firebase.auth.sendPasswordResetEmail(email)
        } catch (error: Exception) {
            errorMessage = error.message.toString()
        }

        return errorMessage
    }

}