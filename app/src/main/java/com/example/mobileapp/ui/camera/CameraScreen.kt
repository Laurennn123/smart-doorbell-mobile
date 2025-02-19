package com.example.mobileapp.ui.camera

import android.graphics.Bitmap
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CameraPreviewScreen(
    modifier: Modifier = Modifier,
    controller: LifecycleCameraController,
    onClickPhoto: () -> Unit,
    navigateUp: () -> Unit,
    photoTaken: Bitmap?
    ) {

    Box(
        modifier = modifier
    ) {
        if(photoTaken == null) {
            CameraPreview(
                controller = controller,
                modifier = Modifier
                    .fillMaxSize()
            )
            controller.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            Image(
                bitmap = photoTaken.asImageBitmap(),
                contentDescription = "Authorized Face",
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        ) {
            IconButton(onClick = onClickPhoto) {
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Take a Photo",
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Take a Photo",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier
    )
}