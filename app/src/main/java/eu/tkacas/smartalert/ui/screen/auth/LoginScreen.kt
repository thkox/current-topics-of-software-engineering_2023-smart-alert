package eu.tkacas.smartalert.ui.screen.auth

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.component.ButtonComponent
import eu.tkacas.smartalert.ui.component.ClickableLoginTextComponent
import eu.tkacas.smartalert.ui.component.DividerTextComponent
import eu.tkacas.smartalert.ui.component.HeadingTextComponent
import eu.tkacas.smartalert.ui.component.TextFieldComponent
import eu.tkacas.smartalert.ui.component.PasswordTextFieldComponent
import eu.tkacas.smartalert.ui.component.UnderLinedTextComponent
import eu.tkacas.smartalert.ui.event.LoginUIEvent
import eu.tkacas.smartalert.viewmodel.auth.LoginViewModel
import eu.tkacas.smartalert.R.string.welcome_to_smart_alert_app
import eu.tkacas.smartalert.R.string.welcome_to_smart_alert_app_landscape
import eu.tkacas.smartalert.ui.component.ButtonComponent
import eu.tkacas.smartalert.ui.component.HeadingTextLandscapeComponent
import eu.tkacas.smartalert.ui.component.PasswordTextFieldLandscapeComponent
import eu.tkacas.smartalert.ui.component.TextFieldLandscapeComponent
import eu.tkacas.smartalert.ui.theme.SkyBlue

@Composable
fun LoginScreen(navController: NavController? = null) {

    val loginViewModel: LoginViewModel = viewModel()
    loginViewModel.navController = navController

    val context = LocalContext.current

    LaunchedEffect(key1 = loginViewModel.refreshScreen.value) {
        loginViewModel.toastMessage.value?.let {
            if (it.isNotEmpty()) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LazyColumn (
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
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(28.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        HeadingTextComponent(value = stringResource(id = welcome_to_smart_alert_app))
                        Spacer(modifier = Modifier.height(20.dp))

                        TextFieldComponent(
                            labelValue = stringResource(id = R.string.email),
                            painterResource(id = R.drawable.email),
                            onTextChanged = {
                                loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                            },
                            errorStatus = loginViewModel.loginUIState.value.emailError
                        )

                        PasswordTextFieldComponent(
                            labelValue = stringResource(id = R.string.password),
                            painterResource(id = R.drawable.password),
                            onTextSelected = {
                                loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                            },
                            errorStatus = loginViewModel.loginUIState.value.passwordError
                        )

                        Spacer(modifier = Modifier.height(40.dp))
                        UnderLinedTextComponent(
                            value = stringResource(id = R.string.forgot_password),
                            onClick = {
                                navController?.navigate("forgotPassword")
                            })

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ButtonComponent(
                                value = stringResource(id = R.string.login),
                                onButtonClicked = {
                                    loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
                                },
                                isEnabled = loginViewModel.allValidationsPassed.value,
                            )
                        }


                        Spacer(modifier = Modifier.height(20.dp))

                        DividerTextComponent()

                        ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                            navController?.navigate("signup")
                        }, context = context)
                    }
                }

                if (loginViewModel.loginInProgress.value) {
                    CircularProgressIndicator(color = SkyBlue)
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}