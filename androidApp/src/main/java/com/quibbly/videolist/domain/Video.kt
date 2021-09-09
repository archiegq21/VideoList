package com.quibbly.videolist.domain

import kotlinx.serialization.Serializable

@Serializable
data class Video(
    val title: String,
    val presenter_name: String,
    val description: String,
    val thumbnail_url: String,
    val video_url: String,
    val video_duration: Long,
)