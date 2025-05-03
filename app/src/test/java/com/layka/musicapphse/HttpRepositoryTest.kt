package com.layka.musicapphse

import com.layka.musicapphse.screens.AlbumScreen.PlaylistData
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import com.layka.musicapphse.services.TokenManager
import com.layka.musicapphse.storage.httpRepo.HttpRepository
import com.layka.musicapphse.storage.httpRepo.MusicApi
import com.layka.musicapphse.storage.httpRepo.returnTypes.AllTracksResponse
import com.layka.musicapphse.storage.httpRepo.returnTypes.PlaylistInfo
import com.layka.musicapphse.storage.httpRepo.returnTypes.PlaylistResponse
import com.layka.musicapphse.storage.httpRepo.returnTypes.ProfileResponse
import com.layka.musicapphse.storage.httpRepo.returnTypes.RecentTracksResponse
import com.layka.musicapphse.storage.httpRepo.returnTypes.TrackInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.testng.Assert
import retrofit2.Response


class HttpRepositoryTest {


    val musicApi: MusicApi = mock(MusicApi::class.java)
    val tokenManager: TokenManager = mock(TokenManager::class.java)

    var repository: HttpRepository = HttpRepository(musicApi, tokenManager)

    fun token(): Flow<String> = flow {
        emit("")
    }

    @Test
    fun getAllTracksTest() {
        val allTracksResponse = AllTracksResponse(true, listOf(TrackInfo("", "B", "", 1, "", 1, "/image", "A", 1)))
        val resultingData = MusicTrackData(1, "A", "B", 1, "http://192.168.1.29:8080/api/tracks/stream/1", "http://192.168.1.29:8080/image")
        val response = Response.success(allTracksResponse)
        val result: List<MusicTrackData>
        runBlocking {
            `when`(tokenManager.getToken()).thenReturn(token())
            `when`(musicApi.getAllTracks("Bearer ")).thenReturn(response)
            result = repository.getAllTracks()
        }
        Assert.assertEquals(result.size, 1)
        Assert.assertEquals(result[0], resultingData)
    }

    @Test
    fun getRecentTracksTest() {
        val allTracksResponse = RecentTracksResponse.RecentTracksInfo(1, "", "", "", "A", "B", "", "", 1, "/image", "", 1)
        val resultingData = MusicTrackData(1, "A", "B", 1, "http://192.168.1.29:8080/api/tracks/stream/1", "http://192.168.1.29:8080/image")
        val response = Response.success(RecentTracksResponse(true, listOf(allTracksResponse)))
        val result: List<MusicTrackData>
        runBlocking {
            `when`(tokenManager.getToken()).thenReturn(token())
            `when`(musicApi.getRecentTracks("Bearer ")).thenReturn(response)
            result = repository.getRecentTracks()
        }
        Assert.assertEquals(result.size, 1)
        Assert.assertEquals(result[0], resultingData)
    }

    @Test
    fun getUserTracksTest() {
        val allTracksResponse = AllTracksResponse(true, listOf(TrackInfo("", "B", "", 1, "", 1, "/image", "A", 1)))
        val resultingData = MusicTrackData(1, "A", "B", 1, "http://192.168.1.29:8080/api/tracks/stream/1", "http://192.168.1.29:8080/image")
        val response = Response.success(allTracksResponse)
        val result: List<MusicTrackData>
        runBlocking {
            `when`(tokenManager.getToken()).thenReturn(token())
            `when`(musicApi.getUserTracks("Bearer ", 1)).thenReturn(response)
            result = repository.getUserTracks(1)
        }
        Assert.assertEquals(result.size, 1)
        Assert.assertEquals(result[0], resultingData)
    }

    @Test
    fun getPlaylistTest() {
        val playlistResponse = PlaylistResponse(true, PlaylistInfo("", "aa", 1, "A", listOf(TrackInfo("", "B", "", 1, "", 1, "/image", "A", 1))))
        val resultingData = PlaylistData("", "aa", 1, "A", listOf( MusicTrackData(1, "A", "B", 1, "http://192.168.1.29:8080/api/tracks/stream/1", "http://192.168.1.29:8080/image")))
        val response = Response.success(playlistResponse)
        val result: PlaylistData
        runBlocking {
            `when`(tokenManager.getToken()).thenReturn(token())
            `when`(musicApi.getPlayList("Bearer ", 1)).thenReturn(response)
            result = repository.getPlayList(1)
        }
        Assert.assertEquals(result, resultingData)
    }

    @Test
    fun getArtistTest() {
        val playlistResponse = AllTracksResponse(true, listOf(TrackInfo("", "B", "", 1, "", 1, "/image", "A", 1)))
        val resultingData = listOf( MusicTrackData(1, "A", "B", 1, "http://192.168.1.29:8080/api/tracks/stream/1", "http://192.168.1.29:8080/image"))
        val response = Response.success(playlistResponse)
        val result: List<MusicTrackData>
        runBlocking {
            `when`(tokenManager.getToken()).thenReturn(token())
            `when`(musicApi.searchTracksByParams("Bearer ", mapOf(Pair("artist","A")))).thenReturn(response)
            result = repository.getArtistTracks("A")
        }
        Assert.assertEquals(result, resultingData)
    }

    @Test
    fun getProfileInfoTest() {
        val responseData = ProfileResponse(true, ProfileResponse.ProfileInfo("email", 1, "user") )
        val resultingData = ProfileResponse.ProfileInfo("email", 1, "user")
        val response = Response.success(responseData)
        val result: ProfileResponse.ProfileInfo
        runBlocking {
            `when`(tokenManager.getToken()).thenReturn(token())
            `when`(musicApi.getProfile("Bearer ")).thenReturn(response)
            result = repository.getProfile()
        }
        Assert.assertEquals(result, resultingData)
    }
}