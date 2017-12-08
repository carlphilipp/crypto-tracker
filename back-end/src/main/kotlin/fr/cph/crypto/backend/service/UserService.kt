package fr.cph.crypto.backend.service

import fr.cph.crypto.backend.domain.Position
import fr.cph.crypto.backend.domain.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {

    fun create(user: User): User

    fun findOne(id: String): User

    fun findAll(): List<User>

    fun addPosition(id: String, position: Position)

    fun updatePosition(userId: String, position: Position)

    fun deletePosition(userId: String, positionId: String)

    fun updateAllUsersShareValue()
}
