package jp.html5api.tunag_app.home

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import jp.html5api.tunag_app.ui.theme.TUNAGAppTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.html5api.tunag_app.R
import kotlin.coroutines.coroutineContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TUNAGAppTheme {
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }


                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "chat") {
                    composable("splash") { Splash(/*...*/) }
                    composable("login") { Login(/*...*/) }
                    composable("signUp") { SignUp(/*...*/) }
                    composable("chat") { Chat(/*...*/) }
                }
            }
        }

        val CHANNEL_ID = "channel_id"
        val channel_name = "channel_name"
        val channel_description = "channel_description "

        ///APIレベルに応じてチャネルを作成

        val name = channel_name
        val descriptionText = channel_description
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        /// チャネルを登録
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)


        /// 通知の中身
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)    /// 表示されるアイコン
            .setContentTitle("PUSHタイトル")                  /// 通知タイトル
            .setContentText("メッセージ1234567890")           /// 通知コンテンツ
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)   /// 通知の優先度


        var notificationId = 0   /// notificationID
//        pushBtn.setOnClickListener {
        /// ボタンを押して通知を表示
        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
                notify(notificationId, builder.build())
            notificationId += 1
        }
//            }
    }
}

fun push() {

}

@Preview(showBackground = true)
@Composable
fun TunagPreview() {
    TUNAGAppTheme {
        Login()
    }
}