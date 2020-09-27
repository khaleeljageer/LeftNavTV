package com.jskaleel.leftnav_tv

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.CoilUtils
import okhttp3.OkHttpClient

class CustomApp : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(applicationContext)
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(applicationContext))
                    .build()
            }.build()
    }
}