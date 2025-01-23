package com.layka.musicapphse.screens.AlbumListScreen

import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData

data class AlbumListData (val id: Int,
                          val name: String,
                          val artists: List<String>,
                          val cover: String? = null)