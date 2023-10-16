package jp.html5api.tunag_app.home

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowTopAppBar(title: String, onUpClick: () -> Unit = {}) {
    CenterAlignedTopAppBar(
        title = ({ Text(title, color = Color.White) }),
        navigationIcon = {
            IconButton(onClick = { onUpClick() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color(0xff00aac2)),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowTopAppBarWithMenu(
    title: String,
    onUpClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = ({ Text(title, color = Color.White) }),
        navigationIcon = {
            IconButton(
                onClick = { onUpClick() }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = { onEditClick() }) {
                Icon(
                    Icons.Default.Edit, contentDescription = "Edit text",
                    tint = Color.White
                )
            }

        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color(0xff00aac2)),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowTopAppBarWithMenuDrawer(
    title: String,
    onHamburgerClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = ({ Text(title, color = Color.White) }),
        navigationIcon = {
            IconButton(
                onClick = { onHamburgerClick() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Open drawer",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = { onEditClick() }) {
                Icon(
                    Icons.Default.Edit, contentDescription = "Edit text",
                    tint = Color.White
                )
            }

        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color(0xff00aac2)),
    )
}