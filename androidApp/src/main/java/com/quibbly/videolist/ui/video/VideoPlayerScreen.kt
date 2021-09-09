package com.quibbly.videolist.ui.video

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.quibbly.videolist.domain.Video

private enum class VideoPlayerState {
    Collapsed,
    Expanded
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VideoPlayerScreen(
    video: Video?,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier,
    ) {
        val expandedHeight = maxHeight
        var swipeProgress by remember {
            mutableStateOf(0f)
        }

        val expandedOffset = with(LocalDensity.current) {
            expandedHeight.toPx()
        }

        val swipeableState = rememberSwipeableState(VideoPlayerState.Collapsed)
        swipeProgress = swipeableState.offset.value / expandedOffset

        LaunchedEffect(key1 = video) {
            swipeableState.animateTo(
                if (video == null) {
                    VideoPlayerState.Collapsed
                } else {
                    VideoPlayerState.Expanded
                }
            )
        }

        LaunchedEffect(swipeableState.currentValue) {
            if (swipeableState.currentValue == VideoPlayerState.Collapsed) {
                onDismiss()
            }
        }

        val anchors = mapOf(
            0f to VideoPlayerState.Collapsed,
            expandedOffset to VideoPlayerState.Expanded,
        )

        val surfaceHeight by animateDpAsState(
            targetValue = lerp(0.dp, expandedHeight, swipeProgress)
        )

        Surface(
            modifier = Modifier
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    reverseDirection = true,
                    thresholds = { _, _ -> FractionalThreshold(0.0f) },
                    orientation = Orientation.Vertical,
                )
                .fillMaxWidth()
                .height(surfaceHeight)
        ) {
            VideoPlayer(
                video = video,
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .background(Color.Black),
            )
        }

    }
}