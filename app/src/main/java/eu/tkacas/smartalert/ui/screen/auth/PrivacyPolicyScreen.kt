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
fun PrivacyPolicyScreen(navController: NavController? = null) {
    val scaffoldState = rememberScaffoldState()

    val privacyPolicyIntro = stringResource(id = R.string.privacy_policy_intro)
    val privacyPolicyInfoCollect = stringResource(id = R.string.privacy_policy_info_collect)
    val privacyPolicyUseInfo = stringResource(id = R.string.privacy_policy_use_info)
    val privacyPolicyShareInfo = stringResource(id = R.string.privacy_policy_share_info)
    val privacyPolicyDataSecurity = stringResource(id = R.string.privacy_policy_data_security)
    val privacyPolicyChildren = stringResource(id = R.string.privacy_policy_children)
    val privacyPolicyChanges = stringResource(id = R.string.privacy_policy_changes)
    val privacyPolicyContactUs = stringResource(id = R.string.privacy_policy_contact_us)
    val privacyPolicyAcknowledgement = stringResource(id = R.string.privacy_policy_acknowledgement)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarBackView(
                title = stringResource(id = R.string.privacy_policy_header),
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
                    .padding(16.dp),
            ) {
                item {
                    Text(text = privacyPolicyIntro)
                    Text(text = privacyPolicyInfoCollect)
                    Text(text = privacyPolicyUseInfo)
                    Text(text = privacyPolicyShareInfo)
                    Text(text = privacyPolicyDataSecurity)
                    Text(text = privacyPolicyChildren)
                    Text(text = privacyPolicyChanges)
                    Text(text = privacyPolicyContactUs)
                    Text(text = privacyPolicyAcknowledgement)
                }
            }
        }
    }
}

@Preview
@Composable
fun PrivacyPolicyScreenPreview() {
    PrivacyPolicyScreen()
}