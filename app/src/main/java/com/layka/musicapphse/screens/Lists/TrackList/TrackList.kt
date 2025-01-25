package com.layka.musicapphse.screens.Lists.TrackList

import androidx.compose.foundation.layout.fillMaxWidth
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
    LazyColumn (Modifier.fillMaxWidth()){
        itemsIndexed(trackData)  { idx, track ->

            TrackElement(
                id = track.trackId,
                name = track.trackName,
                artistName = track.artists.map { it.second },
                artistId = track.artists.map { it.first },
                duration = track.duration,
                index = idx + 1, // тут нужен номер трека
                showCover = showCover,
                showArtistName = (showArtistName || track.artists.size > 1) // список исполнителей будет показан если это необходимо или если у трека болше чем 1 исполнитель
            )
        }
    }
}