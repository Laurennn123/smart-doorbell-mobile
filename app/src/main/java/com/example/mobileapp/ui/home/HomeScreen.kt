package com.example.mobileapp.ui.home

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobileapp.HomeScreenAppBar
import com.example.mobileapp.R
import com.example.mobileapp.ui.AppViewModelProvider
import com.example.mobileapp.ui.components.BottomNavigationBar
import com.example.mobileapp.ui.components.SimpleButton
import com.example.mobileapp.ui.navigation.NavigationDestination
import com.example.mobileapp.ui.theme.MobileAppTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object HomeScreenDestination : NavigationDestination {
    override val route = "HomeScreen"
}

@Composable
fun HomeScreen(
    email: String,
    onClickSettings: () -> Unit,
    onClickAccount: () -> Unit,
    onClickBottomBar: (String) -> Unit,
    currentScreen: String,
    homeViewModel: HomeScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier) {

    val homeUiState by homeViewModel.homeUiState.collectAsState()
    homeViewModel.setFullName(email = email)

    Scaffold(
        topBar = { HomeScreenAppBar(
            onClickAccount = onClickAccount,
            onClickSettings = onClickSettings,
            nameOwner = homeUiState.fullNameOfCurrentUser)
        },
        bottomBar = {
            BottomNavigationBar(
                onClick = { onClickBottomBar(it) },
                currentScreen = currentScreen
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF77C89D), Color(0xFF006663)
                        )
                    )
                )
                .padding(innerPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = modifier,
            ) {
                LiveStream(modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
                    .height(800.dp))
                Actions(
                    onClickAlarm = { homeViewModel.sendMessageToESP32("~") },
                    onClickUnlock = {
                        homeViewModel.doorClick()
                        homeViewModel.sendMessageToESP32("!")
                    },
                    isUnlockDoorClick = homeUiState.isUnlockDoorClicked
                )
                MessagePanel(
                    messageValue = homeUiState.nameOfRegisteringForCard,
                    userMessage = { homeViewModel.handleChange(it) },
                    onClickClear = { homeViewModel.clearName() },
                    onClickEnter = { homeViewModel.sendMessageToESP32(homeUiState.nameOfRegisteringForCard) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFF77C89D), shape = MaterialTheme.shapes.medium)
                )
            }
        }
    }
}

@Composable
private fun RecentNotification(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.recent_notification),
        style = MaterialTheme.typography.displayMedium,
        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium))
    )
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = stringResource(id = R.string.recent_notification)
            )
            Text(
                text = stringResource(id = R.string.someone_on_frontdoor),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun LiveStream(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.live_view_stream),
        style = MaterialTheme.typography.displayMedium,
        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
    )
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ESP32VideoStream()
    }
}

@Composable
private fun Actions(
    onClickUnlock: () -> Unit,
    onClickAlarm: () -> Unit,
    isUnlockDoorClick: Boolean
) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = stringResource(id = R.string.actions),
        style = MaterialTheme.typography.displayMedium
    )
    Spacer(modifier = Modifier.height(8.dp))
    SimpleButton(
        onClick = onClickUnlock,
        nameOfButton = if (isUnlockDoorClick) stringResource(id = R.string.lock_door) else stringResource(id = R.string.unlock_door),
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge
    )
    Spacer(modifier = Modifier.height(8.dp))
    SimpleButton(
        onClick = onClickAlarm,
        nameOfButton = stringResource(id = R.string.alarm_trigger),
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge
    )
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
private fun MessagePanel(
    messageValue: String,
    userMessage: (String) -> Unit,
    onClickClear: () -> Unit,
    onClickEnter: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.register),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        )
        TextField(
            value = messageValue,
            onValueChange = userMessage,
            label = { Text(stringResource(id = R.string.enter_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(
                    vertical = dimensionResource(R.dimen.padding_small),
                    horizontal = dimensionResource(R.dimen.padding_extra_medium)
                ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
        )
        Row(
            modifier = Modifier
                .align(alignment = Alignment.End)
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            SimpleButton(
                onClick = onClickClear,
                nameOfButton = stringResource(id = R.string.clear),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.width(8.dp))
            SimpleButton(
                onClick = onClickEnter,
                nameOfButton = stringResource(id = R.string.enter),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun RecentVisitors(
    listOfUsers: MutableList<@Composable () -> Unit>,
    modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.recent_visitor),
        style = MaterialTheme.typography.displayMedium,
        modifier = modifier
    )
    LazyRow {
        items(listOfUsers.size) { users ->
            listOfUsers[users]()
        }
    }
}

@Composable
private fun VisitorCard(
    nameOfVisitors: String,
    isAuthorized: Boolean,
    modifier:Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
//        ImageContainer(
//            faceImage = R.drawable.thomas_si_boss,
//            imageSize = 64,
//            contentDescription = "bossing"
//        )
        Text(text = nameOfVisitors)
        if (isAuthorized) {
            Text(text = stringResource(R.string.authorized))
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ESP32VideoStream() {
    var cameraIpAddress by remember { mutableStateOf<String?>(null) }
    var webViewRef by remember { mutableStateOf<WebView?>(null) }

    LaunchedEffect(Unit) {
        val realtimeDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference("ipAddressCamera")

        realtimeDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    cameraIpAddress = child.child("updatedIp").getValue(String::class.java)
                    Log.d("camera", cameraIpAddress.toString())
                    break
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("camera", "Firebase error: ${error.message}")
            }
        })
    }

    DisposableEffect(Unit) {
        onDispose {
            webViewRef?.let {
                it.loadUrl("about:blank")
                it.stopLoading()
                it.clearHistory()
                it.removeAllViews()
                it.destroy()
                webViewRef = null
                Log.d("WebView", "Destroyed on dispose")
            }
        }
    }

    cameraIpAddress?.let { ipAddress ->

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
                    settings.userAgentString = "AndroidWebView"

                    settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            view?.evaluateJavascript(
                                """
                                (function() {
                                    document.body.style.height = "800px";
                                    document.body.style.overflow = "hidden";
                                    document.documentElement.style.overflow = "hidden";
                                })();
                                """.trimIndent(), null
                            )
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            super.onReceivedError(view, request, error)
                            view?.postDelayed({ view.reload() }, 2000)
                        }

                        override fun onReceivedHttpError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            errorResponse: WebResourceResponse?
                        ) {
                            super.onReceivedHttpError(view, request, errorResponse)
                            view?.postDelayed({ view.reload() }, 2000)
                        }
                    }


                    val headers = mapOf("User-Agent" to "AndroidWebView")
                    loadUrl("http://$ipAddress/", headers)

                    webViewRef = this
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun Test2() {
    MobileAppTheme {
//        HomeScreen(
//            modifier = Modifier.fillMaxWidth()
//        )
    }
}