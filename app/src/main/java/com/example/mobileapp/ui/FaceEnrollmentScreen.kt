package com.example.mobileapp.ui

import android.graphics.Bitmap
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mobileapp.R
import com.example.mobileapp.ui.components.IconAppBar
import com.example.mobileapp.ui.components.SimpleButton
import com.example.mobileapp.ui.face_enrollment.FaceEnrollmentViewModel
import com.example.mobileapp.ui.face_enrollment.PersonDetails


@Composable
fun FaceEnrollmentScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    photoTaken: Bitmap?,
    addPerson: () -> Unit,
    name: String,
    relationship: String,
    faceEnrollment: FaceEnrollmentViewModel,
    onType: (PersonDetails) -> Unit,
) {

    Column(
        modifier = modifier,
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        // i will have condition here if the bitmap is not null then place it
        if(photoTaken == null) {
            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_extra_small_medium)),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    painter = painterResource(R.drawable.face_scan_icon),
                    contentDescription = "Camera",
                    modifier = Modifier
                        .size(100.dp)
                )
            }
        } else {
            Image(
                bitmap = photoTaken.asImageBitmap(),
                contentDescription = "Authorized Face",
                modifier = Modifier
                    .size(300.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .align(Alignment.CenterHorizontally)
            )
        }
        DeleteAndResetButtonPic(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = dimensionResource(R.dimen.padding_medium))
        )
        NameAndRelationshipField(
            name = name,
            relationship = relationship,
            nameOnType = { onType(faceEnrollment.faceUiState.authorizedPerson.copy(name = it)) },
            relationshipOnType = { onType(faceEnrollment.faceUiState.authorizedPerson.copy(relationship = it)) },
        )
        Spacer(modifier = Modifier.height(15.dp))
        SimpleButton(
            onClick = addPerson,
            nameOfButton = "Add Person",
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}


@Composable
private fun DeleteAndResetButtonPic(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = modifier
    ) {
        IconAppBar(
            icon = Icons.Filled.Delete,
            onClick = { },
            contentDescription = "Delete",
        )
        IconAppBar(
            icon = Icons.Filled.Autorenew,
            onClick = { },
            contentDescription = "Refresh",
        )
    }
}

@Composable
private fun NameAndRelationshipField(
    name: String,
    relationship: String,
    nameOnType: (String) -> Unit,
    relationshipOnType: (String) -> Unit,
) {
    TextField(
        value = name,
        onValueChange = { nameOnType(it) },
        label = { Text("Enter Name:") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_extra_medium)),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextField(
        value = relationship,
        onValueChange = { relationshipOnType(it) },
        label = { Text("Relationship (Optional):") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_extra_medium)),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        )
    )
}

