package com.layka.musicapphse.services

import android.net.Uri
import android.os.Environment
import android.util.Log
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import com.layka.musicapphse.storage.Repository
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DownloadService @Inject constructor(val repository: Repository, val tokenManager: TokenManager) {
    suspend fun downloadTrack(uri: String, fileName: String): String? {
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .addHeader("Authorization", "Bearer "+tokenManager.getToken().first())
            .url(uri)
            .build()
        var musicFile: String? = null

        withContext(Dispatchers.IO) {
            try {
                val response: Response = okHttpClient.newCall(request).execute()

                val inputStream = response.body?.byteStream()

                val path = File(Environment.getExternalStorageDirectory().toString() + "/Music/Stuff")
                if (!path.exists()) {
                    path.mkdirs()
                }

                val imageFile = File(path, fileName)
                if (imageFile.exists()) {
                    imageFile.delete()
                }


                musicFile = imageFile.toURI().toString()
                Log.d("SAVE_TRACK", musicFile ?: "no file")

                val fos = FileOutputStream(imageFile)
                fos.write(inputStream?.readBytes())
                fos.flush()
                fos.close()

            } catch (e: IOException) {
                e.printStackTrace()
                Log.v("SAVE_TRACK", e.toString())
            }
        }
        return musicFile
    }
}