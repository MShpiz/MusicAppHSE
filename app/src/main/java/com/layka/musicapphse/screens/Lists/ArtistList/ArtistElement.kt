package com.layka.musicapphse.screens.Lists.ArtistList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ArtistElement(data: ArtistData, onClick: () -> Unit) {
    Box(
        Modifier
            .height(20.dp)
            .fillMaxWidth()
            .clickable { onClick() }) {
        Text(
            text = data.name,
            fontSize = 18.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }

}