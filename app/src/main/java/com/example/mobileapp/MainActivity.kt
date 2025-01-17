package com.example.mobileapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.ui.theme.MobileAppTheme

val produceEntries: @Composable () -> Unit = {

}

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    SmartDoorbellApp()
//                    val entries = EntriesHistory("Borils", true)
//
//                    BackAndUserButton(Modifier
//                        .fillMaxSize()
//                        .wrapContentSize(Alignment.TopStart)
//                        .fillMaxWidth()
//                        .offset(y = 60.dp)
//                        .padding(start = 5.dp, end = 15.dp)
//                    )
//                    entries.EntriesText(
//                        modifier = Modifier
//                            .offset(x = 24.dp, y = 120.dp)
//                    )



                    // draft Settings ui but not interactive when its clicked
//                    BackAndUserButton(Modifier.fillMaxWidth().fillMaxSize().offset(y = 60.dp))
//                    Settings(Modifier.fillMaxSize().padding(start = 25.dp, end = 25.dp).offset(y = -80.dp))

                    // draft ui in Home page
//                    HeaderHomePage("Borilla", Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart).fillMaxWidth().padding(start = 15.dp, end = 15.dp).offset(y = 60.dp))
//                    BodyHomePage(Modifier.fillMaxSize().wrapContentSize(Alignment.CenterStart).padding(start = 15.dp, end = 15.dp).offset(y = -100.dp))
                }
            }
        }
    }
}

// starts here the customizing of the settings
@Composable
fun IconButtonIcon(modifier: Modifier = Modifier, icon: ImageVector, iconModifier: Modifier = Modifier, onClick: @Composable () -> Unit) {

    val composableEntries = remember { mutableStateListOf<@Composable () -> Unit>() }
    val authorizeNames = listOf("Borilla", "David", "Dion", "Harris")
    var index by remember { mutableIntStateOf(0) }
    val entries = EntriesHistory(authorizeNames[index], true)

    IconButton(
        modifier = modifier,
        onClick = {
            composableEntries.add {
                entries.EntriesInformation(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                        .fillMaxWidth()
                        .padding(top = 40.dp, end = 15.dp)
                )
            }
            if (index < authorizeNames.size - 1) {
                index++
            }
        }
    ) {
        Icon(
            modifier = iconModifier,
            imageVector = icon,
            contentDescription = null,
        )
    }

    // so need is to use the lazy column
    LazyColumn(
        modifier = Modifier
            .offset(x = -20.dp, y = 50.dp)
    ) {
        items(composableEntries.size) { size ->
            composableEntries[size]()
        }
    }

}

//@Composable
//fun BackAndUserButton(modifier: Modifier = Modifier) {
//    Row(
//        modifier = modifier,
//    ) {
//        IconButtonIcon(icon = Icons.AutoMirrored.Filled.ArrowBack, onClick = { })
//        IconButtonIcon(modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.End), icon = Icons.Filled.AccountCircle, onClick = { })
//    }
//}

// starts of home page
@Composable
fun HeaderHomePage(nameOfOwner: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = "Welcome back, $nameOfOwner", fontWeight = FontWeight.Bold)
            Text(text = "Check your front door!", fontWeight = FontWeight.Bold)
        }
        Row {
            IconButtonIcon(icon = Icons.Filled.Menu,  modifier = Modifier.offset(x = 15.dp), onClick = {""})
            IconButtonIcon(icon = Icons.Filled.AccountCircle, onClick = { ""})
        }
    }
}

@Composable
fun BodyHomePage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Column {
            Text(text = "Recent Notification", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Button(
                modifier = Modifier.height(60.dp),
                shape = RoundedCornerShape(20.dp),
                onClick = { }
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        Icons.Filled.Notifications,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(start = 10.dp, top = 3.dp),
                        text = "Someone is at your front door !",
                        fontSize = 15.sp
                    )
                }
            }
        }

        Column(
            Modifier.padding(top = 8.dp)
        ){
            Text(text = "Camera", fontWeight = FontWeight.Bold)
            Box(
                modifier = Modifier.fillMaxWidth().background(Color.Gray, shape = RoundedCornerShape(20.dp)).height(90.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { }) {
                    Text(text = "Live View")
                }
            }
        }

        Column(
            Modifier.padding(top = 8.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Actions", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Button(onClick = { }) {
                Row(
                    Modifier.fillMaxWidth().padding(start = 10.dp),
                    horizontalArrangement = Arrangement.Start
                ) { Text(text = "Unlock Door") }
            }
            Button(onClick = { }) {
                Row(
                    Modifier.fillMaxWidth().padding(start = 10.dp),
                    horizontalArrangement = Arrangement.Start
                ) { Text(text = "Alarm Trigger") }
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth().background(Color.Gray, shape = RoundedCornerShape(20.dp)).height(130.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(modifier = Modifier.align(Alignment.Start).offset(x = 15.dp).padding(top = 5.dp, bottom = 5.dp), text = "Message Panel", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                var inputText by remember { mutableStateOf("") }
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    modifier = Modifier.fillMaxWidth().height(50.dp).padding(start = 35.dp, end = 35.dp)
                )
                Row(
                    modifier = Modifier.align(alignment = Alignment.End).offset(x = -32.dp).padding(top = 5.dp,  bottom = 5.dp)
                ) {
                    Button(onClick = { inputText = "" }, modifier = Modifier.height(29.dp)) {
                        Text("Clear", fontSize = 9.sp, modifier = Modifier.offset(y = -4.dp))
                    }
                    Button(onClick = { inputText = "Hi" }, modifier = Modifier.height(29.dp).padding(start = 10.dp)) {
                        Text("Enter", fontSize = 9.sp, modifier = Modifier.offset(y = -4.dp))
                    }
                }
            }
        }

    }
}

// create a function for icon button and icon

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Test() {
    MobileAppTheme {
        SmartDoorbellApp()

//        entries.EntriesInformation(
//            modifier = Modifier
//                .fillMaxSize()
//                .wrapContentSize(Alignment.TopStart)
//                .fillMaxWidth()
//                .padding(start = 30.dp, end = 25.dp)
//                .offset(y = 150.dp)
//        )
        // by 90 of adding


        // draft Settings ui but don't have function when its clicked
//        BackAndUserButton(Modifier.fillMaxWidth().fillMaxSize().offset(y = 60.dp))
//        Settings(Modifier.fillMaxSize().padding(start = 25.dp, end = 25.dp).offset(y = -30.dp))

        // draft Home Page ui but don't have function when its clicked
        // HeaderHomePage("Borilla", Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart).fillMaxWidth().padding(start = 15.dp, end = 15.dp).offset(y = 50.dp))
        // BodyHomePage(Modifier.fillMaxSize().wrapContentSize(Alignment.CenterStart).padding(start = 15.dp, end = 15.dp).offset(y = -80.dp))
//        val homePage = HomePage()
//        homePage.Header("Borilla", Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart).fillMaxWidth().padding(start = 15.dp, end = 15.dp).offset(y = 50.dp))
//        homePage.Body(Modifier.fillMaxSize().wrapContentSize(Alignment.CenterStart).padding(start = 15.dp, end = 15.dp).offset(y = -80.dp))
    }
}

      