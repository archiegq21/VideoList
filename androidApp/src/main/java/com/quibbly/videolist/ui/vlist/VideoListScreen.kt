package com.quibbly.videolist.ui.vlist

import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.quibbly.videolist.VideoListState
import com.quibbly.videolist.domain.Video
import com.quibbly.videolist.ui.video.VideoPlayer
import com.quibbly.videolist.ui.video.VideoPlayerScreen
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@Composable
fun VideoListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    videoState: VideoListState,
) {
    var selectedVideo by remember { mutableStateOf<Video?>(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = {
                        Text("VideoList")
                    },
                )
            },
        ) { padding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = padding,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                videoState.videos.forEach { video ->
                    item(key = video.title) {
                        VideoItem(
                            video = video,
                            onClick = {
                                selectedVideo = video
                            }
                        )
                    }
                }
            }
        }
        VideoPlayerScreen(
            modifier = Modifier.align(Alignment.BottomCenter),
            video = selectedVideo,
            onDismiss = { selectedVideo = null }
        )
    }
}

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalCoilApi::class,
)
@Composable
private fun VideoItem(
    video: Video,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RectangleShape,
        elevation = 0.dp,
        onClick = onClick,
    ) {
        Column(
            modifier = modifier,
        ) {
            Box {
                Image(
                    painter = rememberImagePainter(video.thumbnail_url),
                    contentDescription = video.thumbnail_url,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .aspectRatio(16f / 9f),
                )
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.Black.copy(0.5f), CircleShape)
                        .padding(2.dp)
                        .align(Alignment.BottomEnd),
                    color = Color.White,
                    text = video.video_duration.toFormatDuration(),
                )
            }
            Column(
                modifier = Modifier.padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )
            ) {
                Text(
                    text = "${video.title}: ${video.description}",
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = video.presenter_name,
                    style = MaterialTheme.typography.caption,
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
fun Long.toFormatDuration(): String = Duration.milliseconds(this)
    .toComponents { _, hours, minutes, seconds, _ ->
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
