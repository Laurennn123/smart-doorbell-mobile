package com.example.mobileapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mobileapp.ui.theme.MobileAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_NOTIFICATION_PERMISSIONS, 0
            )
        }
        enableEdgeToEdge()
        setContent {
            MobileAppTheme {
                val controller = remember {
                    LifecycleCameraController(applicationContext).apply {
                        setEnabledUseCases(
                            CameraController.IMAGE_CAPTURE or
                                    CameraController.VIDEO_CAPTURE
                        )
                    }
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    SmartEntryApp(context = applicationContext)
                }
            }
        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_NOTIFICATION_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object  {
        private val CAMERAX_NOTIFICATION_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.POST_NOTIFICATIONS,
        )
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ESP32VideoStream() {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(800.dp),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.cacheMode = WebSettings.LOAD_NO_CACHE
                settings.builtInZoomControls = false
                settings.displayZoomControls = false
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        view?.evaluateJavascript("""
                            (function() {
                                document.body.style.height = "800px";  
                                document.body.style.overflow = "hidden"; 
                                document.documentElement.style.overflow = "hidden";
                            })();
                        """.trimIndent(), null)
                    }
                }
                loadUrl("https://www.google.com/") // INSERT THE URL HERE OF THE ESP 32 CAM
            }
        },
    )
}