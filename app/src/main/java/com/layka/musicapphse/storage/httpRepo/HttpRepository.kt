package com.layka.musicapphse.storage.httpRepo

import android.util.Log
import com.layka.musicapphse.screens.AlbumScreen.PlaylistData
import com.layka.musicapphse.screens.Lists.ArtistList.ArtistData
import com.layka.musicapphse.screens.Lists.PlaylistList.ShortPlaylistData
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import com.layka.musicapphse.services.TokenManager
import com.layka.musicapphse.storage.MusicRepository
import com.layka.musicapphse.storage.httpRepo.returnTypes.ArtistPlays
import com.layka.musicapphse.storage.httpRepo.returnTypes.PlaylistInfo
import com.layka.musicapphse.storage.httpRepo.returnTypes.ProfileResponse
import com.layka.musicapphse.storage.httpRepo.returnTypes.RecentTracksResponse.RecentTracksInfo
import com.layka.musicapphse.storage.httpRepo.returnTypes.ShortPlaylistInfo
import com.layka.musicapphse.storage.httpRepo.returnTypes.ShortTrackInfo
import com.layka.musicapphse.storage.httpRepo.returnTypes.TrackInfo
import com.layka.musicapphse.storage.httpRepo.returnTypes.TrackPlays
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first

class HttpRepository @Inject constructor(
    private val api: MusicApi,
    private val tokenManager: TokenManager
) : MusicRepository {
    private val trackUri = "/api/tracks/stream/"
    private val serverUri = "http://192.168.1.29:8080"

    private suspend fun getToken(): String {
        val tokenValue = tokenManager.getToken().first()
        Log.v("HTTP-REPO", "token:" + tokenValue)
        return "Bearer $tokenValue"
    }

    override suspend fun getTrackInfo(trackId: Int) {
        api.getTrackInfo(getToken(), trackId)
    }

    override suspend fun getAlbum(albumName: String) {
        api.getAlbum(getToken(), albumName)
    }

    override suspend fun getAllTracks(): List<MusicTrackData> {
        try {
            Log.v("HTTP-REPO", "get all tracks")
            val response = api.getAllTracks(getToken())
            if (response.code() == 401) {
                Log.v("HTTP-REPO", "unauthorized")
                throw IllegalAccessException("unauthorized")
            }
            val res: List<TrackInfo> = response.body()?.data ?: listOf()
            Log.v("HTTP-REPO", "get all tracks" + res)
            return convertListTrackInfoToTrackData(res)
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "get all tracks" + e.message)
            return listOf()
        }
    }

    override suspend fun deleteTrack(trackId: Int) {
        try {
            Log.v("HTTP-REPO", "deleteTrack")
            val response = api.deleteTrack(getToken(), trackId)
            if (response.code() == 401) {
                Log.v("HTTP-REPO", "unauthorized")
                throw IllegalAccessException("unauthorized")
            }
            Log.v("HTTP-REPO", "deleteTrack" + response.body())
            return
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "deleteTrack error" + e.message)
            return
        }
    }

    override suspend fun getPlayList(playlistId: Int): PlaylistData {
        try {
            val response = api.getPlayList(getToken(), playlistId)
            Log.v("HTTP-REPO", "get playlist $playlistId")
            if (response.code() == 401) {
                Log.v("HTTP-REPO", "unauthorized")
                throw IllegalAccessException("unauthorized")
            }
            if (response.code() == 200) {
                val res: PlaylistInfo =
                    response.body()?.data ?: PlaylistInfo("", "", -1, "", listOf())
                Log.v("HTTP-REPO", "get playlist ${response.body()}")
                return PlaylistData(
                    res.createdAt,
                    res.description,
                    res.id,
                    res.name,
                    if (res.tracks != null) convertListTrackInfoToTrackData(res.tracks) else listOf()
                )
            } else {
                throw RuntimeException(response.message())
            }
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "get all tracks" + e.message)
            return PlaylistData("", "", -1, "", listOf())
        }
    }

    override suspend fun createPlayList(name: String, description: String): Boolean {
        try {
            Log.v("HTTP-REPO", "get all tracks")
            val response = api.createPlayList(
                getToken(),
                ShortPlaylistInfo(name = name, description = description)
            )
            if (response.code() == 401) {
                Log.v("HTTP-REPO", "unauthorized")
                throw IllegalAccessException("unauthorized")
            }
            Log.v("HTTP-REPO", "create playlist " + response.body())

            return response.code() == 201
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "get all tracks" + e.message)
            return false
        }
    }

    override suspend fun updatePlayList(name: String, description: String, id: Int): Boolean {
        try {
            Log.v("HTTP-REPO", "get all tracks")
            val response = api.updatePlayList(
                getToken(),
                id,
                ShortPlaylistInfo(name = name, description = description)
            )
            if (response.code() == 401) {
                Log.v("HTTP-REPO", "unauthorized")
                throw IllegalAccessException("unauthorized")
            }
            Log.v("HTTP-REPO", "update playlist " + response.body())

            return response.code() == 200
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "update playlist error " + e.message)
            return false
        }
    }

    override suspend fun deletePlayList(playlistId: Int): Boolean {
        try {
            Log.v("HTTP-REPO", "get all tracks")
            val response = api.deletePlayList(
                getToken(),
                playlistId
            )
            if (response.code() == 401) {
                Log.v("HTTP-REPO", "unauthorized")
                throw IllegalAccessException("unauthorized")
            }
            Log.v("HTTP-REPO", "update playlist " + response.body())

            return response.code() == 200
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "update playlist error " + e.message)
            return false
        }
    }

    override suspend fun addTrackToPlayList(playlistId: Int, trackId: Int) {
        try {
            Log.v("HTTP-REPO", "add track to playlist")
            val response = api.addTrackToPlayList(getToken(), playlistId, ShortTrackInfo(trackId))
            if (response.code() == 401) {
                Log.v("HTTP-REPO", "unauthorized")
                throw IllegalAccessException("unauthorized")
            }
            Log.v("HTTP-REPO", "add track to playlist " + response.body())

            return
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "add track to playlist" + e.message)
            return
        }
    }

    override suspend fun deleteTrackFromPlayList(playlistId: Int, trackId: Int) {
        try {
            Log.v("HTTP-REPO", "add track to playlist")
            val response = api.deleteTrackFromPlayList(getToken(), playlistId, trackId)
            if (response.code() == 401) {
                Log.v("HTTP-REPO", "unauthorized")
                throw IllegalAccessException("unauthorized")
            }
            Log.v("HTTP-REPO", "add track to playlist " + response.body())

            return
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "add track to playlist" + e.message)
            return
        }
    }

    override suspend fun getUserPlaylists(): List<ShortPlaylistData> {
        try {
            Log.v("HTTP-REPO", "get all tracks")
            val response = api.getUserPlaylists(getToken())
            if (response.code() == 401) {
                Log.v("HTTP-REPO", "unauthorized")
                throw IllegalAccessException("unauthorized")
            }
            Log.v("HTTP-REPO", "get playlists " + response.body())
            val resp = response.body()?.data ?: return listOf()
            return resp.map { info ->
                ShortPlaylistData(
                    name = info.name,
                    description = info.description,
                    id = info.id
                )
            }
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "get playlists" + e.message)
            return listOf()
        }
    }

    override suspend fun getArtistPlays(): List<ArtistPlays.ArtistPlaysInfo> {
        try {
            Log.v("HTTP-REPO", "add track to playlist")
            val response = api.getArtistPlays(getToken())
            if (response.code() == 401) {
                Log.v("HTTP-REPO", "unauthorized")
                throw IllegalAccessException("unauthorized")
            }
            Log.v("HTTP-REPO", "getArtistPlays " + response.body())

            return response.body()?.data ?: listOf()
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "getArtistPlays error" + e.message)
            return listOf()
        }
    }

    override suspend fun getTrackPlays(): List<TrackPlays.TrackPlaysInfo> {
        try {
            Log.v("HTTP-REPO", "add track to playlist")
            val response = api.getTrackPlays(getToken())
            if (response.code() == 401) {
                Log.v("HTTP-REPO", "unauthorized")
                throw IllegalAccessException("unauthorized")
            }
            Log.v("HTTP-REPO", "getArtistPlays " + response.body())

            return response.body()?.data ?: listOf()
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "getArtistPlays error" + e.message)
            return listOf()
        }
    }

    override suspend fun getRecentArtist(): List<ArtistData> {
        try {
            val response = api.getRecentArtist(getToken())
            if (response.code() == 401) {
                throw IllegalAccessException("unauthorized")
            }
            val res: List<String> = response.body()?.data ?: listOf()
            Log.v("HTTP-REPO", "get recent artists" + response.message())
            return res.map { ArtistData(it) }
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "get all tracks" + e.message)
            return listOf()
        }
    }

    override suspend fun getRecentTracks(): List<MusicTrackData> {
        try {
            val response = api.getRecentTracks(getToken())
            if (response.code() == 401) {
                throw IllegalAccessException("unauthorized")
            }
            val res: List<RecentTracksInfo> = response.body()?.data ?: listOf()
            Log.v("HTTP-REPO", "get recent tracks $res")
            return convertListRecentTrackInfoToTrackData(res)
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "error get recent tracks" + e.message)
            return listOf()
        }
    }

    override suspend fun findTracksByParams(
        query: String,
        albumName: String,
        artist: String,
        genre: String
    ): List<MusicTrackData> {
        try {
            val response = api.searchTracksByParams(
                getToken(),
                mapOf(
                    Pair("artist", artist),
                    Pair("query", query),
                    Pair("albumName", albumName),
                    Pair("genre", genre)
                )
            )
            if (response.code() == 401) {
                throw IllegalAccessException("unauthorized")
            }
            Log.v("HTTP-REPO", "get tracks by params" + response.body())

            return convertListTrackInfoToTrackData(response.body()?.data ?: listOf())
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "get all artist tracks" + e.message)
            return listOf()
        }
    }

    override suspend fun getUserTracks(userId: Int): List<MusicTrackData> {
        try {
            val response = api.getUserTracks(getToken(), userId)
            if (response.code() == 401) {
                throw IllegalAccessException("unauthorized")
            }
            val res: List<TrackInfo> = response.body()?.data ?: listOf()
            Log.v("HTTP-REPO", "get user tracks" + response.message())
            return convertListTrackInfoToTrackData(res)
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "get all tracks" + e.message)
            return listOf()
        }
    }

    override suspend fun getProfile(): ProfileResponse.ProfileInfo {
        try {
            val response = api.getProfile(getToken())
            if (response.code() == 401) {
                throw IllegalAccessException("unauthorized")
            }
            val res: ProfileResponse.ProfileInfo =
                response.body()?.data ?: ProfileResponse.ProfileInfo("", -1, "")
            Log.v("HTTP-REPO", "get user tracks" + response.message())
            return res
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "get all tracks" + e.message)
            return ProfileResponse.ProfileInfo("", -1, "")
        }
    }

    override suspend fun updateProfile(info: ProfileResponse.ProfileInfo): ProfileResponse.ProfileInfo {
        try {
            val response = api.updateProfile(getToken(), info)
            if (response.code() == 401) {
                throw IllegalAccessException("unauthorized")
            }
            val res: ProfileResponse.ProfileInfo =
                response.body()?.data ?: ProfileResponse.ProfileInfo("", -1, "")
            Log.v("HTTP-REPO", "get user tracks" + response.message())
            return res
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "get all tracks" + e.message)
            return ProfileResponse.ProfileInfo("", -1, "")
        }
    }

    override suspend fun getArtistTracks(artist: String): List<MusicTrackData> {
        try {
            val response = api.searchTracksByParams(getToken(), mapOf(Pair("artist", artist)))
            if (response.code() == 401) {
                throw IllegalAccessException("unauthorized")
            }
            Log.v("HTTP-REPO", "get all artist tracks" + response.body())

            return convertListTrackInfoToTrackData(response.body()?.data ?: listOf())
        } catch (e: Exception) {
            if (e is IllegalAccessException) throw e
            Log.v("HTTP-REPO", "get all artist tracks" + e.message)
            return listOf()
        }
    }

    private fun convertListTrackInfoToTrackData(list: List<TrackInfo>): List<MusicTrackData> {
        return list.map { convertResponseToMusicTrackData(it) }
    }

    private fun convertListRecentTrackInfoToTrackData(list: List<RecentTracksInfo>): List<MusicTrackData> {
        return list.map { convertRecentResponseToMusicTrackData(it) }
    }

    private fun convertResponseToMusicTrackData(info: TrackInfo): MusicTrackData {
        return MusicTrackData(
            info.id,
            info.title,
            info.artist,
            info.duration,
            serverUri + trackUri + info.id.toString(),
            serverUri + info.image_url
        )
    }

    private fun convertRecentResponseToMusicTrackData(info: RecentTracksInfo): MusicTrackData {
        return MusicTrackData(
            info.ID,
            info.Title,
            info.Artist,
            info.Duration,
            serverUri + trackUri + info.ID.toString(),
            serverUri + info.ImagePath
        )
    }
}