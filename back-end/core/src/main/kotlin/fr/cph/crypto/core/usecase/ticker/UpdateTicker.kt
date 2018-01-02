package fr.cph.crypto.core.usecase.ticker

import fr.cph.crypto.core.entity.Currency
import fr.cph.crypto.core.spi.TickerClient
import fr.cph.crypto.core.spi.TickerRepository

class UpdateTicker(private val tickerRepository: TickerRepository, private val client: TickerClient) {

	fun updateAll() {
		client.getTickers(Currency.USD, Currency.cryptoCurrenciesAsListOfString())
				.forEach { ticker -> tickerRepository.save(ticker) }
	}
}