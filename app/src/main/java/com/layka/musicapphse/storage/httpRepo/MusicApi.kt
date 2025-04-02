package com.layka.musicapphse.storage.httpRepo

import com.layka.musicapphse.storage.httpRepo.returnTypes.AllPlayListResponse
import com.layka.musicapphse.storage.httpRepo.returnTypes.AllTracksResponse
import com.layka.musicapphse.storage.httpRepo.returnTypes.ArtistPlays
import com.layka.musicapphse.storage.httpRepo.returnTypes.PlaylistResponse
import com.layka.musicapphse.storage.httpRepo.returnTypes.ProfileResponse
import com.layka.musicapphse.storage.httpRepo.returnTypes.RecentArtists
import com.layka.musicapphse.storage.httpRepo.returnTypes.RecentTracksResponse
import com.layka.musicapphse.storage.httpRepo.returnTypes.ShortPlaylistInfo
import com.layka.musicapphse.storage.httpRepo.returnTypes.ShortTrackInfo
import com.layka.musicapphse.storage.httpRepo.returnTypes.StandardResponse
import com.layka.musicapphse.storage.httpRepo.returnTypes.TrackInfo
import com.layka.musicapphse.storage.httpRepo.returnTypes.TrackPlays
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MusicApi {
    @GET("/api/tracks/{id}")
    suspend fun getTrackInfo(
        @Header("Authorization") authToken: String,
        @Path("id") trackId: Int
    ): Response<TrackInfo>

    @GET("/api/tracks/search")
    suspend fun getAlbum(
        @Header("Authorization") authToken: String,
        @Query("album") albumName: String
    ): Response<AllTracksResponse>

    @GET("/api/tracks/")
    suspend fun getAllTracks(@Header("Authorization") authToken: String): Response<AllTracksResponse>

    @DELETE("/api/tracks/{id}")
    suspend fun deleteTrack(
        @Header("Authorization") authToken: String,
        @Path("id") trackId: Int,
    ): Response<StandardResponse>

    @GET("/api/playlists")
    suspend fun getUserPlaylists(@Header("Authorization") authToken: String): Response<AllPlayListResponse>

    @GET("/api/playlists/{id}")
    suspend fun getPlayList(
        @Header("Authorization") authToken: String,
        @Path("id") playlistId: Int
    ): Response<PlaylistResponse>

    @POST("/api/playlists")
    suspend fun createPlayList(
        @Header("Authorization") authToken: String,
        @Body info: ShortPlaylistInfo
    ): Response<PlaylistResponse>

    @PUT("/api/playlists/{id}")
    suspend fun updatePlayList(
        @Header("Authorization") authToken: String,
        @Path("id") playlistId: Int,
        @Body info: ShortPlaylistInfo
    ): Response<PlaylistResponse>

    @DELETE("/api/playlists/{id}")
    suspend fun deletePlayList(
        @Header("Authorization") authToken: String,
        @Path("id") playlistId: Int
    ): Response<StandardResponse>

    @POST("/api/playlists/{id}/tracks")
    suspend fun addTrackToPlayList(
        @Header("Authorization") authToken: String,
        @Path("id") playlistId: Int,
        @Body track: ShortTrackInfo
    ): Response<StandardResponse>

    @DELETE("/api/playlists/{id}/tracks/{trackId}")
    suspend fun deleteTrackFromPlayList(
        @Header("Authorization") authToken: String,
        @Path("id") playlistId: Int,
        @Path("trackId") trackId: Int
    ): Response<StandardResponse>


    @GET("/api/stats/artist-plays")
    fun getArtistPlays(@Header("Authorization") authToken: String): Response<ArtistPlays>

    @GET("/api/stats/track-plays")
    suspend fun getTrackPlays(@Header("Authorization") authToken: String): Response<TrackPlays>

    @GET("/api/stats/recent-artists")
    suspend fun getRecentArtist(@Header("Authorization") authToken: String): Response<RecentArtists>

    @GET("/api/stats/recent-tracks")
    suspend fun getRecentTracks(@Header("Authorization") authToken: String): Response<RecentTracksResponse>

    @GET("/api/tracks/search")
    suspend fun searchTracksByParams(
        @Header("Authorization") authToken: String,
        @QueryMap options: Map<String, String>
    ): Response<AllTracksResponse>

    @GET("/api/tracks/user/{id}")
    suspend fun getUserTracks(
        @Header("Authorization") authToken: String,
        @Path("id") userId: Int
    ): Response<AllTracksResponse>


    @GET("/api/user/profile")
    suspend fun getProfile(@Header("Authorization") authToken: String): Response<ProfileResponse>

    @PUT("/api/user/profile")
    suspend fun updateProfile(
        @Header("Authorization") authToken: String,
        @Body info: ProfileResponse.ProfileInfo
    ): Response<ProfileResponse>
}