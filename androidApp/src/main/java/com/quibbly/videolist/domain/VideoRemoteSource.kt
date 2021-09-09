package com.quibbly.videolist.domain


import io.ktor.client.*
import io.ktor.client.request.*

interface VideoRemoteSource {
    suspend fun fetchVideoList(): Result<List<Video>>
}

class VideoRemoteSourceImpl(
    private val httpClient: HttpClient,
) : VideoRemoteSource {

    override suspend fun fetchVideoList(): Result<List<Video>> = httpClient.runCatching {
        get(urlString = "/native-technical-exam/playlist.json")
    }

}


suspend fun <T> HttpClient.runCatching(action: suspend HttpClient.() -> T): Result<T> = try {
    val result = action()
    Result.success(result)
} catch (e: Throwable) {
    Result.failure(e)
}

