package eu.tkacas.smartalert.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.ui.theme.BlueColor
import eu.tkacas.smartalert.ui.theme.BlueGreen
import eu.tkacas.smartalert.ui.theme.DarkOrange
import eu.tkacas.smartalert.ui.theme.DarkRed
import eu.tkacas.smartalert.ui.theme.DarkYellow
import eu.tkacas.smartalert.ui.theme.OrangeColor
import eu.tkacas.smartalert.ui.theme.Primary
import eu.tkacas.smartalert.ui.theme.PrussianBlue
import eu.tkacas.smartalert.ui.theme.RedColor
import eu.tkacas.smartalert.ui.theme.Secondary
import eu.tkacas.smartalert.ui.theme.SelectiveYellow
import eu.tkacas.smartalert.ui.theme.SkyBlue
import eu.tkacas.smartalert.ui.theme.YellowColor

@Composable
fun ButtonComponent(value: String, onButtonClicked: () -> Unit, isEnabled: Boolean = false) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        onClick = {
            onButtonClicked.invoke()
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(50.dp),
        enabled = isEnabled
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    color = PrussianBlue,
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

        }
    }
}

@Composable
fun ButtonLandscapeComponent(value: String, onButtonClicked: () -> Unit, isEnabled: Boolean = false) {
    Button(
        modifier = Modifier
            .width(185.dp)
            .heightIn(48.dp),
        onClick = {
            onButtonClicked.invoke()
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(50.dp),
        enabled = isEnabled
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    color = PrussianBlue,
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

        }
    }
}


@Composable
fun AlertLevelButtonsRowComponent(
    initialValue: Int = 1,
    onButtonClicked: (Int) -> Unit) {
    var value by remember { mutableStateOf(initialValue) }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly
        ){
        Button(
            onClick = {
                value = 1
                onButtonClicked(value)
            },
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (value == 1) YellowColor else Color.LightGray,
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .height(50.dp)
                .let {
                    if (value == 1) it.border(
                        2.dp,
                        DarkYellow,
                        RoundedCornerShape(10.dp)
                    ) else it
                }
        ){
            Text(
                text = stringResource(id = R.string.low),
                color = if (value == 1) PrussianBlue else Color.White
            )
        }
        Button(
            onClick = {
                value = 2
                onButtonClicked(value)
            },
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (value == 2) OrangeColor else Color.LightGray,
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .height(50.dp)
                .let {
                    if (value == 2) it.border(
                        2.dp,
                        DarkOrange,
                        RoundedCornerShape(10.dp)
                    ) else it
                }
        ){
            Text(
                text = stringResource(id = R.string.normal),
                color = if (value == 2) PrussianBlue else Color.White
                )
        }
        Button(
            onClick = { value = 3
                onButtonClicked(value)
            },
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (value == 3) RedColor else Color.LightGray,
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .height(50.dp)
                .let {
                    if (value == 3) it.border(
                        2.dp,
                        DarkRed,
                        RoundedCornerShape(10.dp)
                    ) else it
                }
        ){
            Text(
                text = stringResource(id = R.string.high),
                color = if (value == 3) PrussianBlue else Color.White
            )
        }
    }
}

@Composable
fun UploadButtonComponent(value: String, onButtonClicked: () -> Unit) {
    Button(
        modifier = Modifier
            .width(175.dp)
            .heightIn(48.dp),
        onClick = {
            onButtonClicked.invoke()
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(30.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(40.dp)
                .background(
                    color = PrussianBlue,
                    shape = RoundedCornerShape(30.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun GeneralButtonComponent(value: String, onButtonClicked: () -> Unit) {
    Button(
        modifier = Modifier
            .width(175.dp)
            .heightIn(48.dp),
        onClick = {
            onButtonClicked.invoke()
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(30.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(40.dp)
                .background(
                    color = SkyBlue,
                    shape = RoundedCornerShape(30.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                color = PrussianBlue,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CameraButton(onButtonClicked: () -> Unit) {
    Button(
        modifier = Modifier
            .size(50.dp)
            .background(
                color = BlueGreen,
                shape = RoundedCornerShape(30.dp)
            ),
        onClick = {
            onButtonClicked.invoke()
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(30.dp)
    ) {
        IconButton(onClick = onButtonClicked) {
            Image(painter = painterResource(id = R.drawable.photo_camera), contentDescription = "Open Camera")
        }
    }
}

@Composable
fun UploadPhotoButton(
    onButtonClicked: () -> Unit
) {

    
    Button(
        modifier = Modifier
            .width(110.dp)
            .height(40.dp)
            .background(
                color = BlueColor,
                shape = RoundedCornerShape(10.dp)
            ),
        onClick = {
            onButtonClicked.invoke()
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(10.dp)
    ){
        //SimpleTextComponent(value = "Upload")
        SimpleTextComponent(value = stringResource(id = R.string.upload))
        Spacer(modifier = Modifier.size(4.dp))
        Icon(painter = painterResource(id = R.drawable.upload), contentDescription = "Upload Photo")
    }
}

@Composable
fun FloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        containerColor = SkyBlue,
        onClick = { onClick() },
    ) {
        Image(painterResource(id = R.drawable.edit), contentDescription = "Localized description")
    }
}


@Composable
fun ButtonWithImageComponent(onClick: () -> Unit, imageId: Int, buttonText: String) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier
            .heightIn(28.dp)
            .width(300.dp)
            .padding(top = 8.dp, bottom = 8.dp)
            .background(color = SkyBlue, shape = RoundedCornerShape(10.dp))
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "Button Image",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = buttonText,
                fontSize = 16.sp,
                color = PrussianBlue
            )
        }
    }
}
