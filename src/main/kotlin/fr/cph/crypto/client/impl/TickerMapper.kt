package fr.cph.crypto.client.impl

import fr.cph.crypto.domain.Currency
import fr.cph.crypto.domain.Currency.EUR
import fr.cph.crypto.domain.Currency.USD
import fr.cph.crypto.domain.Ticker

object TickerMapper {

    fun responseToTicker(currency: Currency, response: Response): Ticker {
        val price: Double = when (currency) {
            USD -> response.priceUsd!!.toDouble()
            EUR -> response.priceEur!!.toDouble()
            else -> 0.0
        }
        val percentChange1h = if (response.percentChange1h == null) 0.0 else response.percentChange1h!!.toDouble()
        val percentChange24h = if (response.percentChange24h == null) 0.0 else response.percentChange24h!!.toDouble()
        val percentChange7d = if (response.percentChange7d == null) 0.0 else response.percentChange7d!!.toDouble()
        val result = Ticker(
                Currency.findCurrency(response.symbol!!),
                currency,
                price,
                "coinmarketcap",
                percentChange1h,
                percentChange24h,
                percentChange7d,
                response.lastUpdated!!.toLong()
        )
        result.id = response.symbol + "-" + currency.code
        return result
    }
}


