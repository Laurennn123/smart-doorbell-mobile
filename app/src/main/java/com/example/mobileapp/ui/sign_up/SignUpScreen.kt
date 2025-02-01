package com.example.mobileapp.ui.sign_up

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DatePicker
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobileapp.R
import com.example.mobileapp.ui.components.IconAppBar
import com.example.mobileapp.ui.components.SimpleButton

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    signUpViewModel: SignUpViewModel = viewModel()) {
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
                signUpUiState = signUpViewModel.signUpUiState,
                signUpViewModel = signUpViewModel,
                onValueChange = signUpViewModel::updateUiState
            )
            Spacer(modifier = Modifier.height(60.dp))
            SimpleButton(
                onClick = {},
                nameOfButton = stringResource(R.string.sign_up).uppercase(),
                shape = MaterialTheme.shapes.extraLarge
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
        SimpleButton(
            onClick = {},
            nameOfButton = stringResource(R.string.login).uppercase(),
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF77C89D))
        )
    }
}

@Composable
private fun IdentityForm(
    signUpUiState: SignUpUiState,
    signUpViewModel: SignUpViewModel,
    onValueChange: (SignUpDetails) -> Unit = {}) {
    IdentityTextField(
        label = stringResource(R.string.full_name),
        displayTyped = signUpUiState.signUpDetails.fullName,
        userTyped = { onValueChange(signUpUiState.signUpDetails.copy(fullName = it)) },
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
    )
    IdentityTextField(
        label = stringResource(R.string.email),
        displayTyped = signUpUiState.signUpDetails.email,
        userTyped = { onValueChange(signUpUiState.signUpDetails.copy(email = it)) },
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
    )
    IdentityTextField(
        label = stringResource(R.string.password),
        displayTyped = signUpUiState.signUpDetails.password,
        userTyped = { onValueChange(signUpUiState.signUpDetails.copy(password = it)) },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        )
    )
    IdentityTextField(
        label = stringResource(R.string.re_enter_password),
        displayTyped = signUpUiState.signUpDetails.reEnterPassword,
        userTyped = { onValueChange(signUpUiState.signUpDetails.copy(reEnterPassword = it)) },
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
            signUpUiState = signUpUiState,
            signUpViewModel = signUpViewModel,
            onClick = { signUpViewModel.clickedCalendarIcon() }
        )
        GenderPicker(
            gender = signUpUiState.signUpDetails.gender,
            signUpUiState = signUpUiState,
            signUpViewModel = signUpViewModel
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
) {
    TextField(
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
        modifier = Modifier
            .height(70.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDocked(
    signUpUiState: SignUpUiState,
    signUpViewModel: SignUpViewModel,
    onClick: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        signUpViewModel.convertMillisToDate(it)
    } ?: ""

    Box(
        modifier = Modifier.padding(12.dp)
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = {},
            label = { Text(stringResource(R.string.date_of_birth)) },
            readOnly = true,
            trailingIcon = {
                IconAppBar(
                    icon = Icons.Default.DateRange,
                    onClick = onClick,
                    contentDescription = stringResource(R.string.select_date)
                )
            },
            modifier = Modifier
                .height(64.dp)
                .width(170.dp)
        )

        if (signUpUiState.showDatePicker) {
            Popup(
                onDismissRequest = {
                    signUpViewModel.updateDateBirthAndPicker(selectedDate)
                },
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
        }
    }
}

@Composable
private fun GenderPicker(
    gender: String,
    signUpUiState: SignUpUiState,
    signUpViewModel: SignUpViewModel
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
                    onClick = { signUpViewModel.clickedArrowDownIcon() },
                    contentDescription = stringResource(R.string.arrow_down)
                )
            },
            modifier = Modifier
                .height(64.dp)
                .width(140.dp)
        )
        DropdownMenu(
            expanded = signUpUiState.showMenuPicker,
            onDismissRequest = { signUpViewModel.dismissMenu() }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.male)) },
                onClick = { signUpViewModel.updateGender("Male") }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.female)) },
                onClick = { signUpViewModel.updateGender("Female") }
            )
        }
    }


}