package com.example.mobileapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.ui.theme.MobileAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xfff5efe7)  //for setting the color of background
                ) {
                    // draft Settings ui but not interactive when its clicked
//                    BackAndUserButton(Modifier.fillMaxWidth().fillMaxSize().offset(y = 60.dp))
//                    Settings(Modifier.fillMaxSize().padding(start = 25.dp, end = 25.dp).offset(y = -80.dp))

                    // draft ui in Home page
                    HeaderHomePage("Borilla", Modifier.fillMaxSize().fillMaxWidth().padding(start = 15.dp, end = 15.dp).offset(y = 55.dp))
                    BodyHomePage(Modifier.fillMaxSize().padding(start = 15.dp, end = 15.dp).offset(y = 120.dp))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "",
        modifier = modifier
    )
    // using the Icon it needs to import the androidx.compose.material3.Icon
    Icon(
        Icons.Rounded.Done,
        contentDescription = null
    )
}

@Composable
fun FilledButtonExample(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {

        FilledTonalButton(
            onClick = { /* Do something */ },
            modifier = Modifier.offset(x = 100.dp, y = 200.dp).width(170.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(
                Icons.Filled.Done,
                contentDescription = null,
                Modifier.padding(end = 20.dp).size(48.dp)
            )
            Text("Click Me")
        }
    }
}

@Composable
fun IconButtonExample() {
    IconButton(
        onClick = { /* Perform an action */ },
        enabled = true,
        modifier = Modifier.offset(100.dp, 200.dp)
    ) {
        Icon(
            Icons.Filled.Done,
            contentDescription = null,
        )
    }
}


@Composable
fun Introduce(name: String, role: String, modifier: Modifier = Modifier) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.web),
            contentDescription = null,
        )
        Text(
            text = name,
            fontSize = 30.sp,
            fontWeight = FontWeight.W300,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Text(
            text = role,
            fontSize = 15.sp
        )
    }
}

@Composable
fun ContactInfo(details: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Row(
        Modifier
            .width(250.dp)
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Text(
            text = details,
            Modifier.padding(start = 30.dp),
        )
    }
}

@Composable
fun Contacts(contactNum: String, username: String, email: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .absoluteOffset(y = -50.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContactInfo(contactNum, Icons.Filled.Call)
        ContactInfo(username, Icons.Filled.Share)
        ContactInfo(email, Icons.Filled.Email)
    }
}

// starts here the customizing of the settings
@Composable
fun IconButtonIcon(modifier: Modifier = Modifier, icon: ImageVector, iconModifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(
        modifier = modifier,
        onClick = { onClick }
    ) {
        Icon(
            modifier = iconModifier,
            imageVector = icon,
            contentDescription = null,
        )
    }
}

@Composable
fun BackAndUserButton(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButtonIcon(modifier = Modifier.padding(start = 5.dp), icon = Icons.AutoMirrored.Filled.ArrowBack, onClick = {})
        IconButtonIcon(modifier = Modifier.padding(end = 15.dp), icon = Icons.Filled.AccountCircle, onClick = {})
    }
}

@Composable
fun SettingsSelect(type: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    FilledTonalButton(
        onClick = {  },
        modifier = modifier.fillMaxWidth().height(80.dp),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(alpha = 0.5f))
    ) {
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = type,
                modifier = Modifier.padding(start = 10.dp,top = 5.dp)
            )
        }
    }

    // soon when tapping different button
//    when (type) {
//        "My Account" -> println("Account")
//        "Face Enrollment" -> println("Enrollment")
//        "Entries History" -> println("History")
//        "General Settings" -> println("Settings")
//        "About Us" -> println("Information")
//        "Log Out" -> println("exit")
//    }

}

@Composable
fun Settings(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Settings",
            fontSize = 35.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        SettingsSelect("My Account", Icons.Filled.AccountCircle, Modifier.padding(top = 10.dp) ,onClick = {})
        SettingsSelect("Face Enrollment", Icons.Filled.Face, Modifier.padding(top = 10.dp) ,onClick = {})
        SettingsSelect("Entries History", Icons.Filled.Lock, Modifier.padding(top = 10.dp) ,onClick = {})
        SettingsSelect("General Settings", Icons.Filled.Settings, Modifier.padding(top = 10.dp) ,onClick = {})
        SettingsSelect("About Us", Icons.Filled.Info, Modifier.padding(top = 10.dp) ,onClick = {})
        SettingsSelect("Log Out", Icons.Filled.Close, Modifier.padding(top = 10.dp) ,onClick = {})
    }
}

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
            IconButtonIcon(icon = Icons.Filled.Menu,  modifier = Modifier.offset(x = 15.dp), onClick = {})
            IconButtonIcon(icon = Icons.Filled.AccountCircle, onClick = {})
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
                var text by remember { mutableStateOf("") }
                TextField(
                    value = text,
                    onValueChange = { text = it},
                    modifier = Modifier.fillMaxWidth().height(50.dp).padding(start = 35.dp, end = 35.dp)
                )
                Row(
                    modifier = Modifier.align(alignment = Alignment.End).offset(x = -32.dp).padding(top = 5.dp,  bottom = 5.dp)
                ) {
                    Button(onClick = { }, modifier = Modifier.height(29.dp)) {
                        Text("Clear", fontSize = 9.sp, modifier = Modifier.offset(y = -4.dp))
                    }
                    Button(onClick = { }, modifier = Modifier.height(29.dp).padding(start = 10.dp)) {
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
//        Greeting("Android App")
//        IconButtonExample()
//        Introduce("Lauren Andre David", "Web Developer")
//        Contacts("09521478934", "@Laurennn123", "meetmeat32@gmail.com")


        // draft Settings ui but don't have function when its clicked
//        BackAndUserButton(Modifier.fillMaxWidth().fillMaxSize().offset(y = 60.dp))
//        Settings(Modifier.fillMaxSize().padding(start = 25.dp, end = 25.dp).offset(y = -30.dp))

        // draft Home Page ui but don't have function when its clicked
        HeaderHomePage("Borilla", Modifier.fillMaxSize().fillMaxWidth().padding(start = 15.dp, end = 15.dp).offset(y = 55.dp))
        BodyHomePage(Modifier.fillMaxSize().padding(start = 15.dp, end = 15.dp).offset(y = 120.dp))
    }
}