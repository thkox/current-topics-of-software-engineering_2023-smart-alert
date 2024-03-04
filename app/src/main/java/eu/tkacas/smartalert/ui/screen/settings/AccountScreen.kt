package eu.tkacas.smartalert.ui.screen.settings

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.component.CircleImage
import eu.tkacas.smartalert.ui.component.EmailDisplayComponent
import eu.tkacas.smartalert.ui.component.NameFieldComponent
import eu.tkacas.smartalert.ui.component.PasswordTextFieldComponent
import eu.tkacas.smartalert.ui.component.UploadButtonComponent
import eu.tkacas.smartalert.ui.navigation.AppBarBackView
import eu.tkacas.smartalert.ui.theme.SkyBlue
import eu.tkacas.smartalert.viewmodel.settings.AccountViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun AccountScreen(navController: NavController? = null) {
    val scaffoldState = rememberScaffoldState()
    val accountViewModel: AccountViewModel = viewModel()
    val email = accountViewModel.email.value
    val firstName = accountViewModel.firstName.collectAsState().value
    val lastName = accountViewModel.lastName.collectAsState().value
    val context = LocalContext.current

    var newPassword by remember { mutableStateOf("") }

    val isLoading = accountViewModel.isLoading.collectAsState().value

    var enableEdit by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(
                title = stringResource(id = R.string.account),
                navController = navController,
                enableSettingsButton = false
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                containerColor = SkyBlue,
                onClick = {
                    enableEdit = !enableEdit
                }
            ) {
                if (enableEdit) {
                    Image(
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = null
                    )
                } else
                    Image(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = null
                    )
            }
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
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircleImage(imageResId = R.drawable.account)

                        Spacer(modifier = Modifier.height(10.dp))

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

                        if (enableEdit) {
                            PasswordTextFieldComponent(
                                labelValue = stringResource(id = R.string.new_password),
                                painterResource(id = R.drawable.password),
                                onTextSelected = {
                                    newPassword = it
                                }
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                UploadButtonComponent(
                                    value = stringResource(id = R.string.submit),
                                    onButtonClicked = {
                                        scope.launch {
                                            if (accountViewModel.changePassword(newPassword)) {
                                                Toast.makeText(
                                                    context,
                                                    "Password changed successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                enableEdit = false
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Password change failed",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }
                                )
                            }
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
