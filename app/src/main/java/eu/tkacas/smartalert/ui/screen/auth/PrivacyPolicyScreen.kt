package eu.tkacas.smartalert.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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

fun PrivacyPolicyScreen( navController: NavController? = null) {

    val privacyPolicyIntro = stringResource(id = R.string.privacy_policy_intro)
    val privacyPolicyInfoCollect = stringResource(id = R.string.privacy_policy_info_collect)
    val privacyPolicyUseInfo = stringResource(id = R.string.privacy_policy_use_info)
    val privacyPolicyShareInfo = stringResource(id = R.string.privacy_policy_share_info)
    val privacyPolicyDataSecurity = stringResource(id = R.string.privacy_policy_data_security)
    val privacyPolicyChildren = stringResource(id = R.string.privacy_policy_children)
    val privacyPolicyChanges = stringResource(id = R.string.privacy_policy_changes)
    val privacyPolicyContactUs = stringResource(id = R.string.privacy_policy_contact_us)
    val privacyPolicyAcknowledgement = stringResource(id = R.string.privacy_policy_acknowledgement)

    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .padding(16.dp)) {

        LazyColumn(modifier = Modifier.background(Color.White)){
            item {
                HeadingTextComponent(value = stringResource(id = R.string.privacy_policy_header))
                Spacer(modifier = Modifier.padding(10.dp))
                Text(text = privacyPolicyIntro)
                Text(text = privacyPolicyInfoCollect)
                Text(text = privacyPolicyUseInfo)
                Text(text = privacyPolicyShareInfo)
                Text(text = privacyPolicyDataSecurity)
                Text(text = privacyPolicyChildren)
                Text(text = privacyPolicyChanges)
                Text(text = privacyPolicyContactUs)
                Text(text = privacyPolicyAcknowledgement)
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
}

@Preview
@Composable
fun PrivacyPolicyScreenPreview(){
    PrivacyPolicyScreen()
}