package com.quibbly.videolist.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.quibbly.videolist.samples.mockVideos
import com.quibbly.videolist.ui.vlist.VideoListScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = VideoListDestination.DASHBOARD_ROUTE,
    ) {
        composable(VideoListDestination.DASHBOARD_ROUTE) {
            VideoListScreen(
                modifier = modifier,
                navController = navController,
                videos = mockVideos,
            )
        }
    }
}

object VideoListDestination {
    const val DASHBOARD_ROUTE = "/dashboard"
}