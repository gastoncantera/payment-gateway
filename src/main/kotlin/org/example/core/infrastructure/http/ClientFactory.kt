package org.example.core.infrastructure.http

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.apache.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object ClientFactory {

    data class Config(
        val baseUrl: String,
        val connectTimeoutMs: Int,
        val connectionRequestTimeoutMs: Int,
        val maxConnTotal: Int,
        val maxConnPerRoute: Int
    ) {
        fun url(): Url = Url(baseUrl)
        fun engine() = Apache.create {
            connectTimeout = connectTimeoutMs
            connectionRequestTimeout = connectionRequestTimeoutMs
            customizeClient {
                setMaxConnTotal(maxConnTotal)
                setMaxConnPerRoute(maxConnPerRoute)
            }
        }
    }

    fun makeClient(config: Config) = makeClient(config.url(), config.engine())

    private fun makeClient(
        targetBaseUrl: Url,
        engine: HttpClientEngine
    ): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        this.ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                url {
                    protocol = targetBaseUrl.protocol
                    host = targetBaseUrl.host
                    port = targetBaseUrl.port
                }
            }
        }
    }

    fun HttpClient.closeWithEngine() {
        close()
        engine.close()
    }
}