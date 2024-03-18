@file:OptIn(ExperimentalMaterial3Api::class)

package jp.html5api.tunag_app.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import jp.html5api.tunag_app.R
import jp.html5api.tunag_app.model.ApiRequest


@Composable
fun LoginScreen(onLoginClick: (ApiRequest) -> Unit = { _ -> }, onSignUp : () -> Unit = {},onPopBack: () -> Unit = {}) {

    Column(modifier = Modifier.fillMaxSize()) {
        var id by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        ShowTopAppBar(stringResource(id = R.string.header_title_login)) {
            onPopBack()
        }
        Text(
            stringResource(id = R.string.title_input_user_and_pw),
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
            value = id,
            maxLines = 1,
            onValueChange = { id = it },
            label = {
                Text(
                    "USER",
                    color = Color.Gray
                )
            }
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
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            maxLines = 1,
            onValueChange = { password = it },
            label = {
                Text(
                    "password",
                    color = Color.Gray
                )
            }
        )

        ClickableText(
            text = AnnotatedString(text = "新規作成はこちらをタップ"),
            onClick = {onSignUp()},
            style = TextStyle(
                color = Color.Blue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            ),
                modifier = Modifier
                .offset(60.dp, 10.dp),
        )

        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd
        ) {
            Button(
                onClick = { onLoginClick(ApiRequest(id, password, "", "")) },
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
                    text = stringResource(id = R.string.button_login),
                    fontSize = 18.sp,
                )
            }

        }

        // イベント取得サンプル
//        val observer = remember {
//            LifecycleEventObserver { _, event ->
//                when (event) {
//                    Lifecycle.Event.ON_CREATE -> {
//                        Log.d("***", "ON_CREATE")
//                    }
//                    Lifecycle.Event.ON_START -> {
//                        Log.d("***", "ON_START")
//                    }
//                    Lifecycle.Event.ON_RESUME -> {
//                        Log.d("***", "ON_RESUME")
//                    }
//                    Lifecycle.Event.ON_PAUSE -> {
//                        Log.d("***", "ON_PAUSE")
//                    }
//                    Lifecycle.Event.ON_STOP -> {
//                        Log.d("***", "ON_STOP")
//                    }
//                    Lifecycle.Event.ON_DESTROY -> {
//                        Log.d("***", "ON_DESTROY")
//                    }
//                    Lifecycle.Event.ON_ANY -> {
//                        Log.d("***", "ON_ANY")
//                    }
//                }
//            }
//        }
//        val lifecycle = LocalLifecycleOwner.current.lifecycle
//
//        DisposableEffect(Unit) {
//            Log.d("***", "addObserver")
//            lifecycle.addObserver(observer)
//            onDispose {
//                Log.d("***", "onDispose")
//                lifecycle.removeObserver(observer)
//            }
//
//        }
//
    }
}


