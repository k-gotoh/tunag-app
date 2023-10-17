package jp.html5api.tunag_app.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerAnimateToItem() {
    Box {
        // [START android_compose_layouts_pager_scroll_animate]
        val pagerState = rememberPagerState(pageCount = {
            10
        })

        HorizontalPager(state = pagerState) { page ->
            // Our page content
            Text(
                text = "Page: $page",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
        }

        // scroll to page
        val coroutineScope = rememberCoroutineScope()
        Button(onClick = {
            coroutineScope.launch {
                // Call scroll to on pagerState
                pagerState.animateScrollToPage(5)
            }
        }, modifier = Modifier.align(Alignment.BottomCenter)) {
            Text("Jump to Page 5")
        }
        // [END android_compose_layouts_pager_scroll_animate]
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator2() {
    Box {
        // [START android_compose_pager_indicator]
        val pageCount = 10
        val pagerState = rememberPagerState(pageCount = {
            10
        })
        HorizontalPager(
            state = pagerState
        ) { page ->
            // Our page content
//            Text(
//                text = "Page: $page",
//                modifier = Modifier
//                    .fillMaxSize()
//            )

        }
//        Row(
//            Modifier
//                .height(50.dp)
//                .fillMaxWidth()
//                .align(Alignment.BottomCenter),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            repeat(pageCount) { iteration ->
//                val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
//                Box(
//                    modifier = Modifier
//                        .padding(2.dp)
//                        .clip(CircleShape)
//                        .background(color)
//                        .size(20.dp)
//
//                )
//            }
//        }
        // [END android_compose_pager_indicator]
    }

}