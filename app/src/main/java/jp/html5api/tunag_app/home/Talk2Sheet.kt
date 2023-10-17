package jp.html5api.tunag_app.home


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.html5api.tunag_app.R
import jp.html5api.tunag_app.data.FriendEntity
import jp.html5api.tunag_app.data.TalkEntity
import jp.html5api.tunag_app.data.db.TalkDao
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Calendar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TalkPager(friends: List<FriendEntity>, currentIndex: Int, talkDao: TalkDao) {
    Box {
        // [START android_compose_pager_indicator]
        val pageCount = 10
        val pagerState = rememberPagerState(pageCount = {
            currentIndex
        })
        HorizontalPager(
            state = pagerState
        ) { page ->

            TalkS2creen(talkDao, friends[currentIndex], {}, {}){}

        }
    }

}

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TalkS2creen(
    talkDao: TalkDao,
    friend: FriendEntity,
    onTalkClick: (TalkEntity) -> Unit = { _ -> },
    onPopBack: () -> Unit = {},
    onEdit: (Int) -> Unit = { _ -> }
) {
    Log.d("****", "friend**:" + Int)
    val talks = talkDao.getTalkByRoom(friend.user)

    var inputMessage by remember { mutableStateOf("") }
    var editName by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            ShowTopAppBarWithMenu(title = friend.name, { onPopBack() }) { editName = true }

            val listState = rememberLazyListState()
            LaunchedEffect(talks.size) {
                listState.animateScrollToItem(talks.size)
            }

//        SystemBroadcastReceiver(stringResource(id = R.string.intent_push)) {
////            val scope = CoroutineScope(Job() + Dispatchers.Main)
////
////            scope.launch {
//
////            Log.d("***", "comp:" + it?.getStringExtra("user") ?: "")
////            Log.d("***", "comp:" + it?.getStringExtra("message") ?: "")
//            val cal = Calendar.getInstance();
//            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
//            val result = sdf.format(cal.getTime())
//            val id:Int = it?.getIntExtra("id", -1)?:-1
//            val data = TalkEntity(
//                0,
//                id,
//                id,
//                result,
//                it?.getStringExtra("message") ?: ""
//            )
//            onTalkClick(data)
//            talks.add(data)
////                talks.add(data)
////            }
//        }
//        val currentOnSystemEvent by rememberUpdatedState(onSystemEvent)
//        val systemAction = stringResource(id = R.string.intent_push)
//        val context = LocalContext.current
//        DisposableEffect(context, "systemAction") {
//            val intentFilter = IntentFilter(systemAction)
//            val broadcast = object : PushReceiver2() {
//                override fun onReceive(context: Context?, intent: Intent?) {
////                currentOnSystemEvent(intent)
//                    Log.d("***", "comp:"+ intent?.getStringExtra("user")?:"")
//                    Log.d("***", "comp:"+ intent?.getStringExtra("user")?:"")
//                    val cal = Calendar.getInstance();
//                    val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
//                    val result = sdf.format(cal.getTime())
//                    val data = TalkEntity(0, intent?.getStringExtra("user")?:"", "", result, intent?.getStringExtra("message")?:"")
////                    val scope = CoroutineScope(Job() + Dispatchers.Main)
////                    scope.launch {
//                    talks.add(data)
//                    onTalkClick(data)
////                    }
//
//                }
//            }
//
//            context.registerReceiver(broadcast, intentFilter)
//
//            // When the effect leaves the Composition, remove the callback
//            onDispose {
//                context.unregisterReceiver(broadcast)
//            }
//        }


            LazyColumn(
                state = listState,
                userScrollEnabled = true,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1.0f)
                    .background(colorResource(id = R.color.background)),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(5.dp)
            ) {
                itemsIndexed(talks) { index, talk ->
                    if (index == 0 || talks[index - 1].dt_talk.take(10) != talks[index].dt_talk.take(
                            10
                        )
                    ) {
                        DrawTime2(message = talk.dt_talk.take(10))
                    }
                    when (talk.person) {
                        "" -> TalkToYou2(talk = talk)
                        else -> TalkToMe2(talk = talk)
                    }

                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.background(Color(0xff00aac2))
            )
            {

                OutlinedTextField(
                    value = inputMessage,
                    onValueChange = { inputMessage = it },
                    modifier = Modifier
                        .weight(1.0f)
                        .background(Color.White),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        containerColor = Color.White
                    ),
                )

                ClickableText(
                    text = AnnotatedString(
                        stringResource(id = R.string.input_message),
                    ),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    onClick = {
                        val cal = Calendar.getInstance();
                        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                        val result = sdf.format(cal.getTime())
                        val data = TalkEntity(0, friend.user, "", result, inputMessage)
                        talks.add(data)
                        onTalkClick(data)
                        inputMessage = ""
                    },
                    maxLines = 1,

                    )
            }
        }
        if (editName) {
            Box(modifier = Modifier.fillMaxSize().background(Color(0xad000000)).clickable { editName = false },
                contentAlignment = Alignment.Center,
            ) {
                OutlinedCard(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,

                        ),

                    modifier = Modifier
                        .wrapContentHeight(unbounded = true)
                        .padding(20.dp, 0.dp),
                    border = BorderStroke(0.5.dp, Color(0xfff1f1f1)),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 20.dp
                    )
                ) {
                    var changeName by remember { mutableStateOf(friend.name) }
                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "名前を変更してね",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(20.dp, 20.dp),

                                )

                        }
                        Divider(
                            color = Color(0xffd0d0d0),
                            modifier = Modifier.height(1.dp),

                            )
                        Spacer(modifier = Modifier.height(20.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp, 20.dp)
                                .offset(0.dp, (-20).dp),
                            value = changeName,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                containerColor = Color.White
                            ),
                            maxLines = 1,
                            onValueChange = { changeName = it },
                            label = {
                                Text(
                                    "名前を入れて",
                                    color = Color.Gray
                                )
                            }
                        )
                        Box(
                            Modifier
                                .fillMaxSize()
                                .offset((-20).dp, (-20).dp), contentAlignment = Alignment.TopEnd) {
                            Row {
                                Button(
                                    onClick = {
                                        editName = false
                                    },
                                    modifier = Modifier
                                        .padding(5.dp),
                                    colors = ButtonDefaults.textButtonColors(
                                        containerColor = Color(0xfff1f1f1),
                                        contentColor = Color.Red,
                                        disabledContentColor = Color.LightGray
                                    )
                                )
                                {
                                    Text(
                                        text = "中止",
                                        fontSize = 18.sp,
                                    )
                                }
                                Button(
                                    onClick = {
                                        friend.name = changeName
                                        talkDao.updateName(friend.user, changeName)
                                        editName = false
                                    },
                                    modifier = Modifier
                                        .padding(5.dp),
                                    colors = ButtonDefaults.textButtonColors(
                                        containerColor = Color(0xff00aac2),
                                        contentColor = Color.White,
                                        disabledContentColor = Color.LightGray
                                    )
                                )
                                {
                                    Text(
                                        text = "変更",
                                        fontSize = 18.sp,
                                    )
                                }

                            }
                        }
                    }

                }
            }
        }

    }
}


