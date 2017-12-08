package fr.cph.crypto.backend.repository

import fr.cph.crypto.backend.domain.Position
import fr.cph.crypto.backend.domain.ShareValue
import fr.cph.crypto.backend.domain.Ticker
import fr.cph.crypto.backend.domain.User
import org.springframework.data.mongodb.repository.MongoRepository

interface PositionRepository : MongoRepository<Position, String>

interface TickerRepository : MongoRepository<Ticker, String> {
    fun findAllByOrderByMarketCapDesc(): List<Ticker>
}

interface UserRepository : MongoRepository<User, String> {
    fun findOneByEmail(email: String): User?
}

interface ShareValueRepository : MongoRepository<ShareValue, String> {
    fun findAllByUser(user: User): List<ShareValue>
    fun findTop1ByUserOrderByTimestampDesc(user: User): ShareValue?
}