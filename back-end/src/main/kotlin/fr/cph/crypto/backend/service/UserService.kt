package fr.cph.crypto.backend.service

import fr.cph.crypto.backend.domain.Position
import fr.cph.crypto.backend.domain.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {

    fun create(user: User): User

    fun findOne(id: String): User

    fun findAll(): List<User>

    // FIXME do not return user
    fun addPosition(id: String, position: Position): User

    // FIXME do not return Position
    fun updatePosition(userId: String, position: Position): Position

    fun deletePosition(userId: String, positionId: String)

    fun updateAllUsersShareValue()
}
