package org.example.modules

import org.example.core.infrastructure.service.AdyenPaymentService
import org.example.modules.GatewaysProvider.adyenClient

object ServiceProvider {

    val paymentsService by lazy {
        AdyenPaymentService(adyenClient)
    }
}
