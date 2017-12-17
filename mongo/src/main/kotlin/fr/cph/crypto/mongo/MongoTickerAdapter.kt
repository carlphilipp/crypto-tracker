package fr.cph.crypto.mongo

import fr.cph.crypto.core.api.entity.Ticker
import fr.cph.crypto.mongo.entity.TickerDB
import fr.cph.crypto.mongo.repository.TickerRepository
import org.springframework.stereotype.Service

@Service
class MongoTickerAdapter(private val repository: TickerRepository) : fr.cph.crypto.core.spi.TickerRepository {

    override fun findOne(id: String): Ticker? {
        return repository.findOne(id)?.toTicker()
    }

    override fun findAllById(ids: List<String>): List<Ticker> {
        return repository.findByIdIn(ids).map { ticker -> ticker.toTicker() }
    }

    override fun findAll(): List<Ticker> {
        return repository.findAllByOrderByMarketCapDesc()
                .map { ticker -> ticker.toTicker() }
    }

    override fun save(ticker: Ticker): Ticker {
        return repository.save(TickerDB.toTickerDB(ticker)).toTicker()
    }

    override fun deleteAll() {
        return repository.deleteAll()
    }
}