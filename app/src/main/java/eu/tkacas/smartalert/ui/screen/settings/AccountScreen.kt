package eu.tkacas.smartalert.ui.screen.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import eu.tkacas.smartalert.ui.component.UploadButtonComponent
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.ui.theme.SkyBlue
import eu.tkacas.smartalert.viewmodel.AccountViewModel
import eu.tkacas.smartalert.viewmodel.auth.LoginViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun AccountScreen(navController: NavController? = null) {
    val scaffoldState = rememberScaffoldState()
    val accountViewModel: AccountViewModel = viewModel()
    val email = accountViewModel.email.value
    val firstName = accountViewModel.firstName.collectAsState().value
    val lastName = accountViewModel.lastName.collectAsState().value

    var newPassword by remember { mutableStateOf("") }

    val password = accountViewModel.password.value

    val isLoading = accountViewModel.isLoading.collectAsState().value

    var isPasswordDisplayVisible by remember { mutableStateOf(false) }

    val loginViewModel: LoginViewModel = viewModel()


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
        LazyColumn {
            item {
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
                            .padding(top = 20.dp),
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))

                        NameFieldComponent(
                            firstName = firstName,
                            lastName = lastName
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        EmailDisplayComponent(
                            email = email,
                            painterResource(id = R.drawable.email)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        PasswordDisplayComponent(
                            password = password,
                            painterResource(id = R.drawable.password),
                            label = stringResource(id = R.string.current_password)
                        )

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
                            Box(
                                modifier = Modifier.fillMaxSize(),
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
                        ) {
                            FloatingActionButton(
                                onClick = {
                                    // TODO: Implement the logic for updating the user's account
                                    isPasswordDisplayVisible = !isPasswordDisplayVisible
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
fun AccountScreenPreview() {
    AccountScreen()
}
