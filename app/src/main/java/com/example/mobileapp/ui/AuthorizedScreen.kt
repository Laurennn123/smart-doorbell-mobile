package com.example.mobileapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobileapp.R
import com.example.mobileapp.model.AuthorizedPerson
import com.example.mobileapp.model.AuthorizedPersonModel
import com.example.mobileapp.ui.theme.MobileAppTheme

@Composable
fun AuthorizedScreen(
    listOfAllAuthorized: List<AuthorizedPerson>,
    modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.all_authorized_person),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(4)
        ) {
            items(listOfAllAuthorized.size) { person ->
                AuthorizedPersonTemplate(
                    authorizedPerson = listOfAllAuthorized[person],
                    modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium))
                )
            }
        }
    }
}

@Composable
private fun AuthorizedPersonTemplate(
    authorizedPerson: AuthorizedPerson,
    modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ){
        authorizedPerson.faceImage?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = authorizedPerson.name,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
                    .size(70.dp),
                contentScale = ContentScale.Crop,
            )
        }
        Text(text = authorizedPerson.name)
        Text(text = authorizedPerson.relationship)
    }
}