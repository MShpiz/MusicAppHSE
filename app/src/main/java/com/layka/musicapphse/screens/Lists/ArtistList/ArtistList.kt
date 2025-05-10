package com.layka.musicapphse.screens.Lists.ArtistList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ArtistList(artistData: List<ArtistData>, navController: NavController) {

    Column(modifier = Modifier.fillMaxWidth()) {
        artistData.forEach {
            ArtistElement(data = it, onClick = {
                navController.navigate("artist/${it.name}")
            })
        }
    }
}