package com.layka.musicapphse.storage.localRepo

import com.layka.musicapphse.screens.AlbumScreen.PlaylistData
import com.layka.musicapphse.screens.Lists.ArtistList.ArtistData
import com.layka.musicapphse.screens.Lists.PlaylistList.ShortPlaylistData
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import com.layka.musicapphse.storage.MusicRepository
import com.layka.musicapphse.storage.httpRepo.returnTypes.ArtistPlays
import com.layka.musicapphse.storage.httpRepo.returnTypes.ProfileResponse
import com.layka.musicapphse.storage.httpRepo.returnTypes.TrackPlays
import com.layka.musicapphse.storage.localRepo.entities.PlaylistEntity
import com.layka.musicapphse.storage.localRepo.entities.TrackEntity
import jakarta.inject.Inject
import java.io.File


class LocalRepository @Inject constructor(private val dataBase: MusicDb) : MusicRepository {

    override suspend fun getAlbum(albumName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTracks(): List<MusicTrackData> {
        return convertListTrackEntities(dataBase.trackDao().getAll() ?: listOf())
    }

    override suspend fun deleteTrack(trackId: Int) {
        val track = dataBase.trackDao().getTrackByTrackId(trackId)
        if (track != null) {
            dataBase.trackDao().deleteTrackById(track)
            val trackFile = File(track.uri)
            if (trackFile.exists()) {
                trackFile.delete()
            }
        }
    }

    override suspend fun getPlayList(playlistId: Int): PlaylistData {
        val playlist = dataBase.playlistDao().getPlaylistById(playlistId)
        if (playlist != null) {
            val tracks =
                dataBase.playlistTrackDao().getPlaylistWithTracksByPlaylistId(playlistId)?.tracks
                    ?: listOf()
            return PlaylistData("", playlist.description ?: "", playlistId, playlist.name, convertListTrackEntities(tracks))
        }

        return PlaylistData("",  "", playlistId, "", listOf())
    }

    override suspend fun createPlayList(name: String, description: String): Boolean {
        dataBase.playlistDao().insertPlayList(PlaylistEntity(name, description))
        return true
    }

    override suspend fun updatePlayList(name: String, description: String, id: Int): Boolean {
        val playlist = dataBase.playlistDao().getPlaylistById(id)
        if (playlist == null) return false
        dataBase.playlistDao().updatePlaylist(PlaylistEntity(name, description, id))
        return true

    }

    override suspend fun deletePlayList(playlistId: Int): Boolean {
        val playlist = dataBase.playlistDao().getPlaylistById(playlistId)
        if (playlist != null)
            dataBase.playlistDao().deletePlaylist(playlist)
        return true
    }

    override suspend fun addTrackToPlayList(playlist: ShortPlaylistData, trackId: Int) {
        val pl = dataBase.playlistDao().getPlaylistById(playlist.id)
        if (pl == null) {
            dataBase.playlistDao().insertPlayList(PlaylistEntity(playlist.name, playlist.description, playlist.id))
        }
        dataBase.playlistTrackDao().addTrackToPlaylist(playlist.id, trackId)
    }

    override suspend fun deleteTrackFromPlayList(playlistId: Int, trackId: Int) {
        dataBase.playlistTrackDao().deleteTrackFromPlaylist(playlistId, trackId)
    }

    override suspend fun getUserPlaylists(): List<ShortPlaylistData> {
        return dataBase.playlistDao().getAll()?.map {
            ShortPlaylistData(it.name, it.description, it.playlistId)
        } ?: listOf()
    }

    override suspend fun getArtistPlays(): List<ArtistPlays.ArtistPlaysInfo> {
        return listOf()
    }

    override suspend fun getTrackPlays(): List<TrackPlays.TrackPlaysInfo> {
        return listOf()
    }

    override suspend fun getRecentArtist(): List<ArtistData> {
        return getAllTracks().map { ArtistData(it.artists) }.toHashSet().toList()
    }

    override suspend fun getRecentTracks(): List<MusicTrackData> {
        return getAllTracks()
    }

    override suspend fun findTracksByParams(
        query: String,
        albumName: String,
        artist: String,
        genre: String
    ): List<MusicTrackData> {
        val byName = dataBase.trackDao().getTracksByName(name = query)?.toHashSet() ?: setOf()
        val byArtist = dataBase.trackDao().getTracksByArtist(artist = artist)?.toHashSet() ?: setOf()
        return convertListTrackEntities((byName + byArtist).toList())
    }

    override suspend fun getUserTracks(userId: Int): List<MusicTrackData> {
        return getAllTracks()
    }


    override suspend fun getProfile(): ProfileResponse.ProfileInfo {
        return ProfileResponse.ProfileInfo("", -1, "")
    }

    override suspend fun updateProfile(info: ProfileResponse.ProfileInfo): ProfileResponse.ProfileInfo {
        return ProfileResponse.ProfileInfo("", -1, "")
    }

    override suspend fun getArtistTracks(artist: String): List<MusicTrackData> {
        val tracks = dataBase.trackDao().getTracksByArtist(artist) ?: listOf()
        return convertListTrackEntities(tracks)
    }

    private fun convertTrackEntityToMusicTrackData(entity: TrackEntity): MusicTrackData {
        return MusicTrackData(
            entity.trackId,
            entity.name,
            entity.artist,
            entity.duration,
            entity.uri,
            entity.albumCover
        )
    }

    private fun convertListTrackEntities(list: List<TrackEntity>): List<MusicTrackData> {
        return list.map { convertTrackEntityToMusicTrackData(it) }
    }

    suspend fun addTrack(track: MusicTrackData) {
        if (dataBase.trackDao().getTrackByTrackId(track.trackId) != null) return
        dataBase.trackDao().insertTrack(
            TrackEntity(
                track.trackName,
                track.artists,
                track.duration,
                track.uri,
                track.albumCover,
                track.trackId
            )
        )
    }

}