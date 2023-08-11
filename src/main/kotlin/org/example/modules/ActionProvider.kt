package org.example.modules

import org.example.core.domain.action.AddCreditCardToWallet
import org.example.core.domain.action.GetPaymentMethods
import org.example.core.domain.action.MakeCreditCardPayment
import org.example.modules.RepositoryProvider.creditCardWalletRepository
import org.example.modules.ServiceProvider.paymentsService

object ActionProvider {

    val addCreditCardToWallet by lazy {
        AddCreditCardToWallet(creditCardWalletRepository)
    }

    val getPaymentMethods by lazy {
        GetPaymentMethods(paymentsService)
    }

    val makeCreditCardPayment by lazy {
        MakeCreditCardPayment(paymentsService)
    }
}