package eu.tkacas.smartalert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import eu.tkacas.smartalert.app.SmartAlertApp
import eu.tkacas.smartalert.screens.SignUpScreen
import eu.tkacas.smartalert.ui.theme.SmartAlertTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartAlertApp()
        }
    }
}

@Preview
@Composable
fun DefaultPreview(){
    SmartAlertApp()
}
