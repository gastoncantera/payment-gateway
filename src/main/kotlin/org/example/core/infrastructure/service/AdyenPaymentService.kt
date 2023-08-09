package org.example.core.infrastructure.service

import com.adyen.model.checkout.PaymentMethodsRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.apache.http.HttpException
import org.example.core.domain.exception.PaymentProviderException
import org.example.core.domain.infrastructure.service.PaymentService
import org.example.modules.ConfigurationProvider.config
import org.slf4j.LoggerFactory

class AdyenPaymentService(
    private val httpClient: HttpClient
) : PaymentService {

    private val logger = LoggerFactory.getLogger(AdyenPaymentService::class.java)

    override suspend fun getPaymentMethods(): String {
        val body = PaymentMethodsRequest()
        body.merchantAccount = config.adyen.merchantAccount

        val httpResponse = httpClient.post(config.adyen.apiVersion + "/paymentMethods") {
            header("X-API-Key", config.adyen.apiKey)
            contentType(ContentType.Application.Json)
            setBody(body.toJson())
        }

        when (httpResponse.status.isSuccess()) {
            true -> return httpResponse.body<String>()
            else -> {
                logger.error("Invalid response = {}", httpResponse)
                throw PaymentProviderException("Invalid provider status response: " + httpResponse.status.toString())
            }
        }
    }
}