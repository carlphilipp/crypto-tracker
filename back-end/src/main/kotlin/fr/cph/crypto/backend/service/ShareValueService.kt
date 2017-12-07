package fr.cph.crypto.backend.service

import fr.cph.crypto.backend.domain.User

interface ShareValueService {

    fun addNewShareValue(user: User)

    fun addNewShareValue(user: User, liquidityMovement: Double)
}
