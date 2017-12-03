package fr.cph.crypto.backend.domain

import java.util.*

enum class Currency constructor(val code: String, val currencyName: String, val symbol: String, val type: Type) {
    BTC("BTC", "Bitcoin", "฿", Type.CRYPTO),
    ETH("ETH", "Ether", "Ξ", Type.CRYPTO),
    GRS("GRS", "Groestlcoin", "GRS", Type.CRYPTO),
    LTC("LTC", "Litecoin", "Ł", Type.CRYPTO),
    VTC("VTC", "Vertcoin", "VTC", Type.CRYPTO),

    USD("USD", "United States Dollar", "$", Type.FIAT),
    EUR("EUR", "Euro", "€", Type.FIAT),
    UNKNOWN("UNKNOWN", "Unknown", "U", Type.FIAT);

    internal enum class Type {
        FIAT,
        CRYPTO
    }

    companion object {
        fun findCurrency(str: String): Currency {
            return Arrays.stream(Currency.values())
                    .filter { currency -> currency.code == str }
                    .findAny()
                    .orElse(Currency.UNKNOWN)
        }

        fun cryptoCurrenciesAsListOfString(): List<String> {
            return Currency.values()
                    .filter { currency -> currency.type == Currency.Type.CRYPTO }
                    .map { currency -> currency.code }
                    .toList()
        }
    }
}
