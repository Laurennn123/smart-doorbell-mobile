package com.example.mobileapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
                    BackAndUserButton(Modifier.fillMaxWidth().fillMaxSize().offset(y = 60.dp))
                    Settings(Modifier.fillMaxSize().padding(start = 25.dp, end = 25.dp).offset(y = -80.dp))
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
fun BackAndUserButton(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { },
            enabled = true,
            modifier = Modifier.padding(start = 5.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        }
        IconButton(
            onClick = { },
            enabled = true,
            modifier = Modifier.padding(end = 15.dp)
        ) {
            Icon(
                Icons.Filled.AccountCircle,
                contentDescription = null
            )
        }
    }
}


@Composable
fun SettingsSelect(type: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    FilledTonalButton(
        onClick = {  },
        modifier = modifier.fillMaxWidth().height(80.dp),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.5f))
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
//
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Test() {
    MobileAppTheme {
//        Greeting("Android App")
//        IconButtonExample()
//        Introduce("Lauren Andre David", "Web Developer")
//        Contacts("09521478934", "@Laurennn123", "meetmeat32@gmail.com")

        BackAndUserButton(Modifier.fillMaxWidth().fillMaxSize().offset(y = 60.dp))
        Settings(Modifier.fillMaxSize().padding(start = 25.dp, end = 25.dp).offset(y = -140.dp))
    }
}