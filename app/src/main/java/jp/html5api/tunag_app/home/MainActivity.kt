package jp.html5api.tunag_app.home

import android.content.BroadcastReceiver
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import jp.html5api.tunag_app.R
import jp.html5api.tunag_app.data.TalkEntity
import jp.html5api.tunag_app.data.db.AppDatabase
import jp.html5api.tunag_app.data.db.TalkDao
import jp.html5api.tunag_app.model.ApiRequest
import jp.html5api.tunag_app.model.LoginResponse
import jp.html5api.tunag_app.service.ApiService
import jp.html5api.tunag_app.ui.theme.TUNAGAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Retrofit
import kotlin.coroutines.EmptyCoroutineContext


@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {


    private val retrofit = Retrofit.Builder().apply {
        baseUrl("http://10.0.2.2:8080/")
    }.build()
    private val service = retrofit.create(ApiService::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        updateFCMToken()
        registerReceiver(PushReceiver(), IntentFilter(getString(R.string.intent_push)))

        val database = AppDatabase.getInstance(this)
        val talkDao = database.talkDao()

        // テストデータ作成
//        insertTestData(talkDao)

        val talkList = talkDao.getTalk()

        setContent {
            TUNAGAppTheme {


                val navController: NavHostController = rememberNavController()
                NavHost(navController = navController, startDestination = "talk") {
                    composable("splash") { SplashScreen(/*...*/) }
                    composable("login") { LoginScreen({callLogin(it, navController)}) {
                        if (!navController.popBackStack()) {
                            finish()
                        }
//                        Log.d("**", "size:"+navController.currentBackStack.value.size)
                    } }
                    composable("signUp") { SignUpScreen({callSignUp(it, navController)}) }
                    composable("talk") { TalkScreen(talkList,  {push(talkDao, it)}) {
                        if (!navController.popBackStack()) {
                            finish()
                        }
//                        Log.d("**", "size:"+navController.currentBackStack.value.size)
                    } }
                }
            }
        }

//        val requestBody = RequestBody.create(mediaType, json)

//        val CHANNEL_ID = "channel_id"
//        val channel_name = "channel_name"
//        val channel_description = "channel_description "
//
//        /* /APIレベルに応じてチャネルを作成 */
//
//        val name = channel_name
//        val descriptionText = channel_description
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//            description = descriptionText
//        }
//        /// チャネルを登録
//        val notificationManager: NotificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//
//
//        /// 通知の中身
//        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_launcher_background)    /// 表示されるアイコン
//            .setContentTitle("PUSHタイトル")                  /// 通知タイトル
//            .setContentText("メッセージ1234567890")           /// 通知コンテンツ
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)   /// 通知の優先度
//
//
//        var notificationId = 0   /// notificationID
////        pushBtn.setOnClickListener {
//        /// ボタンを押して通知を表示
//        with(NotificationManagerCompat.from(this)) {
//            if (ActivityCompat.checkSelfPermission(
//                    applicationContext,
//                    Manifest.permission.POST_NOTIFICATIONS
//                ) == PackageManager.PERMISSION_GRANTED
//            )
//                notify(notificationId, builder.build())
//            notificationId += 1
//        }
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

    private fun callSignUp(apiRequest: ApiRequest, navController: NavHostController) {
        val request = Gson().toJson(apiRequest)
        val requestBody = RequestBody.create(MediaType.parse("application/json"), request)

        val post = service.signUp(requestBody)
        val scope = CoroutineScope(EmptyCoroutineContext)
        scope.launch {
            val responseBody = post.execute()
            responseBody.body()?.let {
                val strResponse: String = it.string()
                val response = Gson().fromJson(strResponse, LoginResponse::class.java)
                if (response.status == 0) {
                    Log.d("****", "message:" + response.message)
                    val scope = CoroutineScope(Job() + Dispatchers.Main)
                    scope.launch {
                        navController.popBackStack()
                        navController.navigate("talk")
                    }
                } else {
                    Log.d("****", "message:" + response.message)
                }

            }
        }
    }

    private fun callLogin(apiRequest: ApiRequest, navController: NavHostController) {
//        val apiRequest = ApinRequest().also {
//            it.user = "user"
//            it.pass = "123456"
//        }
//        val apiRequest = ApiRequest("user", "123456")
        val request = Gson().toJson(apiRequest)
        val requestBody = RequestBody.create(MediaType.parse("application/json"), request)

        val post = service.login(requestBody)
        val scope = CoroutineScope(EmptyCoroutineContext)
        scope.launch {
            val responseBody = post.execute()
            responseBody.body()?.let {
                val strResponse: String = it.string()
                val loginResponse = Gson().fromJson(strResponse, LoginResponse::class.java)
                if (loginResponse.status == 0) {
                    val scope = CoroutineScope(Job() + Dispatchers.Main)
                    scope.launch {
                        navController.popBackStack()
                        navController.navigate("talk")
                    }
                }

            }
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

    private fun updateFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FIREBASE", "getInstanceId failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result

            // 以下通知テスト用にFCMトークンを表示、コピーする為の記述
            Toast.makeText(baseContext,token, Toast.LENGTH_LONG).show()
            val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", token)
            clipboardManager.setPrimaryClip(clipData)
        })
    }

    inner class PushReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("***", intent?.getStringExtra("user")?:"")
            Log.d("***", intent?.getStringExtra("message")?:"")

        }

    }

}



