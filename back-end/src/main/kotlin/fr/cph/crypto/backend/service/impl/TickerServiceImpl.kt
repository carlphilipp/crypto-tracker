package fr.cph.crypto.backend.service.impl

import fr.cph.crypto.core.spi.TickerRepository
import fr.cph.crypto.backend.service.TickerService
import fr.cph.crypto.core.Currency
import fr.cph.crypto.core.Ticker
import fr.cph.crypto.core.api.TickerClient
import org.springframework.stereotype.Service

@Service
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