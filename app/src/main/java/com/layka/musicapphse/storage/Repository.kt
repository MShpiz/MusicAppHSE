package com.layka.musicapphse.storage

import com.layka.musicapphse.screens.AlbumScreen.PlaylistData
import com.layka.musicapphse.screens.Lists.ArtistList.ArtistData
import com.layka.musicapphse.screens.Lists.PlaylistList.ShortPlaylistData
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import com.layka.musicapphse.storage.httpRepo.HttpRepository
import com.layka.musicapphse.storage.httpRepo.returnTypes.ArtistPlays
import com.layka.musicapphse.storage.httpRepo.returnTypes.ProfileResponse
import com.layka.musicapphse.storage.httpRepo.returnTypes.TrackPlays
import jakarta.inject.Inject


class Repository @Inject constructor(
    httpRepository: HttpRepository,
    localRepository: LocalRepository
) : MusicRepository {

    private val strategies: Map<RepoType, MusicRepository> = mapOf(
        Pair<RepoType, MusicRepository>(RepoType.HTTP, httpRepository),
        Pair<RepoType, MusicRepository>(RepoType.LOCAL, localRepository)
    )

    private val currentStrategy = RepoType.HTTP

    override suspend fun getTrackInfo(trackId: Int) {
        strategies[currentStrategy]?.getTrackInfo(trackId)
    }

    override suspend fun getAlbum(albumName: String) {
        strategies[currentStrategy]?.getAlbum(albumName)
    }

    override suspend fun getAllTracks(): List<MusicTrackData> {
        return strategies[currentStrategy]!!.getAllTracks()
    }

    override suspend fun deleteTrack(trackId: Int) {
        strategies[currentStrategy]!!.deleteTrack(trackId)
    }

    override suspend fun getPlayList(playlistId: Int): PlaylistData {
        return strategies[currentStrategy]!!.getPlayList(playlistId)
    }

    override suspend fun createPlayList(name: String, description: String): Boolean {
        return strategies[currentStrategy]?.createPlayList(name, description) ?: false
    }

    override suspend fun updatePlayList(name: String, description: String, id: Int): Boolean {
        return strategies[currentStrategy]?.updatePlayList(name, description, id) ?: false
    }

    override suspend fun deletePlayList(playlistId: Int): Boolean {
        return strategies[currentStrategy]?.deletePlayList(playlistId) ?: false
    }

    override suspend fun addTrackToPlayList(playlistId: Int, trackId: Int) {
        strategies[currentStrategy]?.addTrackToPlayList(playlistId, trackId)
    }

    override suspend fun deleteTrackFromPlayList(playlistId: Int, trackId: Int) {
        strategies[currentStrategy]?.deleteTrackFromPlayList(playlistId, trackId)
    }

    override suspend fun getUserPlaylists(): List<ShortPlaylistData> {
        return strategies[currentStrategy]?.getUserPlaylists() ?: listOf()
    }

    override suspend fun getArtistPlays(): List<ArtistPlays.ArtistPlaysInfo> {
        return strategies[currentStrategy]?.getArtistPlays() ?: listOf()
    }

    override suspend fun getTrackPlays(): List<TrackPlays.TrackPlaysInfo> {
        return strategies[currentStrategy]?.getTrackPlays() ?: listOf()
    }

    override suspend fun getRecentArtist(): List<ArtistData> {
        return strategies[currentStrategy]?.getRecentArtist() ?: listOf()
    }

    override suspend fun getRecentTracks(): List<MusicTrackData> {
        return strategies[currentStrategy]?.getRecentTracks() ?: listOf()
    }

    override suspend fun findTracksByParams(
        query: String,
        albumName: String,
        artist: String,
        genre: String
    ): List<MusicTrackData> {
        return strategies[currentStrategy]?.findTracksByParams(query, albumName, artist, genre)
            ?: listOf()
    }

    override suspend fun getUserTracks(userId: Int): List<MusicTrackData> {
        return strategies[currentStrategy]?.getUserTracks(userId) ?: listOf()
    }

    override suspend fun getProfile(): ProfileResponse.ProfileInfo {
        return strategies[currentStrategy]?.getProfile() ?: ProfileResponse.ProfileInfo("", -1, "")
    }

    override suspend fun updateProfile(info: ProfileResponse.ProfileInfo): ProfileResponse.ProfileInfo {
        return strategies[currentStrategy]?.updateProfile(info) ?: ProfileResponse.ProfileInfo(
            "",
            -1,
            ""
        )
    }

    override suspend fun getArtistTracks(artist: String): List<MusicTrackData> {
        return strategies[currentStrategy]?.getArtistTracks(artist) ?: listOf()
    }

}