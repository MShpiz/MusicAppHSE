package com.layka.musicapphse.storage

import com.layka.musicapphse.screens.AlbumScreen.PlaylistData
import com.layka.musicapphse.screens.Lists.ArtistList.ArtistData
import com.layka.musicapphse.screens.Lists.PlaylistList.ShortPlaylistData
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import com.layka.musicapphse.storage.httpRepo.returnTypes.ArtistPlays
import com.layka.musicapphse.storage.httpRepo.returnTypes.ProfileResponse
import com.layka.musicapphse.storage.httpRepo.returnTypes.TrackPlays

interface MusicRepository {
    suspend fun getAlbum(albumName: String)
    suspend fun getAllTracks(): List<MusicTrackData>

    suspend fun deleteTrack(trackId: Int)

    suspend fun getPlayList(playlistId: Int): PlaylistData
    suspend fun createPlayList(name: String, description: String): Boolean
    suspend fun updatePlayList(name: String, description: String, id: Int): Boolean
    suspend fun deletePlayList(playlistId: Int): Boolean
    suspend fun addTrackToPlayList(playlist: ShortPlaylistData, trackId: Int)
    suspend fun deleteTrackFromPlayList(playlistId: Int, trackId: Int)
    suspend fun getUserPlaylists(): List<ShortPlaylistData>

    suspend fun getArtistPlays(): List<ArtistPlays.ArtistPlaysInfo>
    suspend fun getTrackPlays(): List<TrackPlays.TrackPlaysInfo>
    suspend fun getRecentArtist(): List<ArtistData>
    suspend fun getRecentTracks(): List<MusicTrackData>

    suspend fun findTracksByParams(
        query: String,
        albumName: String,
        artist: String,
        genre: String
    ): List<MusicTrackData>

    suspend fun getUserTracks(userId: Int): List<MusicTrackData>

    suspend fun getProfile(): ProfileResponse.ProfileInfo
    suspend fun updateProfile(info: ProfileResponse.ProfileInfo): ProfileResponse.ProfileInfo

    suspend fun getArtistTracks(artist: String): List<MusicTrackData>
}