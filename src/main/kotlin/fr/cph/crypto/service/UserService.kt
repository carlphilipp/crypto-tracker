package fr.cph.crypto.service

import fr.cph.crypto.domain.Position

interface UserService {

    fun updatePosition(position: Position): Position
}
