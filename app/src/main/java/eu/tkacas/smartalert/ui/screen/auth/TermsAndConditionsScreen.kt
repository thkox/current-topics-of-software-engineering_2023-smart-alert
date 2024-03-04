package eu.tkacas.smartalert.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.navigation.AppBarBackView


@Composable
fun TermsAndConditionsScreen(navController: NavController? = null) {
    val scaffoldState = rememberScaffoldState()

    val termsOfUseIntro = stringResource(id = R.string.terms_of_use_intro)
    val termsOfUseLicense = stringResource(id = R.string.terms_of_use_license)
    val termsOfUseConduct = stringResource(id = R.string.terms_of_use_conduct)
    val termsOfUseIp = stringResource(id = R.string.terms_of_use_ip)
    val termsOfUseLiability = stringResource(id = R.string.terms_of_use_liability)
    val termsOfUseModifications = stringResource(id = R.string.terms_of_use_modifications)
    val termsOfUseGoverningLaw = stringResource(id = R.string.terms_of_use_governing_law)
    val termsOfUseContactUs = stringResource(id = R.string.terms_of_use_contact_us)
    val termsOfUseAcknowledgement = stringResource(id = R.string.terms_of_use_acknowledgement)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(
                title = stringResource(id = R.string.terms_and_conditions_header),
                navController = navController,
                enableSettingsButton = false
            )
        },
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(it)
        ) {

            LazyColumn(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                item {
                    Text(text = termsOfUseIntro)
                    Text(text = termsOfUseLicense)
                    Text(text = termsOfUseConduct)
                    Text(text = termsOfUseIp)
                    Text(text = termsOfUseLiability)
                    Text(text = termsOfUseModifications)
                    Text(text = termsOfUseGoverningLaw)
                    Text(text = termsOfUseContactUs)
                    Text(text = termsOfUseAcknowledgement)
                }
            }
        }
    }
}

@Preview
@Composable
fun TermsAndConditionsScreenPreview() {
    TermsAndConditionsScreen()
}