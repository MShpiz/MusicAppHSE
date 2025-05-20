package com.layka.musicapphse.screens.utils

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.layka.musicapphse.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    showScreenName: MutableState<Boolean>,
    screenName: String = "",
    showBackArrow: Boolean = true,
    topBarViewModel: TopBarViewModel = hiltViewModel()
) {
    val createPlaylistPopupOn = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val showToast = fun(text: String) {
        Toast.makeText(
            context, text, Toast.LENGTH_SHORT
        ).show()
    }
    val currentLocation = navController.currentBackStackEntryAsState()

    TopAppBar(
        navigationIcon = {
            if (showBackArrow) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.grey),
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            if (showScreenName.value)
                Text(screenName)
        },
        actions = {

            if (!currentLocation.value?.destination?.route.toString().startsWith("trackPlaylist/") ||
                !currentLocation.value?.destination?.route.toString().startsWith("settings_screen")) {
                IconButton(onClick = { createPlaylistPopupOn.value = true }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.library_add),
                        contentDescription = "Localized description"
                    )
                }
            }

            IconButton(onClick = { navController.navigate("settings_screen") }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.settings),
                    contentDescription = "Localized description"
                )
            }
        }
    )
    if (createPlaylistPopupOn.value) {
        CreatePlaylistPopUp({ createPlaylistPopupOn.value = false },
            { name: String, description: String ->
                topBarViewModel.createPlayList(name, description, showToast, navController)
                createPlaylistPopupOn.value = false
            }
        )
    }
}

@Composable
private fun CreatePlaylistPopUp(
    onDismissRequest: () -> Unit,
    onConfirmation: (String, String) -> Unit
) {
    val playlistName = remember { mutableStateOf("") }
    val playlistDescription = remember { mutableStateOf("") }
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 5.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = R.string.create_playlist),
                    fontSize = 20.sp
                )
                TextField(
                    value = playlistName.value,
                    onValueChange = { playlistName.value = it },
                    label = { Text(stringResource(id = R.string.name)) },
                    maxLines = 1,
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 1.dp)
                )
                TextField(
                    value = playlistDescription.value,
                    onValueChange = { playlistDescription.value = it },
                    label = { Text(stringResource(id = R.string.description)) },
                    maxLines = 10,
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 1.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(id = R.string.cancel))
                    }
                    TextButton(
                        onClick = { onConfirmation(playlistName.value, playlistDescription.value) },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(id = R.string.create))
                    }
                }
            }
        }
    }
}