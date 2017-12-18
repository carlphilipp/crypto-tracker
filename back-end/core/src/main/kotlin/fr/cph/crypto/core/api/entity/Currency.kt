package fr.cph.crypto.core.api.entity

import java.util.*

enum class Currency constructor(val code: String, val currencyName: String, val symbol: String, val type: Type) {
    BTC("BTC", "Bitcoin", "฿", Type.CRYPTO),
    ETH("ETH", "Ethereum", "Ξ", Type.CRYPTO),
    GRS("GRS", "Groestlcoin", "GRS", Type.CRYPTO),
    LTC("LTC", "Litecoin", "Ł", Type.CRYPTO),
    VTC("VTC", "Vertcoin", "VTC", Type.CRYPTO),
    ETHOS("ETHOS", "Ethos", "ETHOS", Type.CRYPTO),
    ADA("ADA", "Cardano", "ADA", Type.CRYPTO),
    POWR("POWR", "Power Ledger", "POWR", Type.CRYPTO),

    USD("USD", "United States Dollar", "$", Type.FIAT),
    EUR("EUR", "Euro", "€", Type.FIAT),
    UNKNOWN("UNKNOWN", "Unknown", "U", Type.FIAT);

    internal enum class Type {
        FIAT,
        CRYPTO
    }

    companion object {
        fun findCurrency(code: String): Currency {
            return Arrays.stream(Currency.values())
                    .filter { currency -> currency.code == code }
                    .findAny()
                    .orElse(UNKNOWN)
        }

        fun cryptoCurrenciesAsListOfString(): List<String> {
            return Currency.values()
                    .filter { currency -> currency.type == Type.CRYPTO }
                    .map { currency -> currency.code }
                    .toList()
        }
    }
}
