package com.layka.musicapphse.screens.Lists.ArtistList

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ArtistList(artistData: List<ArtistData>, navController: NavController) {

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(artistData) {
            ArtistElement(data = it, onClick = {
                navController.navigate("artist/${it.name}")
            })
        }
    }
}