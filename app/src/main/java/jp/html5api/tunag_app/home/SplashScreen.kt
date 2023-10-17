package jp.html5api.tunag_app.home


import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import jp.html5api.tunag_app.R
import androidx.compose.ui.res.painterResource as painterResource1

@Composable
fun SplashScreen(onNext: () -> Unit = {}) {
    Log.d("****", "SplashScreen")

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff00aac2)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource1(id = R.drawable.logo),
            contentDescription = null)
    }
//    Handler(Looper.getMainLooper()).postDelayed(Runnable { onNext() }, 2000)
    onNext()

}