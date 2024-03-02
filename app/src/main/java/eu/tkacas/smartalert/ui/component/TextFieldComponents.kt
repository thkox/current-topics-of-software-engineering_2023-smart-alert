package eu.tkacas.smartalert.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tkacas.smartalert.R
import eu.tkacas.smartalert.interfacesAPI.PlacesAPI
import eu.tkacas.smartalert.ui.theme.BgColor
import eu.tkacas.smartalert.ui.theme.BlueGreen
import eu.tkacas.smartalert.ui.theme.Primary
import eu.tkacas.smartalert.ui.theme.PrussianBlue
import eu.tkacas.smartalert.ui.theme.componentShapes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CityTextFieldComponent(
    labelValue: String,
    placesAPI: PlacesAPI,
    apiKey: String,
    locationName: MutableState<String>,
    onTextChanged: (String) -> Unit
){
    var city = remember {
        mutableStateOf("")
    }
    var predictions by remember {
        mutableStateOf(listOf<String>())
    }

    var isDropdownExpanded by remember {
        mutableStateOf(false)
    }

    val locName by remember { mutableStateOf(locationName)}
    locName.value = locationName.value


    val coroutineScope = rememberCoroutineScope()

    BoxWithConstraints {
        val textFieldWidth = this.maxWidth

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = labelValue) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = PrussianBlue,
                focusedLabelColor = PrussianBlue,
                cursorColor = PrussianBlue,
                backgroundColor = BgColor
            ),
            singleLine = true,
            maxLines = 1,
            value = (if(locName.value != "") locName.value else city.value )?: "",
            onValueChange = {
                city.value = it
                onTextChanged(it)
                coroutineScope.launch(Dispatchers.IO) {
                    try {
                        val response = placesAPI.getPlacesAutocomplete(it, apiKey).execute()
                        val newPredictions = response.body()?.predictions?.map { it.description } ?: listOf()
                        withContext(Dispatchers.Main) {
                            predictions = newPredictions
                            isDropdownExpanded = newPredictions.isNotEmpty()
                        }
                    } catch (e: Exception) {
                        println("Network request failed: ${e.message}")
                    }
                }
            },
            enabled = locName.value == ""
        )

        DropdownMenu(
            expanded = isDropdownExpanded,
            onDismissRequest = { isDropdownExpanded = false },
            modifier = Modifier
                .width(textFieldWidth)
                .align(Alignment.BottomStart)
                .height(112.dp)
                .focusable(false)
        ) {
            predictions.forEach { prediction ->
                DropdownMenuItem(onClick = {
                    city.value = prediction
                    isDropdownExpanded = false
                    onTextChanged(prediction)
                }) {
                    Text(text = prediction)
                }
            }
        }
    }
}

@Composable
fun TextFieldComponent(
    labelValue: String,
    painterResource: Painter,
    onTextChanged: (String) -> Unit,
    errorStatus: Boolean = false
) {

    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = PrussianBlue,
            focusedLabelColor = PrussianBlue,
            cursorColor = PrussianBlue,
            backgroundColor = BgColor
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextChanged(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        isError = !errorStatus
    )
}

@Composable
fun TextFieldLandscapeComponent(
    labelValue: String,
    painterResource: Painter,
    onTextChanged: (String) -> Unit,
    errorStatus: Boolean = false
) {

    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .width(400.dp)
            .height(65.dp)
            .clip(componentShapes.small),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = PrussianBlue,
            focusedLabelColor = PrussianBlue,
            cursorColor = PrussianBlue,
            backgroundColor = BgColor
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextChanged(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        isError = !errorStatus
    )
}


