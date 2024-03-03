package eu.tkacas.smartalert.ui.screen.settings

import android.annotation.SuppressLint
import android.app.LocaleConfig
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.component.CircleImage
import eu.tkacas.smartalert.ui.component.EmailDisplayComponent
import eu.tkacas.smartalert.ui.component.FloatingActionButton
import eu.tkacas.smartalert.ui.component.NameFieldComponent
import eu.tkacas.smartalert.ui.component.PasswordDisplayComponent
import eu.tkacas.smartalert.ui.component.PasswordTextFieldComponent
import eu.tkacas.smartalert.ui.component.UploadButtonComponent
import eu.tkacas.smartalert.ui.event.LoginUIEvent
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.ui.theme.SkyBlue
import eu.tkacas.smartalert.viewmodel.AccountViewModel
import eu.tkacas.smartalert.viewmodel.auth.LoginViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun AccountScreen (navController: NavController? = null) {
    val scaffoldState = rememberScaffoldState()
    val accountViewModel: AccountViewModel = viewModel()
    val email = accountViewModel.email.value
    val firstName = accountViewModel.firstName.collectAsState().value
    val lastName = accountViewModel.lastName.collectAsState().value

    var newPassword by remember { mutableStateOf("") }

    val password = accountViewModel.password.value

    val isLoading = accountViewModel.isLoading.collectAsState().value

    val config = LocalConfiguration.current

    val portraitMode = remember { mutableStateOf(config.orientation ) }

    var isPasswordDisplayVisible by remember { mutableStateOf(false) }

    val loginViewModel: LoginViewModel = viewModel()

    if (portraitMode.value == Configuration.ORIENTATION_PORTRAIT) {
        //PortraitLayout()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                AppBarBackView(
                    title = stringResource(id = R.string.account),
                    navController = navController,
                    enableSettingsButton = false
                )
            }
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = SkyBlue)
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 15.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    CircleImage(imageResId = R.drawable.account)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 50.dp),
                ) {
                    Spacer(modifier = Modifier.height(90.dp))

                    NameFieldComponent(
                        firstName = firstName,
                        lastName = lastName
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    EmailDisplayComponent(email = email, painterResource(id = R.drawable.email))

                    Spacer(modifier = Modifier.height(10.dp))

                    PasswordDisplayComponent(password = password, painterResource(id = R.drawable.password), label = stringResource(id = R.string.current_password))

                    if (isPasswordDisplayVisible) {
                        Spacer(modifier = Modifier.height(10.dp))
                        PasswordDisplayComponent(
                            password = password,
                            painterResource(id = R.drawable.password),
                            label = stringResource(id = R.string.new_password)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        PasswordDisplayComponent(
                            password = password,
                            painterResource(id = R.drawable.password),
                            label = stringResource(id = R.string.confirm_password)
                        )
                        //Spacer(modifier = Modifier.height(20.dp))
                        Box(
                            modifier = Modifier.fillMaxSize(),
                                //.padding(bottom = 3.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            UploadButtonComponent(
                                value = stringResource(id = R.string.submit),
                                onButtonClicked = { //TODO: Implement the logic for updating the user's account
                                    accountViewModel.changePassword(newPassword)
                                }
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 40.dp, end = 30.dp)
                        .padding(top = 10.dp),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomEnd
                    ){
                        FloatingActionButton(
                            onClick = {
                                // TODO: Implement the logic for updating the user's account
                                //isPasswordDisplayVisible = true
                                isPasswordDisplayVisible = !isPasswordDisplayVisible
                            }
                        )
                    }

                }
            }
        }
    } else{
        //LandscapeLayout()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                AppBarBackView(
                    title = stringResource(id = R.string.account),
                    navController = navController,
                    enableSettingsButton = false
                )
            }
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = SkyBlue)
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(0.5f)
                            .padding(top = 15.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        CircleImage(imageResId = R.drawable.account)

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 105.dp), contentAlignment = Alignment.TopCenter) {
                            NameFieldComponent(
                                firstName = firstName,
                                lastName = lastName
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 50.dp),
                    ) {
                        EmailDisplayComponent(email = email, painterResource(id = R.drawable.email))

                        Spacer(modifier = Modifier.height(30.dp))

                        PasswordDisplayComponent(password = password, painterResource(id = R.drawable.password), label = "Password")

                        Spacer(modifier = Modifier.height(20.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 320.dp),
                            //contentAlignment = Alignment.BottomEnd
                        ) {
                            FloatingActionButton(
                                onClick = {
                                    // TODO: Implement the logic for updating the user's account
                                }
                            )
                        }
                    }
                }
            }
        }

    }
}



@Preview
@Composable
fun AccountScreenPreview(){
    AccountScreen()
}
