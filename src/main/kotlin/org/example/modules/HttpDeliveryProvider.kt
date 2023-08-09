package org.example.modules

import org.example.core.domain.action.GetPaymentMethods
import org.example.delivery.http.error.ExceptionMapper
import org.example.delivery.http.HttpApiServer
import org.example.delivery.http.handler.administrative.StatusHandler
import org.example.delivery.http.handler.core.PaymentHandler
import org.example.modules.ActionProvider.getPaymentMethods
import org.example.modules.ConfigurationProvider.config

object HttpDeliveryProvider {

    private val exceptionMapper by lazy {
        ExceptionMapper()
    }

    private val statusHandler by lazy {
        StatusHandler()
    }

    private val paymentHandler by lazy {
        PaymentHandler(getPaymentMethods)
    }

    val apiServer by lazy {
        HttpApiServer(
            config.app,
            exceptionMapper,
            statusHandler,
            paymentHandler
        )
    }
}