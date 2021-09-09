package com.quibbly.videolist.di

import com.quibbly.videolist.domain.VideoRemoteSource
import com.quibbly.videolist.domain.VideoRemoteSourceImpl
import org.koin.dsl.module

fun videoModule() = module {

    single<VideoRemoteSource> {
        VideoRemoteSourceImpl(get())
    }

}