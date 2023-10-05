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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp() {
    Column(modifier = Modifier.fillMaxSize()) {
        var id by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var password2 by remember { mutableStateOf("") }
        showTopAppBar(stringResource(id = R.string.header_title_singup))
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
            value = id,
            maxLines = 1,
            onValueChange = { id = it },
            label = { Text("USER") }
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(10.dp)
                .offset(40.dp, 0.dp),
            value = password,
            maxLines = 1,
            onValueChange = { password = it },
            label = { Text("password") },
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
            value = password2,
            maxLines = 1,
            onValueChange = { password2 = it },
            label = { Text("passwordの再入力") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            )
        )


        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(5.dp)
                    .offset((-60).dp, 40.dp),
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

