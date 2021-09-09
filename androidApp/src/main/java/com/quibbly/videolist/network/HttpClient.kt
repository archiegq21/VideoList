package com.quibbly.videolist.network

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.URLProtocol.Companion.HTTPS
import kotlinx.serialization.json.Json


fun HttpClient(
    baseUrl: String,
    json: Json,
): HttpClient {
    return HttpClient {
        defaultRequest {
            url {
                protocol = HTTPS
            }
            host = baseUrl
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 30000
            socketTimeoutMillis = 30000
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }
}