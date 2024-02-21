package eu.tkacas.smartalert.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.component.HeadingTextComponent
import eu.tkacas.smartalert.R.string.create_account
import eu.tkacas.smartalert.R.string.firstname
import eu.tkacas.smartalert.R.string.lastname
import eu.tkacas.smartalert.R.string.email
import eu.tkacas.smartalert.R.string.password
import eu.tkacas.smartalert.R.string.terms_and_conditions
import eu.tkacas.smartalert.R.string.register
import eu.tkacas.smartalert.ui.component.ButtonComponent
import eu.tkacas.smartalert.ui.component.CheckboxComponent
import eu.tkacas.smartalert.ui.component.ClickableLoginTextComponent
import eu.tkacas.smartalert.ui.component.DividerTextComponent
import eu.tkacas.smartalert.ui.component.TextFieldComponent
import eu.tkacas.smartalert.ui.component.PasswordTextFieldComponent
import eu.tkacas.smartalert.ui.event.SignupUIEvent
import eu.tkacas.smartalert.viewmodel.auth.SignupViewModel


@Composable
fun SignUpScreen(navController: NavController? = null) {

    val signupViewModel: SignupViewModel = viewModel()
    signupViewModel.navController = navController

    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                HeadingTextComponent(value = stringResource(id = create_account))
                Spacer(modifier = Modifier.height(20.dp))
                TextFieldComponent(
                    labelValue = stringResource(id = firstname),
                    painterResource(id = R.drawable.profile),
                    onTextChanged = {
                        signupViewModel.onEvent(SignupUIEvent.FirstNameChanged(it))
                    },
                    errorStatus = signupViewModel.registrationUIState.value.firstNameError
                )
                TextFieldComponent(
                    labelValue = stringResource(id = lastname),
                    painterResource(id = R.drawable.profile),
                    onTextChanged = {
                        signupViewModel.onEvent(SignupUIEvent.LastNameChanged(it))
                    },
                    errorStatus = signupViewModel.registrationUIState.value.lastNameError
                )
                TextFieldComponent(
                    labelValue = stringResource(id = email),
                    painterResource(id = R.drawable.email),
                    onTextChanged = {
                        signupViewModel.onEvent(SignupUIEvent.EmailChanged(it))
                    },
                    errorStatus = signupViewModel.registrationUIState.value.emailError
                )
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = password),
                    painterResource(id = R.drawable.password),
                    onTextSelected = {
                        signupViewModel.onEvent(SignupUIEvent.PasswordChanged(it))
                    },
                    errorStatus = signupViewModel.registrationUIState.value.passwordError
                )
                CheckboxComponent(
                    value = stringResource(id = terms_and_conditions),
                    onTextSelected = {
                        navController?.navigate("termsAndConditionsScreen")
                    },
                    onCheckedChange = {
                        signupViewModel.onEvent(SignupUIEvent.PrivacyPolicyCheckBoxClicked(it))
                    }
                )
                Spacer(modifier = Modifier.height(40.dp))
                ButtonComponent(
                    value = stringResource(id = register),
                    onButtonClicked = {
                        signupViewModel.onEvent(SignupUIEvent.RegisterButtonClicked)
                    },
                    isEnabled = signupViewModel.allValidationsPassed.value
                )
                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent()

                ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
                    navController?.navigate("login") }, context = context)
            }


        }
        if (signupViewModel.signUpInProgress.value) {
            CircularProgressIndicator()
        }
    }
}


@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}