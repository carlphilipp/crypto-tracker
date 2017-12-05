package fr.cph.crypto.backend.client.impl

import fr.cph.crypto.backend.domain.Currency
import fr.cph.crypto.backend.domain.Currency.EUR
import fr.cph.crypto.backend.domain.Currency.USD
import fr.cph.crypto.backend.domain.Ticker

object TickerMapper {

    fun responseToTicker(currency: Currency, response: Response): Ticker {
        val price: Double = when (currency) {
            USD -> response.priceUsd!!.toDouble()
            EUR -> response.priceEur!!.toDouble()
            else -> 0.0
        }
        val volume24h = if (response._24hVolumeUsd == null) 0.0 else response._24hVolumeUsd!!.toDouble()
        val marketCap = if (response.marketCapUsd == null) 0.0 else response.marketCapUsd!!.toDouble()
        val percentChange1h = if (response.percentChange1h == null) 0.0 else response.percentChange1h!!.toDouble()
        val percentChange24h = if (response.percentChange24h == null) 0.0 else response.percentChange24h!!.toDouble()
        val percentChange7d = if (response.percentChange7d == null) 0.0 else response.percentChange7d!!.toDouble()
        val result = Ticker(
                Currency.findCurrency(response.symbol!!),
                Currency.findCurrency(response.symbol!!).currencyName,
                currency,
                price,
                "coinmarketcap",
                volume24h,
                marketCap,
                percentChange1h / 100,
                percentChange24h / 100,
                percentChange7d / 100,
                response.lastUpdated!!.toLong()
        )
        result.id = response.symbol + "-" + currency.code
        return result
    }
}


