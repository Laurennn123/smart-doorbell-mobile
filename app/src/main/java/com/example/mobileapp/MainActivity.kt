package com.example.mobileapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Test() {
    MobileAppTheme {



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

      