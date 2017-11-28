package fr.cph.crypto.service

import fr.cph.crypto.domain.Position

interface UserService {

    fun refreshUserPositions(id: String): List<Position>

    fun updatePosition(position: Position): Position
}
