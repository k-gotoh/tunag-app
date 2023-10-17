package jp.html5api.tunag_app.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.html5api.tunag_app.R
import jp.html5api.tunag_app.model.PasswordChange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowChangePasswordDialog(onButtonClick: (PasswordChange) -> Unit = { _ -> }) {

    var result by remember { mutableStateOf("Result") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
    ) {
        var oldPassword by remember { mutableStateOf("") }
        var newPassword by remember { mutableStateOf("") }

        AlertDialog(
            containerColor = Color(0xfff8f4e6),
            onDismissRequest = {
                result = "Dismiss"
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        result = "OK"
                        onButtonClick(PasswordChange(true, oldPassword, newPassword))
                    }
                ) {
                    Text("変更",color = Color.Blue, fontSize = 14.sp)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        result = "Cancel"
                        onButtonClick(PasswordChange(false, "", ""))
                    }
                ) {
                    Text("Cancel", color = Color.Red, fontSize = 14.sp)
                }
            },
            title = {
                Text("パスワードの変更", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            },
            text = {
                Column() {
                    OutlinedTextField(
                        value = oldPassword,
                        onValueChange = { oldPassword = it },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            containerColor = Color.White,
                        ),

                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done,
                        ),
                        label = { Text("現在のpassword", color = Color.Gray) },
                    )
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            containerColor = Color.White
                        ),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done,
                        ),
                        label = { Text("設定したいpassword", color = Color.Gray) },
                    )
                }
            }

        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowChangeNameDialog(me: String, onButtonClick: (String) -> Unit = { _ -> }) {

    var result by remember { mutableStateOf("Result") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
    ) {
        var newName by remember { mutableStateOf(me) }

        AlertDialog(
            containerColor = Color(0xfff8f4e6),
            onDismissRequest = {
                result = "Dismiss"
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        result = "OK"
                        onButtonClick(newName)
                    }
                ) {
                    Text("変更",color = Color.Blue, fontSize = 14.sp)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        result = "Cancel"
                        onButtonClick("")
                    }
                ) {
                    Text("Cancel", color = Color.Red, fontSize = 14.sp)
                }
            },
            title = {
                Text("名前の変更", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            },
            text = {
                Column() {
                    OutlinedTextField(
                        value = newName,
                        onValueChange = { newName = it },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            containerColor = Color.White,
                        ),
                        label = { Text("新しい名前", color = Color.Gray) }
                    )
                }
            }

        )

    }
}