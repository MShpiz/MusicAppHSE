package com.layka.musicapphse.screens.Lists.PlaylistList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun PlaylistList(
    playLists: List<ShortPlaylistData>,
    navController: NavController,
    showCheckBox: Boolean = false,
    onCheckBoxChecked: (id: Int, check: Boolean) -> Unit = { id, check -> },
    enableClick: Boolean = true
) {
    val onclick =
    Column(modifier = Modifier.fillMaxWidth()) {
        playLists.forEachIndexed  { idx, playlist ->
            PlaylistItem(
                playlist,
                { if (enableClick) {
                    navController.navigate("playlist/${playlist.id}")
                } },
                showCheckBox,
                onCheckBoxChecked,

            )
        }
    }
}