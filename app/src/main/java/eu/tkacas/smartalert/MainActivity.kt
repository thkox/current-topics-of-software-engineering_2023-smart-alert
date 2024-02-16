package eu.tkacas.smartalert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import eu.tkacas.smartalert.app.SmartAlertApp

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
