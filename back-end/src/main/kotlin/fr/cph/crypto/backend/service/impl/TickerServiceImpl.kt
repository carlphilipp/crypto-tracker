package fr.cph.crypto.backend.service.impl

import fr.cph.crypto.backend.client.impl.CoinMarketCapClient
import fr.cph.crypto.backend.domain.Currency
import fr.cph.crypto.backend.domain.Ticker
import fr.cph.crypto.backend.repository.TickerRepository
import fr.cph.crypto.backend.service.TickerService
import org.springframework.stereotype.Service

@Service
class TickerServiceImpl(private val client: CoinMarketCapClient,
                        private val tickerRepository: TickerRepository) : TickerService {

    override fun findOne(id: String): Ticker {
        return tickerRepository.findOne(id)
    }

    override fun findAll(): List<Ticker> {
        return tickerRepository.findAll()
    }

    override fun updateAll() {
        client.getTickers(Currency.USD, "BTC", "ETH", "LTC", "VTC", "GRS")
                .forEach { ticker -> tickerRepository.save(ticker) }
    }
}