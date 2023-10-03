@file:OptIn(ExperimentalMaterial3Api::class)

package jp.html5api.tunag_app.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun login() {
    Column(modifier = Modifier.fillMaxSize()) {
        var id by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        showTopAppBar("ログイン")
        Text(
            "USERとPASSWORDを入力しなよ！",
            Modifier
                .padding(10.dp)
                .offset(40.dp, 10.dp),
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(10.dp)
                .offset(40.dp, 10.dp),
            value = id,
            onValueChange = { id = it },
            label = { Text("USER") }
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(10.dp)
                .offset(40.dp, 0.dp),
            value = password,
            onValueChange = { password = it },
            label = { Text("password") }
        )

        Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.TopEnd
            ) {
            Button(onClick = {},
                modifier = Modifier.padding(5.dp)
                    .offset(-60.dp, 40.dp)
                    )
            {
                Text(
                    text = "LOGIN",
                    fontSize = 18.sp,

                )
            }

        }
    }
}