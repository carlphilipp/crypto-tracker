package fr.cph.crypto.service

import fr.cph.crypto.domain.Position
import fr.cph.crypto.domain.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {

    fun create(user: User): User

    fun findOne(id: String): User

    fun findAll(): List<User>

    fun refreshUserPositions(id: String): List<Position>

    fun updatePosition(position: Position): Position
}
