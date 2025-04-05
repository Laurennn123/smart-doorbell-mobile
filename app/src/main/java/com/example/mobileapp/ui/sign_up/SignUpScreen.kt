package com.example.mobileapp.ui.sign_up

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobileapp.R
import com.example.mobileapp.ui.AppViewModelProvider
import com.example.mobileapp.ui.components.IconAppBar
import com.example.mobileapp.ui.components.SimpleButton
import com.example.mobileapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object SignUpDestination : NavigationDestination {
    override val route = "SignUp"
}

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navigateBack: (String) -> Unit,
    navigateToLogIn: () -> Unit,
    signUpViewModel: SignUpViewModel = viewModel(factory = AppViewModelProvider.Factory)) {

    val coroutineScope = rememberCoroutineScope()

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
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Text(
                text = stringResource(id = R.string.create_account),
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = dimensionResource(R.dimen.padding_medium))
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            ) {
                IdentityForm(
                    signUpViewModel = signUpViewModel,
                    onValueChange = signUpViewModel::updateUiState
                )
                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    onClick = {
                        signUpViewModel.signUpClick()

                        coroutineScope.launch {
                            if (signUpViewModel.signUpUiState.signUpDetails.password != signUpViewModel.signUpUiState.signUpDetails.reEnterPassword) {
                                signUpViewModel.updateErrorMessage("Not equal password")
                            } else {
                                signUpViewModel.updateErrorMessage("")
                                signUpViewModel.addAccountCloud()
                            }
                            if (signUpViewModel.errorMessage.isBlank()) {
                                signUpViewModel.addAccountCloudInformation()
                                signUpViewModel.addLocalAccount()
                                withContext(Dispatchers.Main) {
                                    signUpViewModel.signUpClick()
                                    navigateBack("")
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    signUpViewModel.signUpClick()
                                    navigateBack(signUpViewModel.errorMessage)
                                }
                            }
                        }
                    },
                    shape = MaterialTheme.shapes.extraLarge,
                    modifier = Modifier
                        .height(40.dp)
                        .width(110.dp),
                    enabled = signUpViewModel.signUpUiState.isEntryValid
                ) {
                    if (signUpViewModel.isSignUpClicked) {
                        CircularProgressIndicator(
                            modifier = Modifier.width(24.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.sign_up),
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
            SimpleButton(
                onClick = { navigateToLogIn() },
                nameOfButton = stringResource(R.string.login),
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .safeContentPadding()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IdentityForm(
    signUpViewModel: SignUpViewModel,
    onValueChange: (SignUpDetails) -> Unit = {}) {
    IdentityTextField(
        label = stringResource(R.string.full_name),
        displayTyped = signUpViewModel.signUpUiState.signUpDetails.fullName,
        userTyped = { onValueChange(signUpViewModel.signUpUiState.signUpDetails.copy(fullName = it)) },
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
    )
    IdentityTextField(
        label = stringResource(R.string.email),
        displayTyped = signUpViewModel.signUpUiState.signUpDetails.email,
        userTyped = { onValueChange(signUpViewModel.signUpUiState.signUpDetails.copy(email = it)) },
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
    )
    IdentityTextField(
        label = stringResource(R.string.password),
        displayTyped = signUpViewModel.signUpUiState.signUpDetails.password,
        userTyped = { onValueChange(signUpViewModel.signUpUiState.signUpDetails.copy(password = it)) },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        )
    )
    IdentityTextField(
        label = stringResource(R.string.re_enter_password),
        displayTyped = signUpViewModel.signUpUiState.signUpDetails.reEnterPassword,
        userTyped = { onValueChange(signUpViewModel.signUpUiState.signUpDetails.copy(reEnterPassword = it)) },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        )
    )
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
    ) {
        DatePickerDocked(
            showDatePicker = signUpViewModel.showDatePicker,
            onDismissRequest = {
                signUpViewModel.updateDate(it)
                signUpViewModel.onDismissCalendar()
            },
            datePicker = signUpViewModel::selectedDate,
            onClick = { signUpViewModel.clickedCalendarIcon() }
        )
        GenderPicker(
            gender = signUpViewModel.gender,
            showMenuPicker = signUpViewModel.showMenuPicker,
            iconOnClick = { signUpViewModel.clickedArrowDownIcon() },
            onDismissRequest = { signUpViewModel.onDismissGenderPicker() },
            onMaleClick = { signUpViewModel.updateGender("Male") },
            onFemaleClick = { signUpViewModel.updateGender("Female") }
        )
    }
}

@Composable
private fun IdentityTextField(
    label: String,
    displayTyped: String,
    userTyped: (String) -> Unit,
    visualTransformation: VisualTransformation,
    keyboardOptions: KeyboardOptions,
    isError: Boolean = false,
) {
    OutlinedTextField(
        value = displayTyped,
        onValueChange = userTyped,
        label = { Text(text = label) },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        ),
        singleLine = true,
        isError = isError,
        modifier = Modifier
            .height(70.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDocked(
    showDatePicker: Boolean,
    onDismissRequest: (String) -> Unit,
    datePicker: (DatePickerState) -> String,
    onClick: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePicker(datePickerState)

    Box(
        modifier = Modifier.padding(12.dp)
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text(stringResource(R.string.date_of_birth)) },
            readOnly = true,
            trailingIcon = {
                IconAppBar(
                    icon = Icons.Default.DateRange,
                    onClick = {
                        datePickerState.selectedDateMillis = null
                        onClick()
                    },
                    contentDescription = stringResource(R.string.select_date)
                )
            },
            modifier = Modifier
                .height(64.dp)
                .width(170.dp)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { onDismissRequest(selectedDate) },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = -40.dp)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            }
            if (selectedDate.isNotBlank()) {
                onDismissRequest(selectedDate)
            }
        }

    }
}

@Composable
private fun GenderPicker(
    gender: String,
    showMenuPicker: Boolean,
    iconOnClick: () -> Unit,
    onDismissRequest: () -> Unit,
    onMaleClick: () -> Unit,
    onFemaleClick: () -> Unit
) {

    Box(
        modifier = Modifier.padding(12.dp)
    ) {
        OutlinedTextField(
            value = gender,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = stringResource(R.string.gender))},
            trailingIcon = {
                IconAppBar(
                    icon = Icons.Default.KeyboardArrowDown,
                    onClick = iconOnClick,
                    contentDescription = stringResource(R.string.arrow_down)
                )
            },
            modifier = Modifier
                .height(64.dp)
                .width(140.dp)
        )
        DropdownMenu(
            expanded = showMenuPicker,
            onDismissRequest = onDismissRequest
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.male)) },
                onClick = onMaleClick
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.female)) },
                onClick = onFemaleClick
            )
        }
    }
}