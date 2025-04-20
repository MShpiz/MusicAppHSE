package com.layka.musicapphse.screens.AlbumListScreen

data class AlbumListData(
    val id: Int,
    val name: String,
    val artists: List<String>,
    val cover: String? = null
)