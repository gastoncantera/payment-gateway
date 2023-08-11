package org.example.delivery.http.handler.core

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import org.example.core.domain.action.GetPaymentMethods
import org.example.core.domain.exception.PaymentProviderException
import org.example.delivery.http.handler.Handler

class PaymentHandler(private val getPaymentMethods: GetPaymentMethods) : Handler {

    override fun routing(a: Application) {
        a.routing {
            get("/payment/methods") { handleGetPaymentMethods() }
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
}