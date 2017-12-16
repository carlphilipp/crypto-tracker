package fr.cph.crypto.mongo

import fr.cph.crypto.core.Ticker
import org.springframework.stereotype.Service

@Service
class MongoTickerAdapter(private val repository: TickerRepository) : fr.cph.crypto.core.spi.TickerRepository {

    override fun findOne(id: String): Ticker? {
        return repository.findOne(id)
    }

    override fun findAllById(ids: List<String>): List<Ticker> {
        return repository.findByIdIn(ids)
    }

    override fun findAll(): List<Ticker> {
        return repository.findAllByOrderByMarketCapDesc()
    }

    override fun save(ticker: Ticker): Ticker {
        return repository.save(ticker)
    }

    override fun deleteAll() {
        return repository.deleteAll()
    }

}