@Composable
fun PasswordTextFieldComponent(
    labelValue: String,
    painterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
){

    val localFocusManager = LocalFocusManager.current
    val password = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = PrussianBlue,
            focusedLabelColor = PrussianBlue,
            cursorColor = PrussianBlue,
            backgroundColor = BgColor
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        maxLines = 1,
        value = password.value,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        trailingIcon = {
            val iconImage = if(passwordVisible.value) {
                painterResource(id = R.drawable.visibility)
            } else{
                painterResource(id = R.drawable.visibility_off)
            }
            val description = if(passwordVisible.value){
                stringResource(id = R.string.hide_password)
            } else {
                stringResource(id = R.string.show_password)
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(painter = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = !errorStatus
    )
}

@Composable
fun PasswordTextFieldLandscapeComponent(
    labelValue: String,
    painterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
){

    val localFocusManager = LocalFocusManager.current
    val password = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier
            .width(400.dp)
            .height(65.dp)
            .clip(componentShapes.small),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = PrussianBlue,
            focusedLabelColor = PrussianBlue,
            cursorColor = PrussianBlue,
            backgroundColor = BgColor
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        maxLines = 1,
        value = password.value,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        trailingIcon = {
            val iconImage = if(passwordVisible.value) {
                painterResource(id = R.drawable.visibility)
            } else{
                painterResource(id = R.drawable.visibility_off)
            }
            val description = if(passwordVisible.value){
                stringResource(id = R.string.hide_password)
            } else {
                stringResource(id = R.string.show_password)
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(painter = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = !errorStatus
    )
}

@Composable
fun MultilineTextFieldComponent(
    value: String,
    onTextChanged: (String) -> Unit
){
    var text by remember { mutableStateOf(value) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(start = 15.dp, top = 10.dp, end = 15.dp)
            .background(Color.White, RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        value = text,
        onValueChange = {input ->
            if (input.length <= 250) {
                text = input
                onTextChanged(input)
            }
        },
        textStyle = TextStyle(color = BlueGreen),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = PrussianBlue
        ),
        trailingIcon = {
            Row(
                modifier = Modifier.padding(end = 10.dp, top = 80.dp)
            ) {
                Text("${text.length}/250")
            }
        }
    )
}


@Composable
fun EmailDisplayComponent(email: String, painterResource: Painter) {
    var enabled by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .clip(componentShapes.small),
        textStyle = TextStyle(fontSize = 18.sp),
        value = email,
        onValueChange = {},
        label = { Text(text = "Email") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = PrussianBlue,
            focusedLabelColor = PrussianBlue,
            cursorColor = PrussianBlue,
            backgroundColor = BgColor
        ),
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "Email")
        },
        enabled = false
    )
}



@Composable
fun NameFieldComponent(
    firstName: String,
    lastName: String
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = firstName,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
            color = PrussianBlue
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = lastName,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
            color = BlueGreen
        )
    }
}


@Composable
fun PasswordDisplayComponent(password: String, painterResource: Painter, label: String){
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .clip(componentShapes.small),
        textStyle = TextStyle(fontSize = 18.sp),
        value = password,
        onValueChange = {},
        label = { Text(text = label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = PrussianBlue,
            focusedLabelColor = PrussianBlue,
            cursorColor = PrussianBlue,
            backgroundColor = BgColor
        ),
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "Password")
        },
        enabled = false
    )
}



@Composable
fun CityTextFieldLandscapeComponent(
    labelValue: String,
    placesAPI: PlacesAPI,
    apiKey: String,
    onTextChanged: (String) -> Unit
){
    var city = remember {
        mutableStateOf("")
    }
    var predictions by remember {
        mutableStateOf(listOf<String>())
    }

    var isDropdownExpanded by remember {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()


    Column {
        NormalTextComponent(value = stringResource(id = R.string.city_of_emergency))
        BoxWithConstraints {
            val textFieldWidth = this.maxWidth

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
//                    .width(200.dp)
//                    .height(50.dp),
                label = { Text(text = labelValue) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PrussianBlue,
                    focusedLabelColor = PrussianBlue,
                    cursorColor = PrussianBlue,
                    backgroundColor = BgColor
                ),
                singleLine = true,
                maxLines = 1,
                value = city.value,
                onValueChange = {
                    city.value = it
                    onTextChanged(it)
                    coroutineScope.launch(Dispatchers.IO) {
                        try {
                            val response = placesAPI.getPlacesAutocomplete(it, apiKey).execute()
                            val newPredictions =
                                response.body()?.predictions?.map { it.description } ?: listOf()
                            withContext(Dispatchers.Main) {
                                predictions = newPredictions
                                isDropdownExpanded = newPredictions.isNotEmpty()
                            }
                        } catch (e: Exception) {
                            println("Network request failed: ${e.message}")
                        }
                    }
                }
            )

            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false },
                modifier = Modifier
                    .width(textFieldWidth)
                    .align(Alignment.BottomStart)
                    .height(112.dp)
                    .focusable(false)
            ) {
                predictions.forEach { prediction ->
                    DropdownMenuItem(onClick = {
                        city.value = prediction
                        isDropdownExpanded = false
                        onTextChanged(prediction)
                    }) {
                        Text(text = prediction)
                    }
                }
            }
        }
    }
}
