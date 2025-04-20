package com.layka.musicapphse

import android.content.Context
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.ResolvingDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.layka.musicapphse.screens.player.QueueModel
import com.layka.musicapphse.services.AuthApi
import com.layka.musicapphse.services.TokenManager
import com.layka.musicapphse.storage.LocalRepository
import com.layka.musicapphse.storage.httpRepo.MusicApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


@UnstableApi
@Module
@InstallIn(SingletonComponent::class)
class Providers {

    private var tokenManager: TokenManager? = null

    @OptIn(UnstableApi::class)
    private fun headers(tokenManager: TokenManager): Map<String, String> {
        val headersMap: MutableMap<String, String> = HashMap()
        val tokenValue = runBlocking {
            tokenManager.getToken().first()
        }
        headersMap["Authorization"] = "Bearer $tokenValue"
        return headersMap
    }

    private fun dataSourceFactory(tokenManager: TokenManager, @ApplicationContext context: Context): DataSource.Factory {
        return ResolvingDataSource.Factory( DefaultDataSourceFactory(context)) { dataSpec: DataSpec ->
            Log.v("PLAYER", dataSpec.uri.toString())
            dataSpec.withRequestHeaders(headers(tokenManager))
        }
    }

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        if (tokenManager == null) {
            tokenManager = TokenManager(context)
        }
        return tokenManager!!
    }

    @OptIn(UnstableApi::class)
    @Provides
    @Inject
    fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer {
        return ExoPlayer.Builder(context)
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(context).setDataSourceFactory(
                    dataSourceFactory(provideTokenManager(context), context)
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkRepo(): MusicApi {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.29:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MusicApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkAuth(): AuthApi {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.29:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLocalRepo(): LocalRepository {
        return LocalRepository()
    }

    @Provides
    @Singleton
    fun provideQueueModel(@ApplicationContext context: Context): QueueModel {
        return QueueModel(provideExoPlayer(context))
    }
}