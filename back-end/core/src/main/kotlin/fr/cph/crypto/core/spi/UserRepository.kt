package fr.cph.crypto.core.spi

import fr.cph.crypto.core.api.entity.Position
import fr.cph.crypto.core.api.entity.User

interface UserRepository {

    fun findOneUserByEmail(email: String): User?

    fun findOneUserById(id: String): User?

    fun findAllUsers(): List<User>

    fun saveUser(user: User): User

    fun deleteAllUsers()

    fun savePosition(user: User, position: Position): Position

    fun deletePosition(user: User, position: Position)

    fun deleteAllPositions()
}