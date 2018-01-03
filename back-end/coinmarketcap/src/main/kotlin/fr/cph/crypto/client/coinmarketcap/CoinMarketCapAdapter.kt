/**
 * Copyright 2018 Carl-Philipp Harmant
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

import fr.cph.crypto.core.entity.Currency
import fr.cph.crypto.core.entity.Ticker
import fr.cph.crypto.core.spi.TickerClient
import org.slf4j.LoggerFactory
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

class CoinMarketCapAdapter(private val restTemplate: RestTemplate) : TickerClient {

	override fun getTicker(currency: Currency, ticker: String): Ticker? {
		LOGGER.debug("Search ticker: {}", ticker)
		return getAllTickers(currency).firstOrNull { tick -> tick.currency1.code == ticker }
	}

	override fun getTickers(currency: Currency, tickers: List<String>): List<Ticker> {
		LOGGER.debug("Search tickers: {}", tickers)
		return getAllTickers(currency).filter { ticker -> tickers.contains(ticker.currency1.code) }
	}

	private fun getAllTickers(currency: Currency): List<Ticker> {
		val uriComponents = UriComponentsBuilder.newInstance()
			.scheme("https")
			.host("api.coinmarketcap.com")
			.path("/v1/ticker")
			.queryParam("limit", "0")
			.queryParam("convert", currency.code)
			.build()

		LOGGER.debug("HTTP request: {}", uriComponents.toUri())
		val responses = restTemplate.getForObject(uriComponents.toUri(), Array<Response>::class.java)
		return responses
			.filter { response -> !response.id!!.contains("futures") } //Not sure why two coins can have the same code, so filter out for now
			.map { response -> TickerMapper.responseToTicker(currency, response) }
			.filter { ticker -> ticker.currency1 != Currency.UNKNOWN }
	}

	companion object {
		private val LOGGER = LoggerFactory.getLogger(CoinMarketCapAdapter::class.java)
	}
}
