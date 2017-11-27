package fr.cph.crypto.client.impl

import fr.cph.crypto.domain.Currency
import fr.cph.crypto.domain.Currency.EUR
import fr.cph.crypto.domain.Currency.USD
import fr.cph.crypto.domain.Ticker

object TickerMapper {

    fun responseToTicker(response: Response, currency: Currency): Ticker {
        val price: Double = when (currency) {
            USD -> java.lang.Double.valueOf(response.priceUsd)
            EUR -> java.lang.Double.valueOf(response.priceEur)
            else -> 0.0
        }
        val percentChange1h = if (response.percentChange1h == null) 0.0 else java.lang.Double.valueOf(response.percentChange1h)
        val percentChange24h = if (response.percentChange24h == null) 0.0 else java.lang.Double.valueOf(response.percentChange24h)
        val percentChange7d = if (response.percentChange7d == null) 0.0 else java.lang.Double.valueOf(response.percentChange7d)
        val result = Ticker(
                Currency.findCurrency(response.symbol!!),
                price,
                java.lang.Double.valueOf(response.priceBtc),
                percentChange1h,
                percentChange24h,
                percentChange7d,
                response.lastUpdated!!
        )
        result.id = response.symbol + "-" + currency.name
        return result
    }
}


