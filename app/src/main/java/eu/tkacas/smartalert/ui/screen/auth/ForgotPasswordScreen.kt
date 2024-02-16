package eu.tkacas.smartalert.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.component.BackToLoginComponent
import eu.tkacas.smartalert.ui.component.ClickableLoginTextComponent
import eu.tkacas.smartalert.ui.component.GeneralButtonComponent
import eu.tkacas.smartalert.ui.component.HeadingTextComponent
import eu.tkacas.smartalert.ui.component.NormalTextComponent
import eu.tkacas.smartalert.ui.component.TextFieldComponent
import eu.tkacas.smartalert.ui.event.ForgotPasswordUIEvent
import eu.tkacas.smartalert.ui.event.LoginUIEvent
import eu.tkacas.smartalert.viewmodel.ForgotPasswordViewModel
import eu.tkacas.smartalert.viewmodel.LoginViewModel


@Composable
fun ForgotPasswordScreen(navController: NavController? = null) {
    val forgotpasswordViewModel: ForgotPasswordViewModel = viewModel()
    forgotpasswordViewModel.navController = navController

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeadingTextComponent(value = stringResource(id = R.string.forgot_password))
                Spacer(modifier = Modifier.size(30.dp))
                Image(
                    painter = painterResource(id = R.drawable.forgot_password_illustration),
                    contentDescription = "Forgot Password",
                    modifier = Modifier
                        .size(250.dp)
                )
                Spacer(modifier = Modifier.size(20.dp))
                NormalTextComponent(value = stringResource(id = R.string.forgot_password_instructions))

                TextFieldComponent(labelValue = stringResource(id = R.string.email),
                    painterResource(id = R.drawable.email),
                    onTextChanged = {
                        forgotpasswordViewModel.onEvent(ForgotPasswordUIEvent.EmailChanged(it))
                    },
                    errorStatus = forgotpasswordViewModel.forgotPasswordUIState.value.emailError
                )

                Spacer(modifier = Modifier.size(15.dp))

                GeneralButtonComponent(
                    value = stringResource(id = R.string.submit),
                    onButtonClicked = {
                    forgotpasswordViewModel.onEvent(ForgotPasswordUIEvent.ResetPasswordButtonClicked)
                    }
                )
                Spacer(modifier = Modifier.size(50.dp))
                BackToLoginComponent(onTextSelected = {
                    navController?.navigate("login")
                })




                //MyTextFieldComponent(labelValue = stringResource(id = R.string.email))
//                ButtonComponent(value = stringResource(id = R.string.submit)) {
//                    // Handle submit button click
//                }

            }
        }
    }
}

@Preview
@Composable
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen()
}