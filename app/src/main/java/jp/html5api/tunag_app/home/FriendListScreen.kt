package jp.html5api.tunag_app.home

import android.hardware.camera2.params.ColorSpaceTransform
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.composableLambdaInstance
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.html5api.tunag_app.R
import jp.html5api.tunag_app.TunagApplication
import jp.html5api.tunag_app.data.FriendEntity
import jp.html5api.tunag_app.model.EditData
import jp.html5api.tunag_app.model.EditType
import kotlinx.coroutines.launch

enum class DialogType {
    NONE,
    CHANGE_PASSWORD,
    CHANGE_NAME
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendListScreen(
    friends: MutableList<FriendEntity>,
    onFriendClick: (Int) -> Unit = { _ -> },
    onDrawerItem: (Int) -> Unit = {},
    onEdit: (EditData) -> Unit = { _ -> }
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(

        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet ( drawerContainerColor = Color(0xfff8f4e6)){
//                Text("ショートカット", color = Color.Gray, modifier = Modifier.padding(16.dp), fontSize = 18.sp, fontWeight = FontWeight.Bold)
//                Divider()

                Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                    Box (contentAlignment = Alignment.Center){
                        Image(painter = painterResource(id = R.drawable.logo), contentDescription = null)
                        Text("Shortcut", fontSize = 30.sp, color = Color(0xff00aac2))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(

                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(20.dp, 0.dp)
                            .border(width = 1.dp, shape = CircleShape, color = Color(0xffffb6c1))
                            .clickable {
                                onDrawerItem(1)
                                scope.launch { drawerState.close() }
                            }
                    ) {
                        Row() {
                            Image(painter = painterResource(id = android.R.drawable.ic_delete), contentDescription = null)
                            Text(
                                text = "Logout",
                                color = Color.Gray,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(20.dp, 0.dp)
                            .border(width = 1.dp, shape = CircleShape, color = Color.Gray)
                            .clickable {
//                                onDrawerItem(1)
                                scope.launch { drawerState.close() }
                            }
                    ) {
                        Row() {
                            Image(painter = painterResource(id = android.R.drawable.ic_dialog_email), contentDescription = null)
                            Text(
                                text = "なんかの機能１",
                                color = Color.Gray,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(20.dp, 0.dp)

                            .border(width = 1.dp, shape = CircleShape, color = Color.Gray)
                            .clickable {
//                                onDrawerItem(1)
                                scope.launch { drawerState.close() }
                            }
                    ) {
                        Row() {
                            Image(painter = painterResource(id = android.R.drawable.ic_input_get), contentDescription = null)
                            Text(
                                text = "なんかの機能2",
                                color = Color.Gray,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
//                NavigationDrawerItem(
//                    modifier = Modifier.padding(10.dp, 0.dp).,
//
//                    label = { Text(text = "Logout", fontSize = 16.sp) },
//                    selected = false,
//                    onClick = {
//                        onDrawerItem(1)
//                        scope.launch { drawerState.close() }
//                    }
//                )
//                NavigationDrawerItem(
//                    modifier = Modifier.padding(10.dp, 0.dp),
//                    label = { Text(text = "なんかの機能", fontSize = 16.sp) },
//                    selected = false,
//                    onClick = {
//                        scope.launch { drawerState.close() }
//                    }
//                )
//                NavigationDrawerItem(
//                    modifier = Modifier.padding(10.dp, 0.dp),
//                    label = { Text(text = "なんかの機能", fontSize = 16.sp) },
//                    selected = false,
//                    onClick = {
//                        scope.launch { drawerState.close() }
//                    }
//                )
            }

        }
    ) {
        var showDialog by remember { mutableStateOf(DialogType.NONE) }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                var expanded by remember { mutableStateOf(false) }
                ShowTopAppBarWithMenuDrawer(
                    title = stringResource(id = R.string.header_friend_list),
                    {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                ) { expanded = !expanded }


                if (expanded) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.TopEnd)
                            .offset((-1).dp, 0.dp)
                    ) {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                            },
                            Modifier.background(Color.White),
                        ) {

                            DropdownMenuItem(
                                onClick = {
                                    expanded = false
                                    showDialog = DialogType.CHANGE_NAME
                                },
                                interactionSource = MutableInteractionSource(),
                                text = {
                                    Text("名前変更", color = Color.Black)
                                }
                            )

                            DropdownMenuItem(
                                onClick = {
                                    expanded = false
                                    showDialog = DialogType.CHANGE_PASSWORD
                                },
                                interactionSource = MutableInteractionSource(),
                                text = {
                                    Text("パスワード変更", color = Color.Red)
                                }
                            )
                        }
                    }
                }


                LazyColumn(
                    userScrollEnabled = true,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1.0f)
                        .background(Color(0xfffafff3)),
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                    contentPadding = PaddingValues(5.dp)
                ) {
                    itemsIndexed(friends) { index, friend ->
                        OutlinedCard(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xfff8f4e6),
                            ),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                                .height(70.dp),
                            border = BorderStroke(1.dp, Color.Gray),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            onClick = { onFriendClick(index) },


                            ) {
                            Row(
                                modifier = Modifier
                                    .offset(10.dp, 0.dp)
                                    .fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.dog),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .offset(0.dp, 0.dp),

                                    )
                                Text(
                                    friend.name,
                                    modifier = Modifier
                                        .padding(1.dp)
                                        .offset(10.dp, 0.dp),
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Light, textAlign = TextAlign.Center
                                    ),
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }

                    }
                }
            }
        }
        when (showDialog) {
            DialogType.CHANGE_PASSWORD -> ShowChangePasswordDialog() {
                showDialog = DialogType.NONE
                if (!it.isChange) {
                    return@ShowChangePasswordDialog
                }
                onEdit(EditData(EditType.CHANGE_PASSWORD, it.oldPassword, it.newPassword, ""))

            }

            DialogType.CHANGE_NAME -> {
                val myName = TunagApplication.prefs.getString("myName", "") ?: ""
                ShowChangeNameDialog(myName) {
                    showDialog = DialogType.NONE
                    if (it == "") {
                        return@ShowChangeNameDialog
                    }
                    onEdit(EditData(EditType.CHANGE_NAME, "", "", it))
                }
            }

            DialogType.NONE -> {}
        }

    }
}