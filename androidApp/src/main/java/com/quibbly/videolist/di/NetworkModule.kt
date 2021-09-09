package com.quibbly.videolist.di

import com.quibbly.videolist.network.HttpClient
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun networkModule() = module {

    single {
        Json {
            allowStructuredMapKeys = true
            ignoreUnknownKeys = true
        }
    }

    single {
        HttpClient(
            baseUrl = "quipper.github.io",
            json = get(),
        )
    }

}