package org.example.modules

import org.example.core.domain.action.wallet.AddCreditCardToWallet
import org.example.core.domain.action.GetPaymentMethods
import org.example.core.domain.action.DirectCreditCardPayment
import org.example.modules.RepositoryProvider.creditCardWalletRepository
import org.example.modules.ServiceProvider.paymentsService

object ActionProvider {

    val addCreditCardToWallet by lazy {
        AddCreditCardToWallet(creditCardWalletRepository)
    }

    val getPaymentMethods by lazy {
        GetPaymentMethods(paymentsService)
    }

    val directCreditCardPayment by lazy {
        DirectCreditCardPayment(paymentsService)
    }
}