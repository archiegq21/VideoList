package com.quibbly.videolist.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.google.accompanist.insets.ProvideWindowInsets
import com.quibbly.videolist.ui.theme.VideoListTheme

@Composable
fun VideoListApp(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    ProvideWindowInsets(
        windowInsetsAnimationsEnabled = true
    ) {
        VideoListTheme(
            darkTheme = darkTheme,
            content = content,
        )
    }
}