package jp.html5api.tunag_app.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.html5api.tunag_app.R
import jp.html5api.tunag_app.model.ApiRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(onPopBack: () -> Unit = {},onSignUpClick: (ApiRequest) -> Unit = { _ -> } ) {
    Column(modifier = Modifier.fillMaxSize()) {
        var user by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var password2 by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }
        ShowTopAppBar(stringResource(id = R.string.header_title_singup)) { onPopBack() }
        Text(
            stringResource(id = R.string.title_input_user_and_pw_conf),
            Modifier
                .padding(10.dp)
                .offset(40.dp, 10.dp)
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(10.dp)
                .offset(40.dp, 10.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = Color.White
            ),
            value = user,
            maxLines = 1,
            onValueChange = { user = it },
            label = { Text("USER", color = Color.Gray) }
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(10.dp)
                .offset(40.dp, 0.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = Color.White
            ),
            value = password,
            maxLines = 1,
            onValueChange = { password = it },
            label = { Text("password", color = Color.Gray) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(10.dp)
                .offset(40.dp, (-20).dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = Color.White
            ),
            value = password2,
            maxLines = 1,
            onValueChange = { password2 = it },
            label = { Text("passwordの再入力", color = Color.Gray) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            )
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(10.dp)
                .offset(40.dp, (-30).dp),
            value = name,
            maxLines = 1,
            onValueChange = { name = it },
            label = { Text("表示用の名前") }
        )

        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd
        ) {
            Button(
                onClick = {

                    onSignUpClick(ApiRequest(user, password, "", name))
                          },
                modifier = Modifier
                    .padding(5.dp)
                    .offset((-60).dp, (-20).dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color(0xff00aac2),
                    contentColor = Color.White,
                    disabledContentColor = Color.LightGray
                )
            )
            {
                Text(
                    text = "CREATE",
                    fontSize = 18.sp,
                )
            }

        }
    }
}

