package jp.html5api.tunag_app.home

import android.content.BroadcastReceiver
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.MainThread
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import jp.html5api.tunag_app.R
import jp.html5api.tunag_app.TunagApplication
import jp.html5api.tunag_app.data.FriendEntity
import jp.html5api.tunag_app.data.TalkEntity
import jp.html5api.tunag_app.data.db.AppDatabase
import jp.html5api.tunag_app.data.db.TalkDao
import jp.html5api.tunag_app.model.ApiRequest
import jp.html5api.tunag_app.model.ChangeNameRequest
import jp.html5api.tunag_app.model.ChangePasswordRequest
import jp.html5api.tunag_app.model.EditData
import jp.html5api.tunag_app.model.EditType
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
import java.nio.file.WatchEvent
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.coroutines.EmptyCoroutineContext


@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    companion object {
        const val PREF_NAME = "encrypted_prefs"
    }

    lateinit var broadcast:PushReceiver

    private val retrofit = Retrofit.Builder().apply {
//        baseUrl("http://10.0.2.2:8080/")
        baseUrl("http://192.168.0.3:8080/")
    }.build()
    private val service = retrofit.create(ApiService::class.java)
    private var friend: FriendEntity = FriendEntity(0, "", "")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getInstance(this)
        val talkDao = database.talkDao()

        broadcast = PushReceiver(talkDao)

        val me = (applicationContext as TunagApplication).getMe()
//                val apiRequest = ApiRequest("12345", "","0000")
//        val request = Gson().toJson(apiRequest)
        val requestBody =
            RequestBody.create(MediaType.parse("application/json"), "{\"user\":\"$me\"}")
        val post = service.getName(requestBody)
        val scope = CoroutineScope(EmptyCoroutineContext)
        scope.launch {
            try {
                val responseBody = post.execute()
                responseBody.body()?.let {
                    val strResponse: String = it.string()
                    val response = Gson().fromJson(strResponse, LoginResponse::class.java)
                    if (response.status == 0) {
                        Log.d("***", " user name:" + response.message);
                        val token =
                            (applicationContext as TunagApplication).saveMyName(response.message)

                    } else {
                        Handler(Looper.getMainLooper()).post{Toast.makeText(baseContext, response.message, Toast.LENGTH_LONG).show()}
                    }

                }
            } catch(e:Exception) {
                Handler(Looper.getMainLooper()).post{Toast.makeText(baseContext, e.message?:"エラー", Toast.LENGTH_LONG).show()}

            }
        }

        // token変更API
//        val apiRequest = ApiRequest("12345", "","0000")
//        val request = Gson().toJson(apiRequest)
//        val requestBody = RequestBody.create(MediaType.parse("application/json"), request)
//        val post = service.updateToken(requestBody)
//        val scope = CoroutineScope(EmptyCoroutineContext)
//        scope.launch {
//            val responseBody = post.execute()
//        }


        // テストデータ作成
