package fr.cph.crypto.persistence.mongo.repository

import fr.cph.crypto.persistence.mongo.entity.PositionDB
import fr.cph.crypto.persistence.mongo.entity.ShareValueDB
import fr.cph.crypto.persistence.mongo.entity.TickerDB
import fr.cph.crypto.persistence.mongo.entity.UserDB
import org.springframework.data.mongodb.repository.MongoRepository

interface TickerRepository : MongoRepository<TickerDB, String> {
    fun findAllByOrderByMarketCapDesc(): List<TickerDB>
    fun findByIdIn(ids: List<String>): List<TickerDB>
}

interface ShareValueRepository : MongoRepository<ShareValueDB, String> {
    fun findAllByUser(user: UserDB): List<ShareValueDB>
    fun findTop1ByUserOrderByTimestampDesc(user: UserDB): ShareValueDB?
}

interface PositionRepository : MongoRepository<PositionDB, String>

interface UserRepository : MongoRepository<UserDB, String> {
    fun findOneByEmail(email: String): UserDB?
}