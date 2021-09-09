package com.quibbly.videolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quibbly.videolist.domain.Video
import com.quibbly.videolist.domain.VideoRemoteSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val remoteSource: VideoRemoteSource,
) : ViewModel() {

    private val _videosState = MutableStateFlow(VideoListState())
    val videoState = _videosState.asStateFlow()

    init {
        fetchVideos()
    }

    fun fetchVideos() {
        viewModelScope.launch {
            val result = remoteSource.fetchVideoList()

            _videosState.emit(
                videoState.value.LoadingVideoListState()
            )

            result.fold({
                _videosState.emit(
                    videoState.value.SuccessVideoListState(it)
                )
            }, {
                _videosState.emit(
                    videoState.value.FailedVideoListState(it)
                )
            })
        }
    }
}

data class VideoListState(
    val videos: List<Video> = emptyList(),
    val loading: Boolean = false,
    val error: Throwable? = null,
) {
    fun FailedVideoListState(e: Throwable) =
        this.copy(error = e, loading = false)

    fun SuccessVideoListState(videos: List<Video>) =
        this.copy(videos = videos, loading = false, error = null)

    fun LoadingVideoListState() =
        this.copy(loading = true)
}