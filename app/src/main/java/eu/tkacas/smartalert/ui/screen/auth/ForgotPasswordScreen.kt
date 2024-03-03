package eu.tkacas.smartalert.ui.screen.auth

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.component.BackToLoginComponent
import eu.tkacas.smartalert.ui.component.GeneralButtonComponent
import eu.tkacas.smartalert.ui.component.HeadingTextComponent
import eu.tkacas.smartalert.ui.component.HeadingTextLandscapeComponent
import eu.tkacas.smartalert.ui.component.NormalTextComponent
import eu.tkacas.smartalert.ui.component.TextFieldComponent
import eu.tkacas.smartalert.ui.component.TextFieldLandscapeComponent
import eu.tkacas.smartalert.ui.component.UploadButtonComponent
import eu.tkacas.smartalert.ui.event.ForgotPasswordUIEvent
import eu.tkacas.smartalert.viewmodel.auth.ForgotPasswordViewModel


@Composable
fun ForgotPasswordScreen(navController: NavController? = null) {
    val forgotPasswordViewModel: ForgotPasswordViewModel = viewModel()
    forgotPasswordViewModel.navController = navController

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    color = Color.White,
                    modifier = Modifier.padding(16.dp),
                    shape = MaterialTheme.shapes.medium,
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
                                forgotPasswordViewModel.onEvent(ForgotPasswordUIEvent.EmailChanged(it))
                            },
                            errorStatus = forgotPasswordViewModel.forgotPasswordUIState.value.emailError
                        )
                        Spacer(modifier = Modifier.size(15.dp))

                        UploadButtonComponent(
                            value = stringResource(id = R.string.submit),
                            onButtonClicked = {
                                forgotPasswordViewModel.onEvent(ForgotPasswordUIEvent.ResetPasswordButtonClicked)
                            }
                        )
                        Spacer(modifier = Modifier.size(50.dp))
                        BackToLoginComponent(onTextSelected = {
                            navController?.navigate("login")
                        })
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen()
}