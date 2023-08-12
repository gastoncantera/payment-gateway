package org.example.modules

import org.example.core.infrastructure.http.ClientFactory
import org.example.core.infrastructure.http.ClientFactory.closeWithEngine
import org.example.modules.ConfigurationProvider.config
import org.slf4j.LoggerFactory

object GatewaysProvider {
    private val logger = LoggerFactory.getLogger(this::class.java)

    data class HttpEngineConfiguration(
        val connectTimeoutMs: Int,
        val connectionRequestTimeoutMs: Int,
        val maxConnTotal: Int,
        val maxConnPerRoute: Int
    )

    data class AdyenConfiguration(
        val url: String,
        val apiKey: String,
        var apiVersion: String,
        val merchantAccount: String
    )

    val adyenHttpClient by lazy {
        ClientFactory.makeClient(ClientFactory.Config(
            baseUrl = config.adyen.url,
            connectTimeoutMs = config.http.connectTimeoutMs,
            connectionRequestTimeoutMs = config.http.connectionRequestTimeoutMs,
            maxConnTotal = config.http.maxConnTotal,
            maxConnPerRoute = config.http.maxConnPerRoute
        ))
    }

    fun closeClients() {
        logger.info("Stopping http clients")
        adyenHttpClient.closeWithEngine()
        logger.info("Http clients stopped")
    }
}