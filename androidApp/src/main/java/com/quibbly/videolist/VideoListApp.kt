package com.quibbly.videolist

import android.app.Application
import com.quibbly.videolist.di.mainModule
import com.quibbly.videolist.di.networkModule
import com.quibbly.videolist.di.videoModule
import org.koin.core.context.startKoin

class VideoListApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                networkModule(),
                mainModule(),
                videoModule(),
            )
        }
    }

}