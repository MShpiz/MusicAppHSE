package com.layka.musicapphse

import com.layka.musicapphse.screens.AlbumScreen.PlaylistData
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import com.layka.musicapphse.storage.localRepo.LocalRepository
import com.layka.musicapphse.storage.localRepo.MusicDb
import com.layka.musicapphse.storage.localRepo.dao.PlaylistDao
import com.layka.musicapphse.storage.localRepo.dao.PlaylistTrackDao
import com.layka.musicapphse.storage.localRepo.dao.TrackDao
import com.layka.musicapphse.storage.localRepo.entities.PlaylistEntity
import com.layka.musicapphse.storage.localRepo.entities.PlaylistWithTracks
import com.layka.musicapphse.storage.localRepo.entities.TrackEntity
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.testng.Assert

class LocalRepositoryTest {


    val database: MusicDb = mock(MusicDb::class.java)
    val trackDao: TrackDao = mock(TrackDao::class.java)
    val playlistDao: PlaylistDao = mock(PlaylistDao::class.java)
    val playlistTrackDao: PlaylistTrackDao = mock(PlaylistTrackDao::class.java)

    var repository: LocalRepository = LocalRepository(database)

    @Test
    fun getAllTracksTest() {
        val allTracksResponse = listOf(TrackEntity("A", "B", 1, "uri", "/image", 1))
        val resultingData = MusicTrackData(1, "A", "B", 1, "uri", "/image")
        val result: List<MusicTrackData>
        runBlocking {
            `when`(database.trackDao()).thenReturn(trackDao)
            `when`(trackDao.getAll()).thenReturn(allTracksResponse)
            result = repository.getAllTracks()
        }
        Assert.assertEquals(result.size, 1)
        Assert.assertEquals(result[0], resultingData)
    }

    @Test
    fun getRecentTracksTest() {
        val allTracksResponse = listOf(TrackEntity("A", "B", 1, "uri", "/image", 1))
        val resultingData = MusicTrackData(1, "A", "B", 1, "uri", "/image")
        val result: List<MusicTrackData>
        runBlocking {
            `when`(database.trackDao()).thenReturn(trackDao)
            `when`(trackDao.getAll()).thenReturn(allTracksResponse)
            result = repository.getRecentTracks()
        }
        Assert.assertEquals(result.size, 1)
        Assert.assertEquals(result[0], resultingData)
    }

    @Test
    fun getUserTracksTest() {
        val allTracksResponse = listOf(TrackEntity("A", "B", 1, "uri", "/image", 1))
        val resultingData = MusicTrackData(1, "A", "B", 1, "uri", "/image")
        val result: List<MusicTrackData>
        runBlocking {
            `when`(database.trackDao()).thenReturn(trackDao)
            `when`(trackDao.getAll()).thenReturn(allTracksResponse)
            result = repository.getAllTracks()
        }
        Assert.assertEquals(result.size, 1)
        Assert.assertEquals(result[0], resultingData)
    }

    @Test
    fun getPlaylistTest() {
        val playlistResponse = PlaylistEntity("A", "aa", 1)
        val tracks =
            PlaylistWithTracks(playlistResponse, listOf(TrackEntity("A", "B", 1, "uri", "", 1)))

        val resultingData =
            PlaylistData("", "aa", 1, "A", listOf(MusicTrackData(1, "A", "B", 1, "uri", "")))

        val result: PlaylistData
        runBlocking {
            `when`(database.playlistDao()).thenReturn(playlistDao)
            `when`(playlistDao.getPlaylistById(1)).thenReturn(playlistResponse)
            `when`(database.playlistTrackDao()).thenReturn(playlistTrackDao)
            `when`(playlistTrackDao.getPlaylistWithTracksByPlaylistId(1)).thenReturn(tracks)
            result = repository.getPlayList(1)
        }
        Assert.assertEquals(result, resultingData)
    }

    @Test
    fun getArtistTest() {
        val allTracksResponse = listOf(TrackEntity("A", "B", 1, "uri", "/image", 1))
        val resultingData = listOf(
            MusicTrackData(
                1,
                "A",
                "B",
                1,
                "uri",
                "/image"
            )
        )
        val result: List<MusicTrackData>
        runBlocking {
            `when`(database.trackDao()).thenReturn(trackDao)
            `when`(trackDao.getTracksByArtist("A")).thenReturn(allTracksResponse)
            result = repository.getArtistTracks("A")
        }
        Assert.assertEquals(result, resultingData)
    }
}