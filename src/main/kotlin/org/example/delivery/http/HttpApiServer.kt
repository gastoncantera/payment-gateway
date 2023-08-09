package org.example.delivery.http

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.example.delivery.http.error.ErrorResponse
import org.example.delivery.http.error.ExceptionMapper
import org.example.delivery.http.handler.Handler
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import java.util.concurrent.TimeUnit

class HttpApiServer(
    private val config: AppConfig,
    private val exceptionMapper: ExceptionMapper,
    private vararg val handlers: Handler
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val port = config.port
    private lateinit var server: ApplicationEngine

    data class AppConfig(
        val port: Int,
        val shutdown: ShutDownConfig
    )

    data class ShutDownConfig(
        val gracePeriod: Long,
        val stopTimeout: Long,
        val timeUnit: TimeUnit
    )

    fun start(wait: Boolean = true) {
        logger.info("Starting HttpApiServer in port $port")

        server = embeddedServer(Netty, port = port) {
            main()
        }

        server.start(wait)
    }

    fun stop() {
        with(config.shutdown) {
            logger.info("Stopping application engine using {}", this)
            server.stop(gracePeriod, stopTimeout, timeUnit)
        }
    }

    private fun Application.main() {
        installFeatures()

        routing {
            if (logger.isTraceEnabled) {
                trace { logger.trace(it.buildText()) }
            }
        }

        handlers.forEach { it.routing(this) }
    }

    private fun Application.installFeatures() {
        install(DefaultHeaders)
        install(Compression)
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        install(CallLogging) {
            level = Level.INFO
        }
        install(StatusPages) {
            exception<Throwable> { call, cause ->
                logger.error(cause::class.java.simpleName + ": " + cause.message, cause)
                call.respond(
                    exceptionMapper.toHttpCode(cause),
                    ErrorResponse(exceptionMapper.toDomainCode(cause), cause.localizedMessage)
                )
            }
        }
    }
}