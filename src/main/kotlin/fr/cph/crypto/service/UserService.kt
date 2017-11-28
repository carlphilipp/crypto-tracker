package fr.cph.crypto.service

import fr.cph.crypto.domain.Position
import fr.cph.crypto.domain.User

interface UserService {

    fun refreshUser(id: String): User

    fun refreshPosition(position: Position): Position

    fun updatePosition(position: Position): Position
}
