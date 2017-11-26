package fr.cph.crypto.domain

import java.util.*

enum class Currency private constructor(val currencyName: String, val symbol: String, val type: Type) {
    BTC("Bitcoin", "฿", Type.CRYPTO),
    ETH("Ether", "Ξ", Type.CRYPTO),
    GRS("Groestlcoin", "GRS", Type.CRYPTO),
    LTC("Litecoin", "Ł", Type.CRYPTO),
    VTC("Vertcoin", "VTC", Type.CRYPTO),

    USD("United States Dollar", "$", Type.FIAT),
    EUR("Euro", "€", Type.FIAT),
    UNKNOWN("Unknown", "U", Type.FIAT);

    val code: String
        get() = this.name

    internal enum class Type {
        FIAT,
        CRYPTO
    }

    companion object {
        fun findCurrency(str: String): Currency {
            return Arrays.stream(Currency.values())
                    .filter { currency -> currency.name == str }
                    .findAny()
                    .orElse(Currency.UNKNOWN)
        }
    }
}
