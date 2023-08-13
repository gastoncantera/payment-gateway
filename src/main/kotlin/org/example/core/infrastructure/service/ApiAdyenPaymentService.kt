package org.example.core.infrastructure.service

import com.adyen.model.checkout.*
import com.adyen.service.checkout.PaymentsApi
import com.adyen.service.exception.ApiException
import org.example.core.domain.exception.PaymentProviderException
import org.example.core.domain.infrastructure.service.AdyenPaymentService
import org.example.modules.ConfigurationProvider
import org.slf4j.LoggerFactory
import java.util.*

class ApiAdyenPaymentService(
    private val paymentsApi: PaymentsApi
) : AdyenPaymentService {

    private val logger = LoggerFactory.getLogger(ApiAdyenPaymentService::class.java)

    override suspend fun getPaymentMethods(): PaymentMethodsResponse {
        val paymentMethodsRequest = PaymentMethodsRequest()
            .merchantAccount(ConfigurationProvider.config.adyen.merchantAccount)

        try {
            return paymentsApi.paymentMethods(paymentMethodsRequest)
        } catch (e: ApiException) {
            logger.error("Adyen API Exception = {}", e.toString())
            throw PaymentProviderException("Payment provider error")
        }
    }

    override suspend fun creditCardPayment(cardDetails: CardDetails, amount: Amount): PaymentResponse {
        val paymentRequest = PaymentRequest()
            .merchantAccount(ConfigurationProvider.config.adyen.merchantAccount)
            .returnUrl("")
            .reference(UUID.randomUUID().toString())
            .paymentMethod(CheckoutPaymentMethod(cardDetails))
            .amount(amount)

        try {
            return paymentsApi.payments(paymentRequest)
        } catch (e: ApiException) {
            logger.error("Adyen API Exception = {}", e.toString())
            throw PaymentProviderException("Payment provider error")
        }
    }
}