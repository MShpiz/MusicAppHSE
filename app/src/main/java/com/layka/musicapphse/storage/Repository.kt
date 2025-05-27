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
import com.layka.musicapphse.storage.localRepo.entities.UpdateType
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

    override suspend fun createPlayList(name: String, description: String): PlaylistData? {
        if (getCurrentStrategy() == RepoType.LOCAL) return null
        val result = strategies[getCurrentStrategy()]?.createPlayList(name, description)
        if (result != null && getCurrentStrategy() == RepoType.HTTP) {
            (strategies[RepoType.LOCAL] as LocalRepository).createPlayList(
                name,
                description,
                result.id
            )
        }
        return result
    }

    override suspend fun updatePlayList(name: String, description: String, id: Int): Boolean {
        val result =
            strategies[getCurrentStrategy()]?.updatePlayList(name, description, id) ?: false
        if (result && getCurrentStrategy() == RepoType.HTTP) {
            (strategies[RepoType.LOCAL] as LocalRepository).updatePlayList(name, description, id)
        } else if (result && getCurrentStrategy() == RepoType.LOCAL) {
            (strategies[RepoType.LOCAL] as LocalRepository).addUpdate(
                UpdateType.UPDATE_PLAYLIST,
                id
            )
        }
        return result
    }

    override suspend fun deletePlayList(playlistId: Int): Boolean {
        if (getCurrentStrategy() == RepoType.LOCAL) return false
        val result = strategies[getCurrentStrategy()]?.deletePlayList(playlistId) ?: false
        if (result && getCurrentStrategy() == RepoType.HTTP) {
            (strategies[RepoType.LOCAL] as LocalRepository).deletePlayList(playlistId)
        }
        return result
    }

    override suspend fun addTrackToPlayList(playlist: ShortPlaylistData, trackId: Int): Boolean {
        val result =
            strategies[getCurrentStrategy()]?.addTrackToPlayList(playlist, trackId) ?: false
        if (result && getCurrentStrategy() == RepoType.HTTP) {
            (strategies[RepoType.LOCAL] as LocalRepository).addTrackToPlayList(playlist, trackId)
        } else if (result && getCurrentStrategy() == RepoType.LOCAL) {
            (strategies[RepoType.LOCAL] as LocalRepository).addUpdate(
                UpdateType.ADD_TRACK_TO_PLAYLIST,
                playlist.id,
                trackId
            )
        }
        return result
    }

    override suspend fun deleteTrackFromPlayList(playlistId: Int, trackId: Int): Boolean {
        val result =
            strategies[getCurrentStrategy()]?.deleteTrackFromPlayList(playlistId, trackId) ?: false
        if (result && getCurrentStrategy() == RepoType.HTTP) {
            (strategies[RepoType.LOCAL] as LocalRepository).deleteTrackFromPlayList(
                playlistId,
                trackId
            )
        } else if (result && getCurrentStrategy() == RepoType.LOCAL) {
            (strategies[RepoType.LOCAL] as LocalRepository).addUpdate(
                UpdateType.DELETE_TRACK_FROM_PLAYLIST,
                playlistId,
                trackId
            )
        }
        return result
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
        return strategies[getCurrentStrategy()]?.getProfile() ?: ProfileResponse.ProfileInfo(
            "",
            -1,
            ""
        )
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
                    (strategies[RepoType.HTTP] as HttpRepository).getPlayList(it.id).tracks.contains(
                        data
                    )
                }
                .forEach {
                    (strategies[RepoType.LOCAL] as LocalRepository).addTrackToPlayList(
                        it,
                        data.trackId
                    )
                }
        } catch (e: IllegalAccessException) {
        }
    }

    suspend fun sync() {
        val updates = (strategies[RepoType.LOCAL] as LocalRepository).getUpdates()
        for (update in updates) {
            try {
                when (update.updateType) {
                    UpdateType.UPDATE_PLAYLIST -> {
                        val data =
                            (strategies[RepoType.LOCAL] as LocalRepository).getPlayList(update.mainId)
                        (strategies[RepoType.HTTP] as HttpRepository).updatePlayList(
                            data.name,
                            data.description,
                            data.id
                        )
                        (strategies[RepoType.LOCAL] as LocalRepository).markUpdated(update)
                    }

                    UpdateType.ADD_TRACK_TO_PLAYLIST -> {
                        val data =
                            (strategies[RepoType.LOCAL] as LocalRepository).getPlayList(update.mainId)

                        (strategies[RepoType.HTTP] as HttpRepository).addTrackToPlayList(
                            ShortPlaylistData(data.name, data.description, data.id),
                            update.secondaryId ?: -1
                        )
                        (strategies[RepoType.LOCAL] as LocalRepository).markUpdated(update)
                    }

                    UpdateType.DELETE_TRACK_FROM_PLAYLIST -> {
                        (strategies[RepoType.HTTP] as HttpRepository).deleteTrackFromPlayList(
                            update.mainId, update.secondaryId ?: -1
                        )
                        (strategies[RepoType.LOCAL] as LocalRepository).markUpdated(update)
                    }
                }
            } catch (e: IllegalAccessException) {
                return
            }
        }
    }
}