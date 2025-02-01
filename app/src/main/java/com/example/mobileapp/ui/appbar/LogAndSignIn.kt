package com.example.mobileapp.ui.appbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mobileapp.R
import com.example.mobileapp.ui.components.SimpleButton

@Composable
fun LogAndSignInBottomBar(modifier: Modifier = Modifier) {
    BottomAppBar(
        contentColor = contentColorFor(backgroundColor = Color.Transparent),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            SimpleButton(
                onClick = {},
                nameOfButton = stringResource(R.string.sign_in),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .fillMaxWidth()
            )
            SimpleButton(
                onClick = {},
                nameOfButton = stringResource(R.string.sign_up),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .fillMaxWidth()
            )
        }

    }
}