package eu.tkacas.smartalert.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.component.HeadingTextComponent


@Composable
fun TermsAndConditionsScreen() {
    val termsAndConditionsText = """
        These Terms and Conditions govern your use of the Smart Alert mobile application provided by TKACAS ("we," "us," or "our"). By downloading, installing, or using the App, you agree to comply with and be bound by these Terms and Conditions.

        1. License: We grant you a limited, non-exclusive, non-transferable, revocable license to use the App for your personal, non-commercial purposes. You may not sublicense, sell, or distribute the App.

        2. User Conduct: You agree to use the App in compliance with all applicable laws and regulations. You further agree not to:
        •	Use the App for any unlawful purpose.
        •	Interfere with or disrupt the operation of the App.
        •	Attempt to gain unauthorized access to any part of the App.
        •	Transmit any viruses, worms, or other harmful code through the App.
        •	Use the App to infringe upon the rights of others.

        3. Privacy: Your privacy is important to us. Please review our Privacy Policy [link to Privacy Policy] to understand how we collect, use, and disclose your information.

        4. Intellectual Property: All intellectual property rights in the App, including but not limited to copyrights, trademarks, and trade secrets, belong to us or our licensors. You may not copy, modify, or distribute any part of the App without our prior written consent.

        5. Disclaimers: The App is provided on an "as is" and "as available" basis, without any warranties of any kind, express or implied. We do not guarantee that the App will be error-free, secure, or uninterrupted. You use the App at your own risk.

        6. Limitation of Liability: To the fullest extent permitted by law, we shall not be liable for any indirect, incidental, special, consequential, or punitive damages arising out of or relating to your use of the App.

        7. Modifications: We reserve the right to modify or discontinue the App at any time without prior notice. We may also update these Terms and Conditions from time to time. Your continued use of the App after any such changes constitutes acceptance of the updated Terms and Conditions.

        8. Governing Law: These Terms and Conditions shall be governed by and construed in accordance with the laws of Greece, without regard to its conflict of law principles.

        9. Contact Us: If you have any questions or concerns about these Terms and Conditions, please contact us at info@smaralert.gr.

        By using the App, you acknowledge that you have read, understood, and agree to be bound by these Terms and Conditions. If you do not agree with any part of these Terms and Conditions, you may not use the App.
    """.trimIndent()



    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .padding(16.dp)) {

        HeadingTextComponent(value = stringResource(id = R.string.terms_and_conditions_header))

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            HeadingTextComponent(value = stringResource(id = R.string.terms_and_conditions_header))

            Text(
                text = termsAndConditionsText,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

    }

    // go back to sign up screen
}

@Preview
@Composable
fun TermsAndConditionsScreenPreview(){
    TermsAndConditionsScreen()
}