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

    override fun findOneUserByEmail(email: String): User? {
        return userRepository.findOneByEmail(email)?.toUser()
    }

    override fun findOneUserById(id: String): User? {
        return userRepository.findOne(id)?.toUser()
    }

    override fun findAllUsers(): List<User> {
        return userRepository.findAll().map { user -> user.toUser() }
    }

    override fun saveUser(user: User): User {
        return userRepository.save(UserDB.from(user)).toUser()
    }

    override fun savePosition(user: User, position: Position): Position {
        val savedPosition = positionRepository.save(PositionDB.from(position)).toPosition()
        saveUser(user)
        return savedPosition
    }

    override fun deletePosition(user: User, position: Position) {
        user.positions.remove(position)
        positionRepository.delete(position.id)
        userRepository.save(UserDB.from(user))
    }

    override fun deleteAllPositions() {
        positionRepository.deleteAll()
    }

    override fun deleteAllUsers() {
        userRepository.deleteAll()
    }
}