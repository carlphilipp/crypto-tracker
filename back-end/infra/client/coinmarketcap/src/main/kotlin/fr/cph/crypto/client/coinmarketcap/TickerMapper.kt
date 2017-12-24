/**
 * Copyright 2017 Carl-Philipp Harmant
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.cph.crypto.client.coinmarketcap

import fr.cph.crypto.core.api.entity.Currency
import fr.cph.crypto.core.api.entity.Ticker
import org.slf4j.LoggerFactory

object TickerMapper {

    private val LOGGER = LoggerFactory.getLogger(CoinMarketCapAdapter::class.java)

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
                    currency1 = Currency.findCurrency(response.symbol!!),
                    currency2 = currency,
                    price = price,
                    exchange = "coinmarketcap",
                    volume24h = volume24h,
                    marketCap = marketCap,
                    percentChange1h = percentChange1h / 100,
                    percentChange24h = percentChange24h / 100,
                    percentChange7d = percentChange7d / 100,
                    lastUpdated = lastUpdated)
            result.id = response.symbol + "-" + currency.code
            return result
        } catch (e: Exception) {
            LOGGER.error("Error while processing: {}", response, e)
            throw e
        }
    }
}


