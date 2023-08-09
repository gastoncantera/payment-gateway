package org.example.modules

import org.example.delivery.http.error.ExceptionMapper
import org.example.delivery.http.HttpApiServer
import org.example.delivery.http.handler.administrative.StatusHandler
import org.example.modules.ConfigurationProvider.config

object HttpDeliveryProvider {

    private val exceptionMapper by lazy {
        ExceptionMapper()
    }

    private val statusHandler by lazy {
        StatusHandler()
    }

    val apiServer by lazy {
        HttpApiServer(
            config.app,
            exceptionMapper,
            statusHandler
        )
    }
}