package org.example.modules

import org.example.core.infrastructure.service.ApiAdyenPaymentService
import org.example.core.infrastructure.service.HttpAdyenPaymentService
import org.example.modules.GatewaysProvider.adyenApiClient
import org.example.modules.GatewaysProvider.adyenHttpClient

object ServiceProvider {



    val adyenPaymentsService by lazy {
        ApiAdyenPaymentService(adyenApiClient)
        //HttpAdyenPaymentService(adyenHttpClient)
    }
}
