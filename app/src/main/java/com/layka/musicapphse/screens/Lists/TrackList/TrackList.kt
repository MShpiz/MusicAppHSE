package com.layka.musicapphse.screens.Lists.TrackList

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun TrackList(
    trackData: List<MusicTrackData>,
    showCover: Boolean,
    showArtistName: Boolean = false
) {
    LazyColumn (Modifier.fillMaxHeight()){
        itemsIndexed(trackData)  { idx, track ->
            val stringDuration = "${track.duration / 60}:${track.duration % 60}"
            TrackElement(
                id = track.trackId,
                name = track.trackName,
                artistName = track.artists.map { it.second },
                artistId = track.artists.map { it.first },
                duration = stringDuration,
                index = idx + 1, // тут нужен номер трека
                showCover = showCover,
                showArtistName = (showArtistName || track.artists.size > 1) // список исполнителей будет показан если это необходимо или если у трека болше чем 1 исполнитель
            )
        }
    }
}