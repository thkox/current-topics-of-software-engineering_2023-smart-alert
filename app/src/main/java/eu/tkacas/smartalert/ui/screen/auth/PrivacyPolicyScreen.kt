package eu.tkacas.smartalert.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
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
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.component.HeadingTextComponent

@Composable

fun PrivacyPolicyScreen() {
    val privacyPolicyText = """
        TKACAS ("we," "us," or "our") operates the Smart Alert mobile application. This Privacy Policy outlines how we collect, use, and disclose information obtained from users of the App.

        1. Information We Collect
        Personal Information: We may collect personal information that you voluntarily provide to us, such as your name, email address, and contact information.
        Device Information: We may collect information about your device, including device type, operating system, unique device identifiers, and mobile network information.
        Usage Information: We may collect information about how you use the App, including the pages you visit, the features you use, and your interactions with the App.
        Location Information: With your consent, we may collect and process information about your precise location using GPS, Wi-Fi, or cellular network information.

        2. How We Use Your Information
        We may use the information we collect for the following purposes:
        •	To provide and improve the App and our services.
        •	To communicate with you, respond to your inquiries, and provide customer support.
        •	To personalize your experience and tailor content and advertisements to your interests.
        •	To analyze trends and usage patterns and optimize the performance of the App.
        •	To comply with legal and regulatory requirements.

        3. How We Share Your Information
        We may share your information with third parties for the following purposes:
        •	With service providers who assist us in operating the App and providing our services.
        •	With our affiliates, partners, and other trusted third parties for marketing and promotional purposes.
        •	With law enforcement agencies, government authorities, or other third parties when required by law or to protect our rights, property, or safety.

        4. Data Security
        We take reasonable measures to protect the security of your information and prevent unauthorized access, disclosure, or use. However, no method of transmission over the internet or electronic storage is 100% secure, and we cannot guarantee the absolute security of your information.

        5. Children's Privacy
        The App is not intended for children under the age of 13. We do not knowingly collect personal information from children under the age of 13. If you believe that we have inadvertently collected information from a child under 13, please contact us immediately.

        6. Changes to this Privacy Policy
        We may update this Privacy Policy from time to time to reflect changes in our practices or legal requirements. We will notify you of any material changes by posting the updated Privacy Policy on the App or through other means.

        7. Contact Us
        If you have any questions or concerns about this Privacy Policy or our privacy practices, please contact us at info@smartalert.gr.

        By using the App, you consent to the collection, use, and disclosure of your information in accordance with this Privacy Policy.
        """.trimIndent()

    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .padding(16.dp)) {

        HeadingTextComponent(value = stringResource(id = R.string.privacy_policy_header))

        LazyColumn(modifier = Modifier.verticalScroll(rememberScrollState())) {
            item {
                HeadingTextComponent(value = stringResource(id = R.string.privacy_policy_header))

                Text(
                    text = privacyPolicyText,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

    }

    // go back to sign up screen
}

@Preview
@Composable
fun PrivacyPolicyScreenPreview(){
    PrivacyPolicyScreen()
}