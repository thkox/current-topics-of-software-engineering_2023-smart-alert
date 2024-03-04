package eu.tkacas.smartalert.ui.screen.auth

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.R.string.create_account
import eu.tkacas.smartalert.R.string.email
import eu.tkacas.smartalert.R.string.firstname
import eu.tkacas.smartalert.R.string.lastname
import eu.tkacas.smartalert.R.string.password
import eu.tkacas.smartalert.R.string.register
import eu.tkacas.smartalert.ui.component.ButtonComponent
import eu.tkacas.smartalert.ui.component.CheckboxComponent
import eu.tkacas.smartalert.ui.component.ClickableLoginTextComponent
import eu.tkacas.smartalert.ui.component.DividerTextComponent
import eu.tkacas.smartalert.ui.component.HeadingTextComponent
import eu.tkacas.smartalert.ui.component.PasswordTextFieldComponent
import eu.tkacas.smartalert.ui.component.TextFieldComponent
import eu.tkacas.smartalert.ui.event.SignupUIEvent
import eu.tkacas.smartalert.ui.theme.SkyBlue
import eu.tkacas.smartalert.viewmodel.auth.SignupViewModel


@Composable
fun SignUpScreen(navController: NavController? = null) {

    val signupViewModel: SignupViewModel = viewModel()
    signupViewModel.navController = navController

    val context = LocalContext.current

    val config = LocalConfiguration.current

    val portraitMode = remember { mutableStateOf(config.orientation) }

    val acceptTermsPrefix = stringResource(id = R.string.terms_and_conditions)
    val and = stringResource(id = R.string.and)
    val privacyPolicy = stringResource(id = R.string.privacy_policy)
    val termsOfUse = stringResource(id = R.string.terms_of_use)

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Black)) {
            append(acceptTermsPrefix)
        }
        pushStringAnnotation(tag = "PrivacyPolicy", annotation = "PrivacyPolicy")
        withStyle(style = SpanStyle(color = SkyBlue, textDecoration = TextDecoration.Underline)) {
            append(privacyPolicy)
        }
        pop()
        withStyle(style = SpanStyle(color = Color.Black)) {
            append(and)
        }
        pushStringAnnotation(tag = "TermsOfUse", annotation = "TermsOfUse")
        withStyle(style = SpanStyle(color = SkyBlue, textDecoration = TextDecoration.Underline)) {
            append(termsOfUse)
        }
        pop()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color.White),
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
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
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

                        Spacer(modifier = Modifier.height(40.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CheckboxComponent(
                                onCheckedChange = {
                                    signupViewModel.onEvent(
                                        SignupUIEvent.PrivacyPolicyCheckBoxClicked(
                                            it
                                        )
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            ClickableText(
                                text = annotatedString,
                                onClick = { offset ->
                                    annotatedString.getStringAnnotations(
                                        tag = "PrivacyPolicy",
                                        start = offset,
                                        end = offset
                                    ).firstOrNull()?.let {
                                        println("Privacy Policy clicked")
                                        navController?.navigate("privacyPolicy")
                                    }
                                    annotatedString.getStringAnnotations(
                                        tag = "TermsOfUse",
                                        start = offset,
                                        end = offset
                                    ).firstOrNull()?.let {
                                        println("Terms of Use clicked")
                                        navController?.navigate("termsAndConditions")
                                    }
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(40.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ButtonComponent(
                                value = stringResource(id = register),
                                onButtonClicked = {
                                    signupViewModel.onEvent(SignupUIEvent.RegisterButtonClicked)
                                },
                                isEnabled = signupViewModel.allValidationsPassed.value
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        DividerTextComponent()

                        ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
                            navController?.navigate("login")
                        }, context = context)
                    }


                }
                if (signupViewModel.signUpInProgress.value) {
                    CircularProgressIndicator(color = SkyBlue)
                }
            }
        }
    }


}


@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}