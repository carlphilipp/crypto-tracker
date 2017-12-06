package fr.cph.crypto.backend.client.impl

import fr.cph.crypto.backend.domain.Currency
import fr.cph.crypto.backend.domain.Ticker
import org.slf4j.LoggerFactory

object TickerMapper {

    private val LOGGER = LoggerFactory.getLogger(CoinMarketCapClient::class.java)

    fun responseToTicker(currency: Currency, response: Response): Ticker {
        try {
            val price: Double = if (response.priceUsd != null) response.priceUsd!!.toDouble() else 0.0
            val volume24h = if (response._24hVolumeUsd == null) 0.0 else response._24hVolumeUsd!!.toDouble()
            val marketCap = if (response.marketCapUsd == null) 0.0 else response.marketCapUsd!!.toDouble()
            val percentChange1h = if (response.percentChange1h == null) 0.0 else response.percentChange1h!!.toDouble()
            val percentChange24h = if (response.percentChange24h == null) 0.0 else response.percentChange24h!!.toDouble()
            val percentChange7d = if (response.percentChange7d == null) 0.0 else response.percentChange7d!!.toDouble()
            val lastUpdated = if (response.lastUpdated == null) 0L else response.lastUpdated!!.toLong()
            val result = Ticker(
                    Currency.findCurrency(response.symbol!!),
                    currency,
                    price,
                    "coinmarketcap",
                    volume24h,
                    marketCap,
                    percentChange1h / 100,
                    percentChange24h / 100,
                    percentChange7d / 100,
                    lastUpdated
            )
            result.id = response.symbol + "-" + currency.code
            return result
        } catch (e: Exception) {
            LOGGER.error("Error while processing: {}", response, e)
            throw e
        }
    }
}


