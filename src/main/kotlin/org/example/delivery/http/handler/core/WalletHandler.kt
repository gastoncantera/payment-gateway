package org.example.delivery.http.handler.core

import com.adyen.model.checkout.Amount
import com.adyen.model.checkout.CardDetails
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import org.example.core.domain.action.wallet.AddCreditCardToWallet
import org.example.core.domain.action.wallet.WalletCreditCardPayment
import org.example.core.domain.exception.PaymentProviderException
import org.example.core.domain.exception.WalletException
import org.example.delivery.http.handler.Handler
import org.example.delivery.http.handler.core.representation.AddToWalletRequest
import org.example.delivery.http.handler.core.representation.WalletPaymentRequest

class WalletHandler(
    private val addCreditCardToWallet: AddCreditCardToWallet,
    private val walletCreditCardPayment: WalletCreditCardPayment

) : Handler {

    override fun routing(a: Application) {
        a.routing {
            post("/wallet/add") { handleAddCreditCardToWallet() }
            post("/wallet/checkout") { handleWalletCreditCardPayment() }
        }
    }

    private suspend fun PipelineContext<Unit, ApplicationCall>.handleAddCreditCardToWallet() {
        val content = call.receive<AddToWalletRequest>()
        try {
            addCreditCardToWallet(
                AddCreditCardToWallet.ActionData(
                    content.walletId, content.cardId,
                    CardDetails()
                        .holderName(content.cardDetails.holderName)
                        .encryptedCardNumber("test_" + content.cardDetails.cardNumber)
                        .encryptedExpiryMonth("test_" + content.cardDetails.expiryMonth)
                        .encryptedExpiryYear("test_" + content.cardDetails.expiryYear)
                        .encryptedSecurityCode("test_" + content.cardDetails.securityCode)
                )
            ).let {
                call.respond(HttpStatusCode.OK)
            }
        } catch (e: WalletException) {
            call.respond(HttpStatusCode.BadRequest, e.message.toString())
        }
    }

    private suspend fun PipelineContext<Unit, ApplicationCall>.handleWalletCreditCardPayment() {
        val content = call.receive<WalletPaymentRequest>()
        try {
            walletCreditCardPayment(
                WalletCreditCardPayment.ActionData(
                    content.walletId, content.cardId,
                    "test_" + content.securityCode,
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