package com.quibbly.videolist.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.quibbly.videolist.MainViewModel

@Composable
fun MainContent(
    mainViewModel: MainViewModel,
) {
    val navController = rememberNavController()

    VideoListApp {
        NavGraph(
            mainViewModel = mainViewModel,
            navController = navController,
            modifier = Modifier,
        )
    }

}