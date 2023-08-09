package org.example.modules

import org.example.core.domain.action.GetPaymentMethods
import org.example.modules.ServiceProvider.paymentsService

object ActionProvider {

    val getPaymentMethods by lazy {
        GetPaymentMethods(paymentsService)
    }
}