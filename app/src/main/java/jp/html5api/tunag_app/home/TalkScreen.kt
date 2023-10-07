package jp.html5api.tunag_app.home

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import jp.html5api.tunag_app.R
import jp.html5api.tunag_app.data.TalkEntity
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Calendar


@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TalkScreen(talks: MutableList<TalkEntity>, onTalkClick: (TalkEntity) -> Unit = { _ -> }) {


    val bgImg = ContextCompat.getDrawable(
        LocalContext.current,
        R.drawable.wfukidashi
    )
    var inputMessage by remember { mutableStateOf("") }
//    var talkList = talks


    Column(modifier = Modifier.fillMaxSize()) {
        showTopAppBar(title = stringResource(id = R.string.header_title_talk))
        val listState = rememberLazyListState()
        val a = rememberUpdatedState(newValue = talks.size)

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
                if (index == 0 || talks[index - 1].dt_talk.take(10) != talks[index].dt_talk.take(10)) {
                    DrawTime(message = talk.dt_talk.take(10))
                }
                when (talk.sent_user_id) {
                    "" -> TalkToYou(message = talk.message)
                    else -> TalkToMe(message = talk.message)
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
                    .background(Color.White)
            )
            ClickableText(
                text = AnnotatedString(
                    stringResource(id = R.string.input_message)
                ),
                onClick = {
                    val cal = Calendar.getInstance();
                    val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                    val result = sdf.format(cal.getTime())
                    val data = TalkEntity(0, "me", "", result, inputMessage)
                    talks.add(data)
                    // todo どうやって表示を動的に変更するか？

                    onTalkClick(data)
                }

            )
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
