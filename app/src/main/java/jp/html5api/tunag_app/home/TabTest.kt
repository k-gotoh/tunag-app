package jp.html5api.tunag_app.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import jp.html5api.tunag_app.ui.theme.TUNAGAppTheme

@Composable
fun ShowTab() {
    var selectedTabIndex by remember { mutableStateOf(0) }

    TabRow(
        selectedTabIndex = selectedTabIndex,

//        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Tab(
            selected = selectedTabIndex == 0,
            onClick = {
                selectedTabIndex = 0
            },
            text = {
                Text(
                    text = "タブ１",
                    fontWeight = FontWeight.Bold,
                    color = if (selectedTabIndex == 0) Color.Blue else Color.Red
                )
            }
        )
        Tab(
            selected = selectedTabIndex == 1,
            onClick = {
                selectedTabIndex = 1
            },
            text = {
                Text(
                    text = "タブ２",
                    fontWeight = FontWeight.Bold,
                    color = if (selectedTabIndex == 1) Color.Blue else Color.Red
                )
            }
        )
    }

}

