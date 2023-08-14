package org.example.core.domain.model.ultis

/**
 * Shamelessly stolen from https://gist.github.com/EmmanuelGuther/1ebe1bc2b00f7a540a93c3f7d62bf3e3
 */
object LuhnValidator {
    fun isValid(cardNumber: String): Boolean {
        var s1 = 0
        var s2 = 0
        val reverse = StringBuffer(cardNumber).reverse().toString()
        for (i in reverse.indices) {
            val digit = Character.digit(reverse[i], 10)
            when {
                i % 2 == 0 -> s1 += digit
                else -> {
                    s2 += 2 * digit
                    when {
                        digit >= 5 -> s2 -= 9
                    }
                }
            }
        }
        return (s1 + s2) % 10 == 0
    }
}