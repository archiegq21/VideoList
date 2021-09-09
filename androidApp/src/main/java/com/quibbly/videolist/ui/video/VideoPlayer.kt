package com.quibbly.videolist.ui.video

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.quibbly.videolist.R
import com.quibbly.videolist.domain.Video


@Composable
fun VideoPlayer(
    video: Video?,
    modifier: Modifier = Modifier,
) {
    val simpleExoPlayer = rememberSimpleExoPlayer()

    LaunchedEffect(video) {
        simpleExoPlayer.apply {
            if (video != null) {
                val mediaItem = MediaItem.fromUri(video.video_url)
                setMediaItem(mediaItem)

                prepare()
                playWhenReady = true
            } else {
                stop()
            }
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            PlayerView(it).apply {
                layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                id = R.id.player
            }
        },
    ) { videoView ->
        videoView.player = simpleExoPlayer
    }
}

@Composable
fun rememberSimpleExoPlayer(): SimpleExoPlayer {
    val context = LocalContext.current
    val simpleExoPlayer = remember {
        SimpleExoPlayer.Builder(context).build()
    }

    val lifecycleOwner = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    simpleExoPlayer.playWhenReady = false
                }
                Lifecycle.Event.ON_RESUME -> {
                    simpleExoPlayer.playWhenReady = true
                }
                Lifecycle.Event.ON_DESTROY -> {
                    simpleExoPlayer.run {
                        stop()
                        release()
                    }
                }
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    return simpleExoPlayer
}
