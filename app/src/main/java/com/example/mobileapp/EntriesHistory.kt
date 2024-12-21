package com.example.mobileapp

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date

class EntriesHistory(name: String, isAuthorized: Boolean) {
    private val nameEntry = name
    private val authorizedOrNot = if (isAuthorized) {
        "Authorized"
    } else {
        "Unknown"
    }

    @Composable
    fun EntriesText(modifier: Modifier = Modifier) {
        Text(
            modifier = modifier,
            text = "Entries History",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }

    @SuppressLint("SimpleDateFormat")
    @Composable
    fun EntriesInformation(modifier: Modifier = Modifier) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .background(Color.Gray)
                    .width(60.dp)
                    .height(60.dp),
                painter = painterResource(R.drawable.circle),
                contentDescription = "Face of Entry"
            )
            Spacer(Modifier.width(25.dp))
            Row(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .background(Color.Cyan, RoundedCornerShape(16.dp))
                    .padding(start = 5.dp, end = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val formaterDate = SimpleDateFormat("EEE, MMM. d, yyyy")
                val formaterTime = SimpleDateFormat("h:mm a")
                val currentDate = formaterDate.format(Date())
                val currentTime = formaterTime.format(Date())
                Column(
                    modifier = Modifier.padding(start = 5.dp)
                ) {
                    Text(
                        modifier = Modifier.offset(y = 5.dp),
                        text = nameEntry,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold)
                    Text(
                        modifier = Modifier.offset(y = -5.dp),
                        text = authorizedOrNot,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold)
                }
                // soon will create a utils for Text to replace the same attributes
                Column(
                    modifier = Modifier.padding(end = 5.dp)
                ) {
                    Text(
                        modifier = Modifier.offset(y = 5.dp),
                        text = currentDate,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold)
                    Text(
                        modifier = Modifier.align(Alignment.End).offset(y = -5.dp),
                        text = currentTime,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

}