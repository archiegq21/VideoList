package com.quibbly.videolist.di

import com.quibbly.videolist.MainViewModel
import com.quibbly.videolist.network.HttpClient
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun mainModule() = module {

    viewModel {
        MainViewModel(get())
    }

}