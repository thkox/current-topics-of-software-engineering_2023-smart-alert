package eu.tkacas.smartalert.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import eu.tkacas.smartalert.ui.component.ButtonComponent
import eu.tkacas.smartalert.ui.component.HeadingTextComponent


@Composable
fun TermsAndConditionsScreen(navController: NavController? = null) {

    val termsOfUseIntro = stringResource(id = R.string.terms_of_use_intro)
    val termsOfUseLicense = stringResource(id = R.string.terms_of_use_license)
    val termsOfUseConduct = stringResource(id = R.string.terms_of_use_conduct)
    val termsOfUseIp = stringResource(id = R.string.terms_of_use_ip)
    val termsOfUseLiability = stringResource(id = R.string.terms_of_use_liability)
    val termsOfUseModifications = stringResource(id = R.string.terms_of_use_modifications)
    val termsOfUseGoverningLaw = stringResource(id = R.string.terms_of_use_governing_law)
    val termsOfUseContactUs = stringResource(id = R.string.terms_of_use_contact_us)
    val termsOfUseAcknowledgement = stringResource(id = R.string.terms_of_use_acknowledgement)

    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .padding(16.dp)) {

        LazyColumn(modifier = Modifier.background(Color.White)) {
            item {
                HeadingTextComponent(value = stringResource(id = R.string.terms_and_conditions_header))
                Spacer(modifier = Modifier.padding(10.dp))
                Text(text = termsOfUseIntro)
                Text(text = termsOfUseLicense)
                Text(text = termsOfUseConduct)
                Text(text = termsOfUseIp)
                Text(text = termsOfUseLiability)
                Text(text = termsOfUseModifications)
                Text(text = termsOfUseGoverningLaw)
                Text(text = termsOfUseContactUs)
                Text(text = termsOfUseAcknowledgement)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    ButtonComponent(
                        value = stringResource(id = R.string.go_back),
                        onButtonClicked = {
                            navController?.navigateUp()
                        }
                    )
                }
            }
        }
    }

    // go back to sign up screen
}

@Preview
@Composable
fun TermsAndConditionsScreenPreview(){
    TermsAndConditionsScreen()
}