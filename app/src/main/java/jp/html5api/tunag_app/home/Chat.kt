package jp.html5api.tunag_app.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import jp.html5api.tunag_app.R
import jp.html5api.tunag_app.data.Talk
import java.lang.Exception


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chat() {

    val talks = listOf(
        Talk("", "わかった", "2023/10/03 18:01:25.000"),
        Talk("1", "さっきの件", "2023/10/03 18:05:11.000"),
        Talk("1", "忘れないようにね", "2023/10/03 18:05:15.000"),
        Talk("", "はいじゃあ明日", "2023/10/03 18:25:11.000"),
//        Talk("2", "2023年10月4日"),
        Talk("", "あの後大変だったよ", "2023/10/04 22:15:11.000"),
        Talk("1", "どした？", "2023/10/04 22:17:11.000"),
        Talk("", "いやぁ、歩いてたらハクビシンに\n突然噛まれた", "2023/10/04 22:18:11.000"),
        Talk("1", "えぇぇぇ、まじかよ大丈夫なん", "2023/10/04 22:19:11.000"),
        Talk("", "うんへーき", "2023/10/04 22:20:11.000"),
        Talk("1", "よかったねー", "2023/10/04 22:21:11.000"),
        Talk("", "指が3本もげただけ", "2023/10/04 25:19:11.000"),
        Talk("1", "え？", "2023/10/04 22:24:18.000"),
        Talk("1", "えええええええええええええええええ", "2023/10/04 25:19:11.000")
        )
    val bgImg = ContextCompat.getDrawable(
        LocalContext.current,
        R.drawable.wfukidashi
    )
    var inputMessage by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        showTopAppBar(title = stringResource(id = R.string.header_title_talk))
        LazyColumn(
            userScrollEnabled = true,
            modifier = Modifier
                .fillMaxSize()
                .weight(1.0f)
                .background(colorResource(id = R.color.background)),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(5.dp)
        ) {
            itemsIndexed(talks) { index, talk ->
                if (index == 0 || talks[index - 1].createDate.take(10) != talks[index].createDate.take(10)) {
                    DrawTime(message = talk.createDate.take(10))
                }
                when (talk.user) {
                    "" -> TalkToYou(message = talk.message)
                    else -> TalkToMe(message = talk.message)
                }
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.TopEnd
//                ) {
//                    Text(text = talk.message, modifier = Modifier
//                        .drawBehind {
//                            drawRoundRect(
//                                Color(0xff5555ee),
//                                cornerRadius = CornerRadius(10.dp.toPx())
//                            )
//                        }
//                        .padding(10.dp),
//                        color = Color.White
//
//                    )
//                    Image(painter = painterResource(id = R.drawable.fukidashi), contentDescription = "",
//                        )
//                    Text(text = talk.message,
//
//                        modifier = Modifier
//                            .drawBehind {
//                                bgImg?.updateBounds(
//                                    -20,
//                                    -15,
//                                    (size.width * 1.0).toInt(),
//                                    (size.height * 1.0).toInt() + 20
//                                )
//                                bgImg?.draw(drawContext.canvas.nativeCanvas)
//                            }
//                            .offset(20.dp, 5.dp))


            }
        }
        Row (verticalAlignment = Alignment.CenterVertically)
        {
            Text(stringResource(id = R.string.input_message))
            OutlinedTextField(
                value = inputMessage,
                onValueChange = { inputMessage = it },
                modifier = Modifier.weight(1.0f))
        }
    }
}


@Composable
fun TalkToYou(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopEnd
    ) {
        Text(text = message, modifier = Modifier
            .offset((-3).dp, 0.dp)
            .drawBehind {
                drawRoundRect(
                    Color(0x77cccccc),
                    cornerRadius = CornerRadius(10.dp.toPx())
                )
            }
            .padding(10.dp),
            color = Color.Black,
            fontSize = 14.sp
        )
    }
}

@Composable
fun TalkToMe(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = message, modifier = Modifier
            .offset(3.dp, 0.dp)
            .drawBehind {
                drawRoundRect(
                    Color(0xff5555ee),
                    cornerRadius = CornerRadius(10.dp.toPx())
                )
            }
            .padding(10.dp),
            color = Color.White,
            fontSize = 14.sp
        )
    }
}

@Composable
fun DrawTime(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
                ) {
        var dt = ""
        var i = 0
        val ymd = listOf("年", "月", "日")
        try {
            message.split("/").map{dt += it + ymd[i++]}
        } catch (e: Exception) {
//            dt = "error"
            // せっかくなので年や月までとかでも表示する
            /* no-op */
        }

        Text("---------- $dt ----------",
            fontSize = 12.sp,
            color = Color.Magenta)
    }
}
