package fr.cph.crypto.backend.repository

import fr.cph.crypto.core.Position
import fr.cph.crypto.core.ShareValue
import fr.cph.crypto.core.Ticker
import fr.cph.crypto.core.User
import org.springframework.data.mongodb.repository.MongoRepository

interface PositionRepository : MongoRepository<Position, String>

interface UserRepository : MongoRepository<User, String> {
    fun findOneByEmail(email: String): User?
}

interface ShareValueRepository : MongoRepository<ShareValue, String> {
    fun findAllByUser(user: User): List<ShareValue>
    fun findTop1ByUserOrderByTimestampDesc(user: User): ShareValue?
}