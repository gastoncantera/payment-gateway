package org.example.modules

import org.example.core.domain.action.GetPaymentMethods
import org.example.core.domain.action.MakeCreditCardPayment
import org.example.modules.ServiceProvider.paymentsService

object ActionProvider {

    val getPaymentMethods by lazy {
        GetPaymentMethods(paymentsService)
    }

    val makeCreditCardPayment by lazy {
        MakeCreditCardPayment(paymentsService)
    }
}