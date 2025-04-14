package com.layka.musicapphse.screens.player

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import kotlin.math.min

class QueueModel(
    private val player: ExoPlayer
) {
    val trackList = mutableStateOf(listOf<MusicTrackData>())
    private val initialTrackList = mutableListOf<MusicTrackData>()
    val currentTrack = mutableStateOf<MusicTrackData?>(null)
    val isPlaying = mutableStateOf(false)
    val shuffleOn = mutableStateOf(false)
    private var trackIdx = 0
    val repeatOn = mutableStateOf(false)

    init {
        val model = this
        player.prepare()
        player.addListener(
            object : Player.Listener {
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    super.onMediaItemTransition(mediaItem, reason)
                    Log.v("PLAYER", reason.toString())
                    if (mediaItem != null && reason == 1) {
                        currentTrack.value = model.trackList.value[player.currentMediaItemIndex]
                        trackIdx = player.currentMediaItemIndex
                    }
                }
            }
        )
    }

    fun setQueue(newQueue: List<MusicTrackData>, currentTrack: UInt = 0U) {
        pausePlayer()
        if (trackList != newQueue) {
            trackList.value = newQueue
            initialTrackList.clear()
            newQueue.forEach {
                initialTrackList.add(it)
            }
            player.clearMediaItems()
            addMediaItemsToPlayer(trackList.value)
        }
        player.seekTo(min(currentTrack.toInt(), player.mediaItemCount), 0L)
        trackIdx = min(currentTrack.toInt(), player.mediaItemCount)
        this.currentTrack.value = trackList.value[trackIdx]
        Log.v("QUEUE_MODEL", player.currentMediaItemIndex.toString())
        startPlayer()
        isPlaying.value = true
    }

    private fun addMediaItemsToPlayer(newQueue: List<MusicTrackData>) {

        newQueue.forEach {
            Log.v("QUEUE_MODEL", it.uri)
            player.addMediaItem(convertToMediaItem(it))
        }
    }

    private fun convertToMediaItem(data: MusicTrackData): MediaItem {
        return MediaItem.fromUri(Uri.parse(data.uri))
    }

    fun startPlayer() {
        isPlaying.value = true
        player.play()
    }

    fun pausePlayer() {
        isPlaying.value = false
        player.pause()
    }

    fun playNext() {
        Log.v("PLAYER", " next ${player.currentMediaItemIndex} ${trackIdx}")

        player.seekToNext()
        trackIdx = player.currentMediaItemIndex
        currentTrack.value = trackList.value[trackIdx]

        Log.v("PLAYER", "next after changes ${player.currentMediaItemIndex} ${trackIdx}")
    }

    fun playPrevious() {
        Log.v("PLAYER", " prev ${player.currentMediaItemIndex} ${trackIdx}")

        player.seekToPrevious()
        trackIdx = player.currentMediaItemIndex
        currentTrack.value = trackList.value[trackIdx]


        Log.v("PLAYER", "prev after changes ${player.currentMediaItemIndex} ${trackIdx}")
    }

    fun getCurrPosition(): Long {
        return player.currentPosition
    }

    fun changePosition(pos: Long) {
        player.seekTo(pos)
    }

    fun shuffle() {
        player.pause()
        val currPos = player.currentPosition
        if (!shuffleOn.value) {
            shuffleOn.value = true
            val currIdx = player.currentMediaItemIndex
            val newList = initialTrackList.toMutableList()
            newList.remove(currentTrack.value)
            newList.shuffle()
            if (currentTrack.value != null)
                newList.add(0, currentTrack.value!!)
            trackList.value = newList

            player.clearMediaItems()
            addMediaItemsToPlayer(trackList.value)
            player.seekTo(0, currPos)
        } else {
            shuffleOn.value = false
            trackList.value = initialTrackList
            val initialIdx = initialTrackList.indexOf(currentTrack.value)

            player.clearMediaItems()
            addMediaItemsToPlayer(trackList.value)
            player.seekTo(initialIdx, currPos)
        }
        player.play()
        isPlaying.value = true
    }

    fun toggleRepeatMode() {
        if (!repeatOn.value) {
            player.repeatMode = Player.REPEAT_MODE_ALL
        } else {
            player.repeatMode = Player.REPEAT_MODE_OFF
        }
        repeatOn.value = !repeatOn.value
    }

//    @SuppressLint("Range")
//    fun getQueue(): List<MusicTrackData> {
//        val cursor: Cursor? = resolver.query(
//            MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
//            arrayOf<String>(
//                MediaStore.Audio.Media._ID,
//                MediaStore.Audio.Media.TITLE,
//                MediaStore.Audio.Media.ARTIST,
//                MediaStore.Audio.Media.DURATION,
//                MediaStore.Audio.Media.DATA
//            ),
//            null,
//            null,
//            null
//        )
//        val tracks = mutableListOf<MusicTrackData>()
//        if (cursor?.moveToFirst() == true) {
//            do {
//                val data = MusicTrackData(
//                    cursor.getInt(
//                        cursor.getColumnIndex(MediaStore.Audio.Media._ID)
//                    ),
//                    cursor.getString(
//                        cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
//                    ),
//
//
//                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
//                    cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)),
//                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)),
//                    null
//                )
//                tracks.add(data)
//            } while (cursor.moveToNext());
//        }
//        cursor?.close()
//        return tracks
//    }

    fun getCurrentTrack(): MusicTrackData {
        if (trackList.value.isEmpty()) return MusicTrackData(-1, "", "", 0, "")
        return trackList.value[player.currentMediaItemIndex]
    }

//    private fun onDestroy() {
//        application.onTerminate()
//        player.release()
//    }

}