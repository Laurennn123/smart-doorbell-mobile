package com.example.mobileapp.ui.instructional_manual

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mobileapp.BackAndUserAppBar
import com.example.mobileapp.R
import com.example.mobileapp.ui.navigation.NavigationDestination

object InstructionalDestination : NavigationDestination {
    override val route = "Instructional Manual"
}

@Composable
fun InstructionalManualScreen(
    navigateUp: () -> Unit,
    onClickAccount: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { BackAndUserAppBar(
            navigateUp = navigateUp,
            currentDestination = "Instructional Manual",
            onClickAccount = onClickAccount
        ) }
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
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.instructional_manual),
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = "Scan the QR Code below to direct for Instructional Manual",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(130.dp))
                Image(
                    painter = painterResource(R.drawable.instructional_manual),
                    contentDescription = stringResource(R.string.instructional_manual),
                    modifier = Modifier
                        .size(230.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}