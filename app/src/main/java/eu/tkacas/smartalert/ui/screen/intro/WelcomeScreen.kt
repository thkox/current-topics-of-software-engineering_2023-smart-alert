package eu.tkacas.smartalert.ui.screen.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.component.ButtonComponent
import eu.tkacas.smartalert.ui.component.CenteredCreatorsText
import eu.tkacas.smartalert.ui.theme.BlueGreen
import eu.tkacas.smartalert.ui.theme.PrussianBlue

@Composable
fun WelcomeScreen( navController: NavController? = null) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                horizontalArrangement = Arrangement.Absolute.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painterResource(id = R.drawable.smart_alert_logo_full_transparent), contentDescription = "Logo of the Smart Alert App", modifier = Modifier.size(200.dp))
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp, top = 25.dp),
                horizontalArrangement = Arrangement.Absolute.Left,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.welcome_to_smart_alert_app),
                    style = typography.h4,
                    modifier = Modifier.padding(bottom = 10.dp),
                    color = PrussianBlue
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Column {
                    Row {
                        Text(
                            text = stringResource(id = R.string.welcome_message),
                            style = typography.body1,
                            color = BlueGreen,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(35.dp))
                    Row {
                        CenteredCreatorsText()
                    }
                }
            }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 30.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {

                    ButtonComponent(
                        value = stringResource(id = R.string.login),
                        onButtonClicked = {
                            navController?.navigate("login")
                        },
                        isEnabled = true
                    )
                }
        }
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
fun WelcomeScreenPreview(){
    WelcomeScreen()
}