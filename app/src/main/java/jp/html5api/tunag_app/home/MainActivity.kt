package jp.html5api.tunag_app.home

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import jp.html5api.tunag_app.ui.theme.TUNAGAppTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.html5api.tunag_app.R
import jp.html5api.tunag_app.data.TalkEntity
import jp.html5api.tunag_app.data.db.AppDatabase
import jp.html5api.tunag_app.data.db.TalkDao
import kotlin.coroutines.coroutineContext

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = AppDatabase.getInstance(this)
        val talkDao = database.talkDao()

        // テストデータ作成
//        insertTestData(talkDao)

        val talkList = talkDao.getTalk()
        setContent {
            TUNAGAppTheme {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "talk") {
                    composable("splash") { SplashScreen(/*...*/) }
                    composable("login") { LoginScreen() }
                    composable("signUp") { SignUpScreen() }
                    composable("talk") { TalkScreen(talkList) { push(talkDao, it) } }
                }
            }
        }


        val CHANNEL_ID = "channel_id"
        val channel_name = "channel_name"
        val channel_description = "channel_description "

        /* /APIレベルに応じてチャネルを作成 */

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

    private fun insertTestData(talkDao: TalkDao) {
        val insertList = listOf(
            TalkEntity(0, "", "", "2023/10/03 18:01:25", "わかった"),
            TalkEntity(0, "12345", "", "2023/10/03 18:05:11", "さっきの件"),
            TalkEntity(0, "12345", "", "2023/10/03 18:05:15", "忘れないようにね"),
            TalkEntity(0, "", "", "2023/10/03 18:25:11", "はいじゃあ明日"),
            TalkEntity(0, "", "", "2023/10/04 18:25:11", "あの後大変だったよ"),
            TalkEntity(0, "12345", "", "2023/10/04 22:17:11", "どした？"),
            TalkEntity(0, "", "", "2023/10/04 22:18:11", "いやぁ、歩いてたらハクビシンに\n突然噛まれた"),
            TalkEntity(0, "12345", "", "2023/10/04 22:19:11", "えぇぇぇ、まじかよ大丈夫なん"),
            TalkEntity(0, "", "", "2023/10/04 22:20:11", "うんへーき"),
            TalkEntity(0, "12345", "", "2023/10/04 22:21:11", "よかったねー"),
            TalkEntity(0, "", "", "2023/10/04 25:19:11", "指が3本もげただけ"),
            TalkEntity(0, "12345", "", "2023/10/04 22:24:11", "え？"),
            TalkEntity(0, "12345", "", "2023/10/04 25:19:11", "えええええええええええええええええ")
        )
        for (data in insertList) {
            talkDao.insert(data)
        }
    }

    private fun push(talkDao: TalkDao, talkEntity: TalkEntity) {
        talkDao.insert(talkEntity)
    }

    @Preview(showBackground = true)
    @Composable
    fun TunagPreview() {
        TUNAGAppTheme {
            LoginScreen()
        }
    }
}