package com.example.mobileapp.ui.account

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobileapp.BackAndUserAppBar
import com.example.mobileapp.R
import com.example.mobileapp.ui.AppViewModelProvider
import com.example.mobileapp.ui.components.DefaultIcon
import com.example.mobileapp.ui.components.IconAppBar
import com.example.mobileapp.ui.components.ImageContainer
import com.example.mobileapp.ui.navigation.NavigationDestination
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object MyAccountScreenDestination: NavigationDestination {
    override val route = "My Account"
}

@Composable
fun MyAccountScreen(
    modifier: Modifier = Modifier,
    userName: String,
    email: String,
    address: String,
    birthDate: String,
    contactNumber: String,
    editClick: () -> Unit,
    addressClick: () -> Unit,
    contactClick: () -> Unit,
    dateClick: () -> Unit,
    changePassClick: () -> Unit,
    navigateUp: () -> Unit,
    myAccountViewModel: MyAccountViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    Scaffold(
        topBar = { BackAndUserAppBar(
            navigateUp = navigateUp,
            currentDestination = "My Account",
            onClickAccount = {}
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
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            ) {
                Text(text = stringResource(id = R.string.my_account),
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.align(alignment = Alignment.Start)
                )
                Spacer(modifier = Modifier.height(30.dp))
                UserProfile(
                    userName = userName,
                    email = email,
                    editClick = editClick
                )
                Spacer(modifier = Modifier.height(20.dp))
                UserDetails(
                    address = address,
                    contactNumber = contactNumber,
                    date = birthDate,
                    addressClick = addressClick,
                    contactClick = contactClick,
                    dateClick = dateClick,
                    changePassClick = changePassClick
                )
            }
        }
    }
}

@Composable
private fun UserProfile(
    userName: String,
    email: String,
    editClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        ImageContainer(
            faceImage = R.drawable.thomas_si_boss,
            imageSize = 150,
            contentDescription = "Bossing"
        )
        Spacer(modifier = Modifier.width(30.dp))
        Column {
            Row {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.displayLarge,
                    maxLines = 11
                )
                IconAppBar(
                    icon = Icons.Default.Edit,
                    onClick = editClick,
                    contentDescription = "Editing Username",
                    modifier = Modifier
                        .offset(x = -10.dp,  y = -25.dp)
                )
            }
            Text(
                text = stringResource(id = R.string.user_email, email),
            )
        }
    }
}

@Composable
private fun UserDetails(
    address: String = "Address",
    contactNumber: String = "Contact Number",
    date: String = "Birth Date",
    addressClick: () -> Unit,
    contactClick: () -> Unit,
    dateClick: () -> Unit,
    changePassClick: () -> Unit,
) {
    UserIdentityButton(
        nameOfButton = address,
        icon = Icons.Outlined.LocationOn,
        onClick = addressClick
    )
    UserIdentityButton(
        nameOfButton = contactNumber,
        icon = Icons.Outlined.Call,
        onClick = contactClick
    )
    UserIdentityButton(
        nameOfButton = date,
        icon = Icons.Outlined.CalendarToday,
        onClick = dateClick
    )
    UserIdentityButton(
        nameOfButton = stringResource(R.string.change_password),
        icon = Icons.Outlined.Lock,
        onClick = changePassClick
    )
}

@Composable
private fun UserIdentityButton(
    nameOfButton: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .padding(vertical = dimensionResource(R.dimen.padding_small))
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultIcon(
                icon = icon,
                nameOfIcon = nameOfButton,
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = nameOfButton,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun InputDialog(
    typeOfInput: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    onDismissRequest: () -> Unit,
    nameOfInput: String,
    valueInputted: String,
    onValueChange: (String) -> Unit,
    cancelClick: () -> Unit,
    confirmClick: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium,
        ) {
            Column {
                Text(
                    text = typeOfInput,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    style = MaterialTheme.typography.displayMedium
                )
                TextField(
                    value = valueInputted,
                    onValueChange = onValueChange,
                    label = { Text(text = nameOfInput ) },
                    keyboardOptions = keyboardOptions,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    ),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = cancelClick,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                    ) {
                        Text(text = "Cancel")
                    }
                    TextButton(
                        onClick = confirmClick,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                    ) {
                        Text(text = "Confirm")
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDatePicker(
    onDismissRequest: () -> Unit
): String {
    val datePickerState = rememberDatePickerState()

    Popup(
        onDismissRequest = { onDismissRequest() },
        alignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = -10.dp)
                .padding(16.dp)
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
    }

    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return datePickerState.selectedDateMillis?.let {
        formatter.format(Date(it))
    } ?: ""
}