@Composable
private fun TalkToYou2(talk: TalkEntity) {
    Column() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd
        ) {
            Text(text = talk.message,
                modifier = Modifier
                    .offset((-3).dp, 0.dp)
                    .drawBehind {
                        drawRoundRect(
                            Color(0x77cccccc),
                            cornerRadius = CornerRadius(10.dp.toPx())
                        )
                    }
                    .padding(10.dp),
                color = Color.Black,
                fontSize = 16.sp
            )

        }
        if (talk.dt_talk.split(" ").size == 2) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopEnd
            ) {
                Text(
                    talk.dt_talk.split(" ")[1],
                    modifier = Modifier.offset((-6).dp, 0.dp),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
private fun TalkToMe2(talk: TalkEntity) {
    Column() {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(text = talk.message, modifier = Modifier
                .offset(3.dp, 0.dp)
                .drawBehind {
                    drawRoundRect(
                        Color(0xff5555ee),
                        cornerRadius = CornerRadius(10.dp.toPx())
                    )
                }
                .padding(10.dp),
                color = Color.White,
                fontSize = 16.sp
            )
        }
        if (talk.dt_talk.split(" ").size == 2) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(
                    talk.dt_talk.split(" ")[1],
                    modifier = Modifier.offset((6).dp, 0.dp),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
private fun DrawTime2(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var dt = ""
        var i = 0
        val ymd = listOf("年", "月", "日")
        try {
            message.split("/").map { dt += it + ymd[i++] }
        } catch (e: Exception) {
//            dt = "error"
            // せっかくなので年や月までとかでも表示する
            /* no-op */
        }

        Text(
            "---------- $dt ----------",
            fontSize = 12.sp,
            color = Color.Magenta
        )

    }

}

//@Composable
//fun SystemBroadcastReceiver(
//    systemAction: String,
//    onSystemEvent: (intent: Intent?) -> Unit
//) {
//    // Grab the current context in this part of the UI tree
//    val context = LocalContext.current
//
//    // Safely use the latest onSystemEvent lambda passed to the function
//    val currentOnSystemEvent by rememberUpdatedState(onSystemEvent)
//
//    // If either context or systemAction changes, unregister and register again
//    DisposableEffect(context, systemAction) {
//        val intentFilter = IntentFilter(systemAction)
//        val broadcast = object : BroadcastReceiver() {
//            override fun onReceive(context: Context?, intent: Intent?) {
//                currentOnSystemEvent(intent)
//            }
//        }
//
//        context.registerReceiver(broadcast, intentFilter)
//
//        // When the effect leaves the Composition, remove the callback
//        onDispose {
//            context.unregisterReceiver(broadcast)
//        }
//    }
//}
//
//open class PushReceiver2 : BroadcastReceiver() {
//    override fun onReceive(context: Context?, intent: Intent?) {
//        Log.d("***", intent?.getStringExtra("user") ?: "")
//        Log.d("***", intent?.getStringExtra("message") ?: "")
//
//    }
//
//}
