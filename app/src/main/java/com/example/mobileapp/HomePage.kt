package com.example.mobileapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class HomePage {

    @Composable
    fun IconButtonIcon(
        modifier: Modifier = Modifier,
        icon: ImageVector,
        iconModifier: Modifier = Modifier,
        onClick: () -> Unit) {
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

    // starts of home page
    @Composable
    fun Header(nameOfOwner: String, modifier: Modifier = Modifier) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Welcome back, $nameOfOwner", fontWeight = FontWeight.Bold)
                Text(text = "Check your front door!", fontWeight = FontWeight.Bold)
            }
            Row {
                IconButtonIcon(icon = Icons.Filled.Menu,  modifier = Modifier.offset(x = 15.dp), onClick = { })
                IconButtonIcon(icon = Icons.Filled.AccountCircle, onClick = { })
            }
        }
    }

    @Composable
    fun Body(modifier: Modifier = Modifier) {
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

}