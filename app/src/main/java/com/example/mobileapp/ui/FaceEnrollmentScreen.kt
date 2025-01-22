package com.example.mobileapp.ui

import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mobileapp.R
import com.example.mobileapp.ui.components.IconAppBar



@Composable
fun FaceEnrollmentScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier,
    ) {
        Spacer(modifier = Modifier.height(36.dp))
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
        DeleteAndResetButtonPic(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = dimensionResource(R.dimen.padding_medium))
        )
        NameAndRelationshipField()
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
            icon = Icons.Filled.Refresh,
            onClick = { },
            contentDescription = "Refresh",
        )
    }
}

@Composable
private fun NameAndRelationshipField() {
    TextField(
        value = "",
        onValueChange = { },
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
        value = "",
        onValueChange = { },
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

