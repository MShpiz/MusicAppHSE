package com.layka.musicapphse.screens.Lists.AlbumList

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.layka.musicapphse.screens.AlbumListScreen.AlbumListData

@Composable
fun AlbumVerticalList(albums: List<AlbumListData>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(albums) {
            AlbumElement(name = it.name, artistName = it.artists)
        }
    }
}