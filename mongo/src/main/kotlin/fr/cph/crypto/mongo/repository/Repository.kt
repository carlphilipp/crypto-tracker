package fr.cph.crypto.mongo.repository

import fr.cph.crypto.mongo.entity.PositionDB
import fr.cph.crypto.mongo.entity.ShareValueDB
import fr.cph.crypto.mongo.entity.TickerDB
import fr.cph.crypto.mongo.entity.UserDB
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