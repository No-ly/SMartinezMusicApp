package com.example.smartinezmusicapp

import android.app.Application
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.crossfade
import okhttp3.OkHttpClient

class MusicApp : Application(), SingletonImageLoader.Factory {

    override fun newImageLoader(context: coil3.PlatformContext): ImageLoader {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("User-Agent", "SMartinezMusicApp/1.0 (Android)")
                    .build()
                chain.proceed(request)
            }
            .build()

        return ImageLoader.Builder(context)
            .components {
                add(OkHttpNetworkFetcherFactory(callFactory = { okHttpClient }))
            }
            .crossfade(true)
            .build()
    }
}
