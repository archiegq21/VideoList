package com.quibbly.videolist.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun MainContent() {
    val navController = rememberNavController()

    VideoListApp {
        NavGraph(
            navController = navController,
            modifier = Modifier,
        )
    }

}