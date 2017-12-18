package fr.cph.crypto.persistence.mongo

import fr.cph.crypto.core.api.entity.Position
import fr.cph.crypto.core.api.entity.User
import fr.cph.crypto.persistence.mongo.entity.PositionDB
import fr.cph.crypto.persistence.mongo.entity.UserDB
import fr.cph.crypto.persistence.mongo.repository.PositionRepository
import fr.cph.crypto.persistence.mongo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class MongoUserAdapter(private val userRepository: UserRepository,
                       private val positionRepository: PositionRepository) : fr.cph.crypto.core.spi.UserRepository {

    override fun findOneByEmail(email: String): User? {
        return userRepository.findOneByEmail(email)?.toUser()
    }

    override fun findOne(id: String): User? {
        return userRepository.findOne(id)?.toUser()
    }

    override fun findAll(): List<User> {
        return userRepository.findAll().map { user -> user.toUser() }
    }

    override fun save(user: User): User {
        return userRepository.save(UserDB.from(user)).toUser()
    }

    override fun addPosition(user: User, position: Position): Position {
        val savedPosition = positionRepository.save(PositionDB.from(position)).toPosition()
        user.positions.add(savedPosition)
        user.positions.sortWith(compareBy({ it.currency1.currencyName }))

        save(user)
        return savedPosition
    }

    override fun updatePosition(user: User, position: Position): Position {
        val updatedPosition = positionRepository.save(PositionDB.from(position)).toPosition()
        save(user)
        return updatedPosition
    }

    override fun deletePosition(user: User, position: Position) {
        user.positions.remove(position)
        positionRepository.delete(position.id)
        userRepository.save(UserDB.from(user))
    }

    override fun deleteAllPositions() {
        positionRepository.deleteAll()
    }

    override fun deleteAll() {
        userRepository.deleteAll()
    }
}