//        insertTestData(talkDao)

        val friends = talkDao.getFriends()

        updateFCMToken()

        setContent {
            Log.d("****", "setContent")
            TUNAGAppTheme {
                val navController: NavHostController = rememberNavController()
                val startDestination by remember { mutableStateOf("splash") }
                NavHost(navController = navController, startDestination = startDestination) {
                    composable("splash") { SplashScreen() {
                        checkLogin(navController)
                      }}
                    composable("login") {
                        LoginScreen({ callLogin(it, navController) }, {navController.navigate("signUp")}) {
                            if (!navController.popBackStack()) {
                                finish()
                            }
                        }
                    }
                    composable("friends") {
                        FriendListScreen(friends,
                            { callTalk(it, navController) },
                            {
                                when (it) {
                                    1 -> {(applicationContext as TunagApplication).saveLogin(false)}
                                    else -> {}
                                }
                            }
                        )
                        {
                            when (it.procType) {
                                EditType.CHANGE_PASSWORD -> changePassword(it)
                                EditType.CHANGE_NAME -> changeName(it)
                                EditType.NONE -> {}
                            }
                        }
                    }
                    composable("signUp") { SignUpScreen({ callSignUp(it, navController) }) }
                    composable("talk") {
                        TalkScreen(talkDao, friend, { push(talkDao, it) }, {
                            if (!navController.popBackStack()) {
                                finish()
                            }
//                        Log.d("**", "size:"+navController.currentBackStack.value.size)
                        }) {
                            Log.d("***", "onEdit:" + it)
                        }
                    }
                    composable("tab") { TabScreen() }


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

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcast)
    }
    private fun insertTestData(talkDao: TalkDao) {
        val user = "abc"
        val insertList = listOf(
            TalkEntity(0, user, "", "2023/10/03 18:01:25", "わかった"),
            TalkEntity(0, user, user, "2023/10/03 18:05:11", "さっきの件"),
            TalkEntity(0, user, user, "2023/10/03 18:05:15", "忘れないようにね"),
            TalkEntity(0, user, "", "2023/10/03 18:25:11", "はいじゃあ明日"),
            TalkEntity(0, user, "", "2023/10/04 18:25:11", "あの後大変だったよ"),
            TalkEntity(0, user, user, "2023/10/04 22:17:11", "どした？"),
            TalkEntity(0, user, "", "2023/10/04 22:18:11", "いやぁ、歩いてたらハクビシンに\n突然噛まれた"),
            TalkEntity(0, user, user, "2023/10/04 22:19:11", "えぇぇぇ、まじかよ大丈夫なん"),
            TalkEntity(0, user, "", "2023/10/04 22:20:11", "うんへーき"),
            TalkEntity(0, user, user, "2023/10/04 22:21:11", "よかったねー"),
            TalkEntity(0, user, "", "2023/10/04 25:19:11", "指が3本もげただけ"),
            TalkEntity(0, user, user, "2023/10/04 22:24:11", "え？"),
            TalkEntity(0, user, user, "2023/10/04 25:19:11", "えええええええええええええええええ")
        )
        for (data in insertList) {
            talkDao.insert(data)
        }
    }

    private fun callSignUp(apiRequest: ApiRequest, navController: NavHostController) {

        val token = (applicationContext as TunagApplication).getToken()
        apiRequest.token = token
        val request = Gson().toJson(apiRequest)
        val requestBody = RequestBody.create(MediaType.parse("application/json"), request)

        val post = service.signUp(requestBody)
        val scope = CoroutineScope(EmptyCoroutineContext)
        scope.launch {
            try {
                val responseBody = post.execute()
                responseBody.body()?.let {
                    val strResponse: String = it.string()
                    val response = Gson().fromJson(strResponse, LoginResponse::class.java)
                    if (response.status == 0) {
                        Log.d("****2", "message:" + response.message)

                        (applicationContext as TunagApplication).saveMe(apiRequest.user)
                        (applicationContext as TunagApplication).saveLogin(true)

                        val scope = CoroutineScope(Job() + Dispatchers.Main)
                        scope.launch {
                            navController.popBackStack()
                            navController.navigate("talk")
                        }
                    } else {
                        Handler(Looper.getMainLooper()).post{Toast.makeText(baseContext, response.message, Toast.LENGTH_LONG).show()}
                    }

                }
            } catch(e:Exception) {
                Handler(Looper.getMainLooper()).post{Toast.makeText(baseContext, e.message?:"エラー", Toast.LENGTH_LONG).show()}

            }
        }
    }

    private fun checkLogin(navController: NavHostController) {
        Log.d("****", "checkLogin")
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            navController.popBackStack()
            if ((applicationContext as TunagApplication).isLogin()) {
                navController.navigate("friends")
            } else {
                navController.navigate("login")
            }

        }
    }
    private fun callTalk(_friend: FriendEntity, navController: NavHostController) {
        Log.d("***", "friend:" + _friend)

        friend = _friend
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            navController.navigate("talk")
        }

    }

    private fun callLogin(apiRequest: ApiRequest, navController: NavHostController) {
        Log.d("****", "callLogin")
        val request = Gson().toJson(apiRequest)
        val requestBody = RequestBody.create(MediaType.parse("application/json"), request)

        val post = service.login(requestBody)
        val scope = CoroutineScope(EmptyCoroutineContext)
        scope.launch {
            try {
                val responseBody = post.execute()
                responseBody.body()?.let {
                    val strResponse: String = it.string()
                    val loginResponse = Gson().fromJson(strResponse, LoginResponse::class.java)
                    if (loginResponse.status == 0) {
                        (applicationContext as TunagApplication).saveMe(apiRequest.user)
                        (applicationContext as TunagApplication).saveLogin(true)
                        val scope = CoroutineScope(Job() + Dispatchers.Main)
                        scope.launch {
                            navController.popBackStack()
                            navController.navigate("friends")
                        }
                    } else {
                        Handler(Looper.getMainLooper()).post{Toast.makeText(baseContext, loginResponse.message, Toast.LENGTH_LONG).show()}
                    }
                }
            } catch(e:Exception) {
                Handler(Looper.getMainLooper()).post{Toast.makeText(baseContext, e.message?:"エラー", Toast.LENGTH_LONG).show()}
            }
        }

    }

    private fun push(talkDao: TalkDao, talkEntity: TalkEntity) {
        talkDao.insert(talkEntity)
    }

    private fun changePassword(editData: EditData) {
        Log.d("***", "change password " + editData.oldPassword + " to " + editData.newPassword)
        val me = (applicationContext as TunagApplication).getMe()
        val request =
            Gson().toJson(ChangePasswordRequest(me, editData.oldPassword, editData.newPassword))
        val requestBody = RequestBody.create(MediaType.parse("application/json"), request)

        val post = service.updatePassword(requestBody)
        val scope = CoroutineScope(EmptyCoroutineContext)
        scope.launch {
            try {
                val responseBody = post.execute()
                responseBody.body()?.let {
                    val strResponse: String = it.string()
                    val loginResponse = Gson().fromJson(strResponse, LoginResponse::class.java)
                    Log.d("****", "msg:" + loginResponse.message);
                    if (loginResponse.status == 0) {
                        Log.d("***", "コングラチュレーション");
                        val scope = CoroutineScope(Job() + Dispatchers.Main)
                    }
                    Handler(Looper.getMainLooper()).post{Toast.makeText(baseContext, loginResponse.message, Toast.LENGTH_LONG).show()}
                }
            } catch(e:Exception) {
                Handler(Looper.getMainLooper()).post{Toast.makeText(baseContext, e.message?:"エラー", Toast.LENGTH_LONG).show()}

            }
        }

    }

    private fun changeName(editData: EditData) {
        Log.d("***", "change name " + editData.newName)
        val me = (applicationContext as TunagApplication).getMe()

        val request = Gson().toJson(ChangeNameRequest(me, editData.newName))
        val requestBody = RequestBody.create(MediaType.parse("application/json"), request)

        val post = service.updateName(requestBody)
        val scope = CoroutineScope(EmptyCoroutineContext)
        scope.launch {
            try {
                val responseBody = post.execute()
                responseBody.body()?.let {
                    val strResponse: String = it.string()
                    val loginResponse = Gson().fromJson(strResponse, LoginResponse::class.java)
                    if (loginResponse.status == 0) {
                        Log.d("***", "コングラチュレーション");
                        val scope = CoroutineScope(Job() + Dispatchers.Main)
                        scope.launch {
                        }
                    }
                    Handler(Looper.getMainLooper()).post{Toast.makeText(baseContext, loginResponse.message, Toast.LENGTH_LONG).show()}
                }
            } catch(e:Exception) {
                Handler(Looper.getMainLooper()).post{Toast.makeText(baseContext, e.message?:"エラー", Toast.LENGTH_LONG).show()}

            }
        }
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
//            Toast.makeText(baseContext, token, Toast.LENGTH_LONG).show()
            val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", token)
            (applicationContext as TunagApplication).saveToken(token)

        })
    }

    inner class PushReceiver(val talkDao: TalkDao) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("***", intent?.getStringExtra("user") ?: "")
            Log.d("***", intent?.getStringExtra("message") ?: "")
            val user = intent?.getStringExtra("user") ?: ""
            val name = intent?.getStringExtra("name") ?: ""
            val list = talkDao.getFriendsByName(user)
            Log.d("***", "size:" + list.size)


            if (list.size == 0) {
                talkDao.insert(FriendEntity(0, user, name))
            }
            val cal = Calendar.getInstance();
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            val result = sdf.format(cal.getTime())

            val data = TalkEntity(
                0,
                user,
                user,
                result,
                intent?.getStringExtra("message") ?: ""
            )
            talkDao.insert(data)

        }

    }

    @Composable
    fun TabScreen() {
        var tabIndex by remember { mutableStateOf(0) }

        val tabs = listOf("サンプル1", "サンプル2", "サンプル3")

        Column(modifier = Modifier.fillMaxWidth()) {
            TabRow(selectedTabIndex = tabIndex,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                containerColor = Color.White,
                contentColor = Color.Black,
                indicator = {tabPositions ->
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[tabIndex])
                            .height(3.dp)
                            .padding(start = 20.dp, end = 20.dp)
                            .background(Color(0xff796baf), RoundedCornerShape(100, 100, 0, 0))
                    )
                }
                ) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = {
                        Text(title,
                        color = if (index == tabIndex) Color.Black else Color.Gray,
                        fontWeight = if (index == tabIndex) FontWeight.Bold else FontWeight.Normal
                    )},
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        modifier = Modifier.background(if (index == tabIndex) Color.White else Color(0xfff8f8f8))
                    )
                }
            }
            when (tabIndex) {
                0 -> Test()
                1 -> Test2()
                2 -> Test3()
            }
        }
    }

    @Composable
    fun Test() {
        Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
            Spacer(modifier = Modifier.height(200.dp))
            Text("ああああ", fontSize = 20.sp, color = Color.Black)
            Text("いいいい", fontSize = 20.sp, color = Color.Magenta)
            Text("うううう", fontSize = 20.sp, color = Color.Blue)
            Text("ええええ", fontSize = 20.sp, color = Color.Red)
            Text("おおおお", fontSize = 20.sp, color = Color.Green)
        }
    }

    @Composable
    fun Test2() {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(50.dp))
            Text("かかかか", fontSize = 20.sp, color = Color.Blue)
            Text("きききき", fontSize = 20.sp, color = Color.Red)
            Text("くくくく", fontSize = 20.sp, color = Color.Black)
            Text("けけけけ", fontSize = 20.sp, color = Color.Green)
            Text("ここここ", fontSize = 20.sp, color = Color.Magenta)

        }
    }

    @Composable
    fun Test3() {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(100.dp))
            Text("ささささ", fontSize = 20.sp, color = Color.Magenta)
            Text("しししし", fontSize = 20.sp, color = Color.Green)
            Text("すすすす", fontSize = 20.sp, color = Color.Black)
            Text("せせせせ", fontSize = 20.sp, color = Color.Red)
            Text("そそそそ", fontSize = 20.sp, color = Color.Blue)
        }
    }

}


