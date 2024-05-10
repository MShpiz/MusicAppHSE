package com.layka.musicapphse.storage

import com.layka.musicapphse.screens.AlbumScreen.PlaylistData
import com.layka.musicapphse.screens.Lists.ArtistList.ArtistData
import com.layka.musicapphse.screens.Lists.PlaylistList.ShortPlaylistData
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import com.layka.musicapphse.services.TokenManager
import com.layka.musicapphse.storage.httpRepo.HttpRepository
import com.layka.musicapphse.storage.httpRepo.returnTypes.ArtistPlays
import com.layka.musicapphse.storage.httpRepo.returnTypes.ProfileResponse
import com.layka.musicapphse.storage.httpRepo.returnTypes.TrackPlays
import com.layka.musicapphse.storage.localRepo.LocalRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first


class Repository @Inject constructor(
    httpRepository: HttpRepository,
    localRepository: LocalRepository,
    private val tokenManager: TokenManager
) : MusicRepository {

    private val strategies: Map<RepoType, MusicRepository> = mapOf(
        Pair<RepoType, MusicRepository>(RepoType.HTTP, httpRepository),
        Pair<RepoType, MusicRepository>(RepoType.LOCAL, localRepository)
    )
    
    private suspend fun getCurrentStrategy(): RepoType {
        return RepoType.valueOf(tokenManager.getRepoType().first())
    }

    override suspend fun getAlbum(albumName: String) {
        strategies[getCurrentStrategy()]?.getAlbum(albumName)
    }

    override suspend fun getAllTracks(): List<MusicTrackData> {
        return strategies[getCurrentStrategy()]!!.getAllTracks()
    }

    override suspend fun deleteTrack(trackId: Int) {
        strategies[getCurrentStrategy()]!!.deleteTrack(trackId)
    }

    override suspend fun getPlayList(playlistId: Int): PlaylistData {
        return strategies[getCurrentStrategy()]!!.getPlayList(playlistId)
    }

    override suspend fun createPlayList(name: String, description: String): Boolean {
        return strategies[getCurrentStrategy()]?.createPlayList(name, description) ?: false
    }

    override suspend fun updatePlayList(name: String, description: String, id: Int): Boolean {
        return strategies[getCurrentStrategy()]?.updatePlayList(name, description, id) ?: false
    }

    override suspend fun deletePlayList(playlistId: Int): Boolean {
        return strategies[getCurrentStrategy()]?.deletePlayList(playlistId) ?: false
    }

    override suspend fun addTrackToPlayList(playlist: ShortPlaylistData, trackId: Int) {
        strategies[getCurrentStrategy()]?.addTrackToPlayList(playlist, trackId)
    }

    override suspend fun deleteTrackFromPlayList(playlistId: Int, trackId: Int) {
        strategies[getCurrentStrategy()]?.deleteTrackFromPlayList(playlistId, trackId)
    }

    override suspend fun getUserPlaylists(): List<ShortPlaylistData> {
        return strategies[getCurrentStrategy()]?.getUserPlaylists() ?: listOf()
    }

    override suspend fun getArtistPlays(): List<ArtistPlays.ArtistPlaysInfo> {
        return strategies[getCurrentStrategy()]?.getArtistPlays() ?: listOf()
    }

    override suspend fun getTrackPlays(): List<TrackPlays.TrackPlaysInfo> {
        return strategies[getCurrentStrategy()]?.getTrackPlays() ?: listOf()
    }

    override suspend fun getRecentArtist(): List<ArtistData> {
        return strategies[getCurrentStrategy()]?.getRecentArtist() ?: listOf()
    }

    override suspend fun getRecentTracks(): List<MusicTrackData> {
        return strategies[getCurrentStrategy()]?.getRecentTracks() ?: listOf()
    }

    override suspend fun findTracksByParams(
        query: String,
        albumName: String,
        artist: String,
        genre: String
    ): List<MusicTrackData> {
        return strategies[getCurrentStrategy()]?.findTracksByParams(query, albumName, artist, genre)
            ?: listOf()
    }

    override suspend fun getUserTracks(userId: Int): List<MusicTrackData> {
        return strategies[getCurrentStrategy()]?.getUserTracks(userId) ?: listOf()
    }

    override suspend fun getProfile(): ProfileResponse.ProfileInfo {
        return strategies[getCurrentStrategy()]?.getProfile() ?: ProfileResponse.ProfileInfo("", -1, "")
    }

    override suspend fun updateProfile(info: ProfileResponse.ProfileInfo): ProfileResponse.ProfileInfo {
        return strategies[getCurrentStrategy()]?.updateProfile(info) ?: ProfileResponse.ProfileInfo(
            "",
            -1,
            ""
        )
    }

    override suspend fun getArtistTracks(artist: String): List<MusicTrackData> {
        return strategies[getCurrentStrategy()]?.getArtistTracks(artist) ?: listOf()
    }

    suspend fun saveTrack(data: MusicTrackData) {
        (strategies[RepoType.LOCAL] as LocalRepository).addTrack(data)
        getTrackPlaylists(data)
    }

    private suspend fun getTrackPlaylists(data: MusicTrackData) {
        try {
            (strategies[RepoType.HTTP] as HttpRepository).getUserPlaylists()
                .filter {
                    (strategies[RepoType.HTTP] as HttpRepository).getPlayList(it.id).tracks.contains(data)
                }
                .forEach{
                    (strategies[RepoType.LOCAL] as LocalRepository).addTrackToPlayList(
                        it,
                        data.trackId
                    )
                }
        } catch (e: IllegalAccessException) {
        }
    }

}