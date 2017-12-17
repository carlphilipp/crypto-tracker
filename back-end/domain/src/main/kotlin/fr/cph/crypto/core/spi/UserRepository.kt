package fr.cph.crypto.core.spi

import fr.cph.crypto.core.api.entity.Position
import fr.cph.crypto.core.api.entity.User

interface UserRepository {

    fun findOneByEmail(email: String): User?

    fun findOne(id: String): User?

    fun findAll(): List<User>

    fun save(user: User): User

    fun addPosition(user: User, position: Position): Position

    fun updatePosition(user: User, position: Position): Position

    fun deletePosition(user: User, position: Position)

    fun deleteAllPositions()

    fun deleteAll()
}