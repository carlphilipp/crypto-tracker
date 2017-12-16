package fr.cph.crypto.mongo

import fr.cph.crypto.core.Ticker
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TickerRepository : MongoRepository<Ticker, String> {
    fun findAllByOrderByMarketCapDesc(): List<Ticker>
    fun findByIdIn(ids: List<String>): List<Ticker>
}