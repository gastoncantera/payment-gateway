package org.example.delivery.http.handler.core

import com.adyen.model.checkout.Amount
import com.adyen.model.checkout.CardDetails
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import org.example.core.domain.action.GetPaymentMethods
import org.example.core.domain.action.DirectCreditCardPayment
import org.example.core.domain.exception.PaymentProviderException
import org.example.delivery.http.handler.Handler
import org.example.delivery.http.handler.core.representation.DirectPaymentRequest

class PaymentHandler(
    private val getPaymentMethods: GetPaymentMethods,
    private val directCreditCardPayment: DirectCreditCardPayment
) : Handler {

    override fun routing(a: Application) {
        a.routing {
            get("/payment/methods") { handleGetPaymentMethods() }
            post("/payment/checkout") { handleDirectCreditCardPayment() }
        }
    }

    private suspend fun PipelineContext<Unit, ApplicationCall>.handleGetPaymentMethods() {
        try {
            getPaymentMethods().let {
                call.respond(HttpStatusCode.OK, it)
            }
        } catch (e: PaymentProviderException) {
            call.respond(HttpStatusCode.BadRequest, e.message.toString())
        }
    }

    private suspend fun PipelineContext<Unit, ApplicationCall>.handleDirectCreditCardPayment() {
        val content = call.receive<DirectPaymentRequest>()
        try {
            directCreditCardPayment(
                DirectCreditCardPayment.ActionData(
                    CardDetails()
                        .holderName(content.cardDetails.holderName)
                        .encryptedCardNumber("test_" + content.cardDetails.cardNumber)
                        .encryptedExpiryMonth("test_" + content.cardDetails.expiryMonth)
                        .encryptedExpiryYear("test_" + content.cardDetails.expiryYear)
                        .encryptedSecurityCode("test_" + content.cardDetails.securityCode),
                    Amount()
                        .currency(content.amount.currency)
                        .value(content.amount.value)
                )
            ).let {
                call.respond(HttpStatusCode.OK, it)
            }
        } catch (e: PaymentProviderException) {
            call.respond(HttpStatusCode.BadRequest, e.message.toString())
        }
    }
}