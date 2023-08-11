package org.example.modules

import org.example.core.infrastructure.repository.InMemoryCreditCardWalletRepository

object RepositoryProvider {

    val creditCardWalletRepository by lazy {
        InMemoryCreditCardWalletRepository()
    }
}