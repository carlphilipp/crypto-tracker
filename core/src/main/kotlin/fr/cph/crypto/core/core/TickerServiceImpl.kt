package fr.cph.crypto.core.core

import fr.cph.crypto.core.api.TickerService
import fr.cph.crypto.core.api.entity.Currency
import fr.cph.crypto.core.api.entity.Ticker
import fr.cph.crypto.core.spi.TickerClient
import fr.cph.crypto.core.spi.TickerRepository

class TickerServiceImpl(private val client: TickerClient,
                        private val tickerRepository: TickerRepository) : TickerService {

    override fun findOne(id: String): Ticker {
        return tickerRepository.findOne(id)!!
    }

    override fun findAllById(ids: List<String>): List<Ticker> {
        return tickerRepository.findAllById(ids)
    }

    override fun findAll(): List<Ticker> {
        return tickerRepository.findAll()
    }

    override fun updateAll() {
        client.getTickers(Currency.USD, Currency.cryptoCurrenciesAsListOfString())
                .forEach { ticker -> tickerRepository.save(ticker) }
    }
}