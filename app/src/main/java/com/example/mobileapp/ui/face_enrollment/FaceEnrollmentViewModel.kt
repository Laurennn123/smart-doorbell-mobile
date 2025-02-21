package com.example.mobileapp.ui.face_enrollment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class FaceEnrollmentViewModel : ViewModel() {
    var faceUiState by mutableStateOf(FaceEnrollmentUiState())
        private set

    fun updatePersonDetails(personDetails: PersonDetails) {
        faceUiState = FaceEnrollmentUiState(authorizedPerson = personDetails)
    }

    fun reset(personDetails: PersonDetails = PersonDetails()) {
        faceUiState = FaceEnrollmentUiState(authorizedPerson = personDetails.copy(name = "", relationship = ""))
    }

}

data class FaceEnrollmentUiState(
    val authorizedPerson: PersonDetails = PersonDetails()
)

data class PersonDetails(
    val name: String = "",
    val relationship: String = ""
)