package com.layka.musicapphse.storage

import com.layka.musicapphse.screens.AlbumScreen.PlaylistData
import com.layka.musicapphse.screens.Lists.ArtistList.ArtistData
import com.layka.musicapphse.screens.Lists.PlaylistList.ShortPlaylistData
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import com.layka.musicapphse.storage.httpRepo.returnTypes.ArtistPlays
import com.layka.musicapphse.storage.httpRepo.returnTypes.ProfileResponse
import com.layka.musicapphse.storage.httpRepo.returnTypes.TrackPlays


class LocalRepository : MusicRepository {
    override suspend fun getTrackInfo(trackId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getAlbum(albumName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTracks(): List<MusicTrackData> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrack(trackId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayList(playlistId: Int): PlaylistData {
        TODO("Not yet implemented")
    }

    override suspend fun createPlayList(name: String, description: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updatePlayList(name: String, description: String, id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deletePlayList(playlistId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun addTrackToPlayList(playlistId: Int, trackId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrackFromPlayList(playlistId: Int, trackId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserPlaylists(): List<ShortPlaylistData> {
        TODO("Not yet implemented")
    }

    override suspend fun getArtistPlays(): List<ArtistPlays.ArtistPlaysInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrackPlays(): List<TrackPlays.TrackPlaysInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecentArtist(): List<ArtistData> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecentTracks(): List<MusicTrackData> {
        TODO("Not yet implemented")
    }

    override suspend fun findTracksByParams(
        query: String,
        albumName: String,
        artist: String,
        genre: String
    ): List<MusicTrackData> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserTracks(userId: Int): List<MusicTrackData> {
        TODO("Not yet implemented")
    }


    override suspend fun getProfile(): ProfileResponse.ProfileInfo {
        TODO("Not yet implemented")
    }

    override suspend fun updateProfile(info: ProfileResponse.ProfileInfo): ProfileResponse.ProfileInfo {
        TODO("Not yet implemented")
    }

    override suspend fun getArtistTracks(artist: String): List<MusicTrackData> {
        TODO("Not yet implemented")
    }

}