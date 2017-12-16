package fr.cph.crypto.mongo.repository

import fr.cph.crypto.core.api.entity.Position
import fr.cph.crypto.core.api.entity.ShareValue
import fr.cph.crypto.core.api.entity.Ticker
import fr.cph.crypto.core.api.entity.User
import org.springframework.data.mongodb.repository.MongoRepository

interface TickerRepository : MongoRepository<Ticker, String> {
    fun findAllByOrderByMarketCapDesc(): List<Ticker>
    fun findByIdIn(ids: List<String>): List<Ticker>
}

interface ShareValueRepository : MongoRepository<ShareValue, String> {
    fun findAllByUser(user: User): List<ShareValue>
    fun findTop1ByUserOrderByTimestampDesc(user: User): ShareValue?
}

interface PositionRepository : MongoRepository<Position, String>

interface UserRepository : MongoRepository<User, String> {
    fun findOneByEmail(email: String): User?
}