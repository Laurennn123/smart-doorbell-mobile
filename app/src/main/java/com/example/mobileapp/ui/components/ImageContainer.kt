package com.example.mobileapp.ui.components

import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.OpenDocument
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mobileapp.R
import androidx.core.net.toUri

@Composable
fun ImageContainer(
    @DrawableRes faceImage: Int = R.drawable.thomas_si_boss,
    uri: String, // ito yung sa database
    updatePic: (String) -> Unit,
    imageSize: Int,
    contentDescription: String) {

    val imageDataBase = uri.toUri()
    val context = LocalContext.current

    LaunchedEffect(imageDataBase) {
        imageDataBase.let {
            try {
                val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(it, flags)
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    val pickMedia = rememberLauncherForActivityResult(OpenDocument()) { uriCurrent ->
        uriCurrent?.let {
            val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(it, flags)
            if (true) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                // call, once na pumindot
                updatePic(uriCurrent.toString())
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    }

    if (uri == "") {
        Image(
            painter = painterResource(R.drawable.empty_profile),
            modifier = Modifier
                .size(imageSize.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .clickable {
                    pickMedia.launch(arrayOf("image/*"))
                },
            contentScale = ContentScale.Crop,
            contentDescription = contentDescription
        )
    } else {
        val profilePic = remember(imageDataBase) {
            try {
                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(context.contentResolver, imageDataBase)
                ).asImageBitmap()
            } catch (e: Exception) {
                null
            }
        }
        if (profilePic != null) {
            Image(
                bitmap = profilePic,
                modifier = Modifier
                    .size(imageSize.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .clickable {
                        pickMedia.launch(arrayOf("image/*"))
                    },
                contentScale = ContentScale.Crop,
                contentDescription = contentDescription
            )
        }
    }
}