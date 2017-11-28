package fr.cph.crypto.service

import fr.cph.crypto.domain.Position
import fr.cph.crypto.domain.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {

    fun createUser(user: User): User

    fun refreshUserPositions(id: String): List<Position>

    fun updatePosition(position: Position): Position
}
