package com.example.mobileapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mobileapp.R
import com.example.mobileapp.model.EntriesHistory
import com.example.mobileapp.ui.components.ImageContainer
import com.example.mobileapp.ui.theme.MobileAppTheme

@Composable
fun EntriesHistoryScreen(
    listOfEntries: List<EntriesHistory>,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.entries_history),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(listOfEntries.size) { entries ->
                EntriesCard(
                    entriesPerson = listOfEntries[entries],
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(R.dimen.padding_medium),
                        vertical = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
        }
    }
}

@Composable
private fun EntriesCard(
    entriesPerson: EntriesHistory,
    modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        ImageContainer(
            faceImage = entriesPerson.faceImage,
            imageSize = 74,
            contentDescription = "bossing"
        )
        Spacer(Modifier.width(25.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_medium),
                        vertical = dimensionResource(R.dimen.padding_medium))
            ) {
                Column {
                    Text( text = entriesPerson.name )
                    Text( text = "Authorized" )
                }
                Column {
                    Text( text = entriesPerson.date )
                    Text(
                        text = entriesPerson.time,
                        modifier = Modifier.align(alignment = Alignment.End)
                    )
                }
            }
        }
    }
}